package com.medicalcenter.receptionapi.service;

import com.medicalcenter.receptionapi.domain.Appointment;
import com.medicalcenter.receptionapi.domain.Doctor;
import com.medicalcenter.receptionapi.domain.Patient;
import com.medicalcenter.receptionapi.domain.WorkSchedule;
import com.medicalcenter.receptionapi.dto.appointment.AppointmentRequestDto;
import com.medicalcenter.receptionapi.dto.appointment.AppointmentResponseDto;
import com.medicalcenter.receptionapi.dto.appointment.TimeSlotDto;
import com.medicalcenter.receptionapi.exception.*;
import com.medicalcenter.receptionapi.repository.AppointmentRepository;
import com.medicalcenter.receptionapi.repository.DoctorRepository;
import com.medicalcenter.receptionapi.repository.PatientRepository;
import com.medicalcenter.receptionapi.security.enums.RoleAuthority;
import com.medicalcenter.receptionapi.specification.AppointmentSpecification;
import com.medicalcenter.receptionapi.util.BeanCopyUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
@Validated
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final WorkScheduleService workScheduleService;
    private final PatientRepository patientRepository;
    private final UserService userService;
    private final DoctorRepository doctorRepository;

    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> findAppointmentsByDoctorAndDate(Long doctorId, LocalDate date) {
        return appointmentRepository.findByDoctor_IdAndDate(doctorId, date);
    }

    public AppointmentResponseDto findAppointmentById(Long id) {
        return appointmentRepository.findById(id).map(AppointmentResponseDto::ofEntity).orElseThrow(ResourceNotFoundException::new);
    }

    public Page<AppointmentResponseDto> findAllAppointments(String patient,
                                                            String doctor,
                                                            LocalDate date,
                                                            LocalTime timeStart,
                                                            int page,
                                                            int pageSize) {
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

    @PreAuthorize("hasAnyAuthority('ADMIN', 'RECEPTIONIST') or #doctorId == authentication.principal.id")
    public List<TimeSlotDto> generateTimetable(Long doctorId, LocalDate date) {
        java.time.DayOfWeek dayOfWeek = date.getDayOfWeek();
        WorkSchedule workSchedule = workScheduleService.findWorkScheduleByDoctorAndDayOfWeek(doctorId, dayOfWeek.getValue());
        LocalTime workTimeStart = workSchedule.getWorkTimeStart();
        LocalTime workTimeEnd = workSchedule.getWorkTimeEnd();
        if (workTimeStart == null || workTimeEnd == null) {
            throw new WorkScheduleDoesNotExistException();
        }
        List<TimeSlotDto> timeSlots = new ArrayList<>();
        List<Appointment> appointments = findAppointmentsByDoctorAndDate(doctorId, date);
        LocalTime currentTime = workTimeStart;
        while (currentTime.isBefore(workTimeEnd)) {
            TimeSlotDto timeSlot = new TimeSlotDto();
            timeSlot.setStartTime(currentTime);
            LocalTime slotEndTime = currentTime.plusMinutes(30);
            if (slotEndTime.isAfter(workTimeEnd)) {
                slotEndTime = workTimeEnd;
            }
            timeSlot.setEndTime(slotEndTime);
            List<AppointmentResponseDto> slotAppointments = new ArrayList<>();
            for (Appointment appointment : appointments) {
                if (appointment.getTimeStart().isBefore(timeSlot.getEndTime())
                        && appointment.getTimeEnd().isAfter(timeSlot.getStartTime())) {
                    slotAppointments.add(AppointmentResponseDto.ofEntity(appointment));
                }
            }
            slotAppointments.sort(Comparator.comparing(AppointmentResponseDto::getTimeStart));
            timeSlot.setAppointments(slotAppointments);
            timeSlots.add(timeSlot);
            currentTime = currentTime.plusMinutes(30);
        }
        return timeSlots;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST') or #appointmentRequestDto.doctorId == authentication.principal.id")
    public AppointmentResponseDto saveAppointment(AppointmentRequestDto appointmentRequestDto) {
        Doctor doctor = doctorRepository.findById(appointmentRequestDto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("A doctor with that id doesn't exist"));
        Patient patient = patientRepository.findById(appointmentRequestDto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("A patient with that id doesn't exist"));
        if (!appointmentRequestDto.getTimeStart().isBefore(appointmentRequestDto.getTimeEnd())) {
            throw new IllegalArgumentException("The start time of the appointment cannot be greater than the end time");
        }
        if (appointmentRepository.existsByPatient_IdAndDateAndTimeStartLessThanEqualAndTimeEndGreaterThanEqual(
                appointmentRequestDto.getPatientId(),
                appointmentRequestDto.getDate(),
                appointmentRequestDto.getTimeEnd(),
                appointmentRequestDto.getTimeStart())) {
            throw new AppointmentPatientConflictException();
        }
        if (appointmentRepository.existsByDoctor_IdAndDateAndTimeStartLessThanEqualAndTimeEndGreaterThanEqual(
                appointmentRequestDto.getDoctorId(),
                appointmentRequestDto.getDate(),
                appointmentRequestDto.getTimeEnd(),
                appointmentRequestDto.getTimeStart())) {
            throw new AppointmentDoctorConflictException();
        }
        java.time.DayOfWeek dayOfWeek = appointmentRequestDto.getDate().getDayOfWeek();
        WorkSchedule workSchedule = workScheduleService.findWorkScheduleByDoctorAndDayOfWeek(doctor.getId(), dayOfWeek.getValue());
        if (workSchedule.getWorkTimeStart() == null || workSchedule.getWorkTimeEnd() == null) {
            throw new InvalidAppointmentTimeException();
        }
        if (appointmentRequestDto.getTimeStart().isBefore(workSchedule.getWorkTimeStart())
                || appointmentRequestDto.getTimeStart().isAfter(workSchedule.getWorkTimeEnd())
                || appointmentRequestDto.getTimeEnd().isAfter(workSchedule.getWorkTimeEnd())
                || appointmentRequestDto.getTimeEnd().isBefore(workSchedule.getWorkTimeStart())
        ) {
            throw new InvalidAppointmentTimeException();
        }
        Appointment newAppointment = AppointmentRequestDto.toEntity(appointmentRequestDto);
        newAppointment.setDoctor(doctor);
        newAppointment.setPatient(patient);
        newAppointment = appointmentRepository.save(newAppointment);
        return AppointmentResponseDto.ofEntity(newAppointment);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'RECEPTIONIST') or #appointmentRequestDto.doctorId == authentication.principal.id")
    public void updateAppointment(AppointmentRequestDto appointmentRequestDto, Long id) {
        Doctor doctor = doctorRepository.findById(appointmentRequestDto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("A doctor with that id doesn't exist"));
        if (!appointmentRequestDto.getTimeStart().isBefore(appointmentRequestDto.getTimeEnd())) {
            throw new IllegalArgumentException();
        }
        if (appointmentRepository.existsByDoctor_IdAndDateAndTimeStartLessThanEqualAndTimeEndGreaterThanEqualAndIdNot(
                appointmentRequestDto.getDoctorId(),
                appointmentRequestDto.getDate(),
                appointmentRequestDto.getTimeEnd(),
                appointmentRequestDto.getTimeStart(),
                id)) {
            throw new AppointmentDoctorConflictException();
        }
        if (appointmentRepository.existsByPatient_IdAndDateAndTimeStartLessThanEqualAndTimeEndGreaterThanEqualAndIdNot(
                appointmentRequestDto.getPatientId(),
                appointmentRequestDto.getDate(),
                appointmentRequestDto.getTimeEnd(),
                appointmentRequestDto.getTimeStart(), id)) {
            throw new AppointmentPatientConflictException();
        }
        java.time.DayOfWeek dayOfWeek = appointmentRequestDto.getDate().getDayOfWeek();
        WorkSchedule workSchedule = workScheduleService.findWorkScheduleByDoctorAndDayOfWeek(doctor.getId(), dayOfWeek.getValue());
        if (workSchedule.getWorkTimeStart() == null || workSchedule.getWorkTimeEnd() == null) {
            throw new InvalidAppointmentTimeException();
        }
        if (appointmentRequestDto.getTimeStart().isBefore(workSchedule.getWorkTimeStart())
                || appointmentRequestDto.getTimeStart().isAfter(workSchedule.getWorkTimeEnd())
                || appointmentRequestDto.getTimeEnd().isAfter(workSchedule.getWorkTimeEnd())
                || appointmentRequestDto.getTimeEnd().isBefore(workSchedule.getWorkTimeStart())
        ) {
            throw new InvalidAppointmentTimeException();
        }
        Appointment appointmentToUpdate = appointmentRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if (appointmentRequestDto.getDiagnosis() != null || appointmentRequestDto.getMedicalRecommendations() != null) {
            if (userService.hasAnyAuthority(RoleAuthority.RECEPTIONIST.authority, RoleAuthority.ADMIN.authority)) {
                throw new AccessDeniedException("Access denied");
            }
        }
        Appointment appointmentUpdateRequest = AppointmentRequestDto.toEntity(appointmentRequestDto);
        appointmentUpdateRequest.setDoctor(doctor);
        BeanCopyUtils.copyNonNullProperties(appointmentUpdateRequest, appointmentToUpdate, "id", "patient");
        appointmentRepository.save(appointmentToUpdate);
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public long count() {
        return appointmentRepository.count();
    }
}
