package com.medicalcenter.receptionapi.service;

import com.medicalcenter.receptionapi.domain.DayOfWeek;
import com.medicalcenter.receptionapi.domain.Doctor;
import com.medicalcenter.receptionapi.domain.Office;
import com.medicalcenter.receptionapi.domain.WorkSchedule;
import com.medicalcenter.receptionapi.dto.doctor.DoctorResponseDto;
import com.medicalcenter.receptionapi.dto.office.OfficeResponseDto;
import com.medicalcenter.receptionapi.dto.workschedule.WorkScheduleRequestDto;
import com.medicalcenter.receptionapi.dto.workschedule.WorkScheduleResponseDto;
import com.medicalcenter.receptionapi.exception.InvalidDoctorWorkTimeException;
import com.medicalcenter.receptionapi.exception.ResourceNotFoundException;
import com.medicalcenter.receptionapi.repository.DayOfWeekRepository;
import com.medicalcenter.receptionapi.repository.WorkScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class WorkScheduleService {

    private final WorkScheduleRepository workScheduleRepository;
    private final DayOfWeekRepository dayOfWeekRepository;

    public Page<WorkScheduleResponseDto> findWorkSchedulesByDoctorId(int page, int pageSize, Long doctorId){
        Pageable pageable = PageRequest.of(page, pageSize);
        return workScheduleRepository.findByDoctor_Id(doctorId, pageable).map(WorkScheduleResponseDto::ofEntity);
    }

    public WorkScheduleResponseDto updateWorkSchedule(WorkScheduleRequestDto workScheduleRequestDto, Long id){
        WorkSchedule workScheduleToUpdate = workScheduleRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if (workScheduleRequestDto.getWorkTimeStart() != null && workScheduleRequestDto.getWorkTimeEnd() != null) {
            if (workScheduleRequestDto.getWorkTimeStart().isAfter(workScheduleRequestDto.getWorkTimeEnd())) {
                throw new InvalidDoctorWorkTimeException();
            }
        }
        WorkSchedule updateRequestWorkSchedule = WorkScheduleRequestDto.toEntity(workScheduleRequestDto);
        BeanUtils.copyProperties(updateRequestWorkSchedule, workScheduleToUpdate, "id", "doctor", "dayOfWeek");
        WorkSchedule updatedWorkSchedule = workScheduleRepository.save(workScheduleToUpdate);
        return WorkScheduleResponseDto.ofEntity(updatedWorkSchedule);
    }

    public void saveAll(List<WorkSchedule> workSchedules) {
        for (WorkSchedule workSchedule : workSchedules){
            if (workSchedule.getWorkTimeStart() != null && workSchedule.getWorkTimeEnd() != null) {
                if (workSchedule.getWorkTimeStart().isAfter(workSchedule.getWorkTimeEnd())) {
                    throw new InvalidDoctorWorkTimeException();
                }
            }
        }
        workScheduleRepository.saveAll(workSchedules);
    }

    public void createDoctorEmptyWorkSchedules(Doctor doctor){
        List<DayOfWeek> daysOfWeek = dayOfWeekRepository.findAll();
        List<WorkSchedule> workSchedules = new ArrayList<>();
        for (DayOfWeek dayOfWeek : daysOfWeek) {
            workSchedules.add(WorkSchedule.builder()
                    .doctor(doctor)
                    .dayOfWeek(dayOfWeek)
                    .workTimeStart(null)
                    .workTimeEnd(null).build());
        }
        workScheduleRepository.saveAll(workSchedules);
    }

    public WorkSchedule findWorkScheduleByDoctorAndDayOfWeek(Long doctorId, int dayOfWeek) {
        return workScheduleRepository.findByDoctor_IdAndDayOfWeek_Id(doctorId, dayOfWeek);
    }


}