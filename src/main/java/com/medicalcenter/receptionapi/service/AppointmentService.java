package com.medicalcenter.receptionapi.service;

import com.medicalcenter.receptionapi.domain.*;
import com.medicalcenter.receptionapi.dto.appointment.AppointmentRequestDto;
import com.medicalcenter.receptionapi.dto.appointment.AppointmentResponseDto;
import com.medicalcenter.receptionapi.dto.appointment.TimeSlotDto;
import com.medicalcenter.receptionapi.dto.consultation.ConsultationResponseDto;
import com.medicalcenter.receptionapi.exception.*;
import com.medicalcenter.receptionapi.repository.AppointmentRepository;
import com.medicalcenter.receptionapi.repository.ConsultationRepository;
import com.medicalcenter.receptionapi.repository.DoctorRepository;
import com.medicalcenter.receptionapi.repository.PatientRepository;
import com.medicalcenter.receptionapi.security.CustomUserDetails;
import com.medicalcenter.receptionapi.security.enums.RoleAuthority;
import com.medicalcenter.receptionapi.specification.AppointmentSpecification;
import com.medicalcenter.receptionapi.util.BeanCopyUtils;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppointmentService {
  private final AppointmentRepository appointmentRepository;
  private final WorkScheduleService workScheduleService;
  private final PatientRepository patientRepository;
  private final DoctorRepository doctorRepository;
  private final ConsultationRepository consultationRepository;
  private final ConsultationService consultationService;
  private final UserService userService;

  @Cacheable(value = "appointments", key = "'count'")
  public long count() {
    return appointmentRepository.count();
  }

  @Cacheable(
      value = "appointments",
      key =
          "#patient + '_' + #doctor + '_'"
              + "+ (#date != null ? #date.toString() : 'null') + '_'"
              + "+ (#timeStart != null ? #timeStart.toString() : 'null')"
              + "+ '_' + #page + '_' + #pageSize")
  public Page<AppointmentResponseDto> findAllAppointments(
      String patient, String doctor, LocalDate date, LocalTime timeStart, int page, int pageSize) {
    Specification<Appointment> specs = Specification.where(null);
    if (date != null) {
      specs = specs.and(AppointmentSpecification.withDate(date));
    }
    if (timeStart != null) {
      specs = specs.and(AppointmentSpecification.withTimeStart(timeStart));
    }
    if (patient != null && !patient.isBlank()) {
      specs = specs.and(AppointmentSpecification.withPatient(patient));
    }
    if (doctor != null && !doctor.isBlank()) {
      specs = specs.and(AppointmentSpecification.withDoctor(doctor));
    }
    Sort sort = Sort.by(Sort.Direction.DESC, "id");
    Pageable pageable = PageRequest.of(page, pageSize, sort);
    return appointmentRepository.findAll(specs, pageable).map(AppointmentResponseDto::ofEntity);
  }

  public List<Appointment> findAllAppointments() {
    return appointmentRepository.findAll();
  }

  @Cacheable(value = "appointments", key = "#id")
  public AppointmentResponseDto findAppointmentById(Long id) {
    return appointmentRepository
        .findById(id)
        .map(AppointmentResponseDto::ofEntity)
        .orElseThrow(ResourceNotFoundException::new);
  }

  @Cacheable(value = "timetable", key = "#doctorId + '_' + #date")
  @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST') or #doctorId == authentication.principal.id")
  public List<TimeSlotDto> generateTimetable(Long doctorId, LocalDate date) {
    java.time.DayOfWeek dayOfWeek = date.getDayOfWeek();
    WorkSchedule workSchedule =
        workScheduleService.findWorkScheduleByDoctorAndDayOfWeek(doctorId, dayOfWeek.getValue());
    LocalTime workTimeStart = workSchedule.getWorkTimeStart();
    LocalTime workTimeEnd = workSchedule.getWorkTimeEnd();
    if (workTimeStart == null || workTimeEnd == null) {
      throw new WorkScheduleDoesNotExistException();
    }
    int minutesStep = 15;
    List<TimeSlotDto> timeSlots = new ArrayList<>();
    List<Appointment> appointments = findAppointmentsByDoctorAndDate(doctorId, date);
    LocalTime currentTime = workTimeStart;
    while (currentTime.isBefore(workTimeEnd)) {
      TimeSlotDto timeSlot = new TimeSlotDto();
      timeSlot.setDate(date);
      timeSlot.setStartTime(currentTime);
      LocalTime slotEndTime = currentTime.plusMinutes(minutesStep);
      if (slotEndTime.isAfter(workTimeEnd)) {
        slotEndTime = workTimeEnd;
      }
      timeSlot.setEndTime(slotEndTime);
      List<AppointmentResponseDto> slotAppointments = new ArrayList<>();
      for (Appointment appointment : appointments) {
        if (appointment.getTimeStart().isBefore(timeSlot.getEndTime())
                && appointment.getTimeEnd().isAfter(timeSlot.getStartTime())
            || appointment.getTimeEnd().equals(timeSlot.getStartTime())) {
          slotAppointments.add(AppointmentResponseDto.ofEntity(appointment));
        }
      }
      slotAppointments.sort(Comparator.comparing(AppointmentResponseDto::getTimeStart));
      timeSlot.setAppointments(slotAppointments);
      timeSlots.add(timeSlot);
      currentTime = currentTime.plusMinutes(minutesStep);
    }
    return timeSlots;
  }

  @PreAuthorize(
      "hasAnyRole('ADMIN', 'RECEPTIONIST') or"
          + "#appointmentRequestDto.doctorId == authentication.principal.id")
  @CacheEvict(
      value = {"appointments", "timetable"},
      allEntries = true)
  public AppointmentResponseDto saveAppointment(AppointmentRequestDto appointmentRequestDto) {
    Doctor doctor =
        doctorRepository
            .findById(appointmentRequestDto.getDoctorId())
            .orElseThrow(
                () -> new ResourceNotFoundException("A doctor with that id doesn't exist"));
    Patient patient =
        patientRepository
            .findById(appointmentRequestDto.getPatientId())
            .orElseThrow(
                () -> new ResourceNotFoundException("A patient with that id doesn't exist"));
    if (!appointmentRequestDto.getTimeStart().isBefore(appointmentRequestDto.getTimeEnd())) {
      throw new IllegalArgumentException(
          "The start time of the appointment cannot be greater than the end time");
    }
    if (appointmentRepository
        .existsByPatient_IdAndDateAndTimeStartLessThanEqualAndTimeEndGreaterThanEqual(
            appointmentRequestDto.getPatientId(),
            appointmentRequestDto.getDate(),
            appointmentRequestDto.getTimeEnd().minusMinutes(1),
            appointmentRequestDto.getTimeStart().plusMinutes(1))) {
      throw new AppointmentPatientConflictException();
    }
    if (appointmentRepository
        .existsByDoctor_IdAndDateAndTimeStartLessThanEqualAndTimeEndGreaterThanEqual(
            appointmentRequestDto.getDoctorId(),
            appointmentRequestDto.getDate(),
            appointmentRequestDto.getTimeEnd().minusMinutes(1),
            appointmentRequestDto.getTimeStart().plusMinutes(1))) {
      throw new AppointmentDoctorConflictException();
    }
    java.time.DayOfWeek dayOfWeek = appointmentRequestDto.getDate().getDayOfWeek();
    WorkSchedule workSchedule =
        workScheduleService.findWorkScheduleByDoctorAndDayOfWeek(
            doctor.getId(), dayOfWeek.getValue());
    if (workSchedule.getWorkTimeStart() == null || workSchedule.getWorkTimeEnd() == null) {
      throw new InvalidAppointmentTimeException();
    }
    if (appointmentRequestDto.getTimeStart().isBefore(workSchedule.getWorkTimeStart())
        || appointmentRequestDto.getTimeStart().isAfter(workSchedule.getWorkTimeEnd())
        || appointmentRequestDto.getTimeEnd().isAfter(workSchedule.getWorkTimeEnd())
        || appointmentRequestDto.getTimeEnd().isBefore(workSchedule.getWorkTimeStart())) {
      throw new InvalidAppointmentTimeException();
    }
    Appointment newAppointment = AppointmentRequestDto.toEntity(appointmentRequestDto);
    newAppointment.setDoctor(doctor);
    newAppointment.setPatient(patient);
    newAppointment = appointmentRepository.save(newAppointment);
    Consultation consultation = new Consultation();
    consultation.setAppointment(newAppointment);
    consultationRepository.save(consultation);
    return AppointmentResponseDto.ofEntity(newAppointment);
  }

  @PreAuthorize(
      "hasAnyRole('ADMIN', 'RECEPTIONIST') or"
          + "#appointmentRequestDto.doctorId == authentication.principal.id")
  @CacheEvict(
      value = {"appointments", "timetable"},
      allEntries = true)
  public AppointmentResponseDto updateAppointment(
      AppointmentRequestDto appointmentRequestDto, Long id) {
    CustomUserDetails customUserDetails = userService.getCustomUserDetails();
    Appointment appointmentToUpdate =
        appointmentRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    if (userService.hasAnyAuthority(RoleAuthority.DOCTOR.authority)
        && !Objects.equals(customUserDetails.getId(), appointmentToUpdate.getDoctor().getId())) {
      throw new AccessDeniedException(
          "The doctor can edit only those appointments that are assigned to him");
    }
    Doctor doctor =
        doctorRepository
            .findById(appointmentRequestDto.getDoctorId())
            .orElseThrow(
                () -> new ResourceNotFoundException("A doctor with that id doesn't exist"));
    if (!appointmentRequestDto.getTimeStart().isBefore(appointmentRequestDto.getTimeEnd())) {
      throw new IllegalArgumentException();
    }
    if (appointmentRepository
        .existsByDoctor_IdAndDateAndTimeStartLessThanEqualAndTimeEndGreaterThanEqualAndIdNot(
            appointmentRequestDto.getDoctorId(),
            appointmentRequestDto.getDate(),
            appointmentRequestDto.getTimeEnd().minusMinutes(1),
            appointmentRequestDto.getTimeStart().plusMinutes(1),
            id)) {
      throw new AppointmentDoctorConflictException();
    }
    if (appointmentRepository
        .existsByPatient_IdAndDateAndTimeStartLessThanEqualAndTimeEndGreaterThanEqualAndIdNot(
            appointmentRequestDto.getPatientId(),
            appointmentRequestDto.getDate(),
            appointmentRequestDto.getTimeEnd().minusMinutes(1),
            appointmentRequestDto.getTimeStart().plusMinutes(1),
            id)) {
      throw new AppointmentPatientConflictException();
    }
    java.time.DayOfWeek dayOfWeek = appointmentRequestDto.getDate().getDayOfWeek();
    WorkSchedule workSchedule =
        workScheduleService.findWorkScheduleByDoctorAndDayOfWeek(
            doctor.getId(), dayOfWeek.getValue());
    if (workSchedule.getWorkTimeStart() == null || workSchedule.getWorkTimeEnd() == null) {
      throw new InvalidAppointmentTimeException();
    }
    if (appointmentRequestDto.getTimeStart().isBefore(workSchedule.getWorkTimeStart())
        || appointmentRequestDto.getTimeStart().isAfter(workSchedule.getWorkTimeEnd())
        || appointmentRequestDto.getTimeEnd().isAfter(workSchedule.getWorkTimeEnd())
        || appointmentRequestDto.getTimeEnd().isBefore(workSchedule.getWorkTimeStart())) {
      throw new InvalidAppointmentTimeException();
    }
    Appointment appointmentUpdateRequest = AppointmentRequestDto.toEntity(appointmentRequestDto);
    appointmentUpdateRequest.setDoctor(doctor);
    BeanCopyUtils.copyNonNullProperties(
        appointmentUpdateRequest, appointmentToUpdate, "id", "patient");
    Appointment updatedAppointment = appointmentRepository.save(appointmentToUpdate);
    return AppointmentResponseDto.ofEntity(updatedAppointment);
  }

  @CacheEvict(
      value = {"appointments", "timetable"},
      allEntries = true)
  public void deleteAppointment(Long id) {
    Appointment appointmentToDelete =
        appointmentRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Appointment with such id is not found"));
    CustomUserDetails currentUser = userService.getCustomUserDetails();
    if (userService.hasAnyAuthority(RoleAuthority.DOCTOR.authority)
        && !Objects.equals(currentUser.getId(), appointmentToDelete.getDoctor().getId())) {
      throw new AccessDeniedException("Doctor can only delete their own appointments");
    }
    appointmentRepository.deleteById(id);
  }

  public ConsultationResponseDto getAppointmentConsultation(Long appointmentId) {
    return consultationService.findConsultationById(appointmentId);
  }

  public List<Appointment> findAppointmentsByDoctorAndDate(Long doctorId, LocalDate date) {
    return appointmentRepository.findByDoctor_IdAndDate(doctorId, date);
  }
}
