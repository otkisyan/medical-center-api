package com.medicalcenter.receptionapi.service;

import com.medicalcenter.receptionapi.domain.Patient;
import com.medicalcenter.receptionapi.dto.patient.PatientDto;
import com.medicalcenter.receptionapi.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @Test
    public void PatientService_Save_ReturnsPatientResponseDto() {
        Patient patient = Patient.builder()
                .name("Name")
                .surname("Surname")
                .middleName("Middle name")
                .address("Address")
                .phone("Phone")
                .messengerContact("Messenger contact")
                .birthDate(LocalDate.of(2023, 2, 1))
                .preferentialCategory("Preferential category")
                .build();
        PatientDto patientRequestDto = PatientDto.builder()
                .name("Name")
                .surname("Surname")
                .middleName("Middle name")
                .address("Address")
                .phone("Phone")
                .messengerContact("Messenger contact")
                .birthDate(LocalDate.of(2023, 2, 1))
                .preferentialCategory("Preferential category")
                .build();
        when(patientRepository.save(Mockito.any(Patient.class))).thenReturn(patient);
        PatientDto savedPatient = patientService.savePatient(patientRequestDto);
        Assertions.assertNotNull(savedPatient);
    }

/*    @Test
    public void PatientService_FindAll_ReturnsPagePatientResponseDto() {
        Specification<Patient> patientSpec = Specification.where(null);
        int page = 1;
        int pageSize = 5;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);
        List<Patient> patientsList = Arrays.asList(
                Patient.builder()
                        .name("John")
                        .surname("Doe")
                        .middleName("William")
                        .address("123 Main Street")
                        .phone("555-1234")
                        .messengerContact("john.doe@example.com")
                        .birthDate(LocalDate.of(1990, 5, 15))
                        .preferentialCategory("None")
                        .build(),
                Patient.builder()
                        .name("Jane")
                        .surname("Smith")
                        .middleName("Marie")
                        .address("456 Oak Avenue")
                        .phone("555-5678")
                        .messengerContact("jane.smith@example.com")
                        .birthDate(LocalDate.of(1985, 8, 22))
                        .preferentialCategory("Senior Citizen")
                        .build());
        Page<Patient> patientPage = new PageImpl<>(patientsList, pageable, patientsList.size());
        when(patientRepository.findAll(patientSpec, pageable)).thenReturn(patientPage);
        Page<PatientDto> result = patientService.findAllPatients(patientSpec, page, pageSize);
        Assertions.assertEquals(2, result.getContent().size());
    }*/

    @Test
    public void PatientService_FindById_ReturnsPatientResponseDto() {
        Long id = 1L;
        Patient patient = Patient.builder()
                .name("Name")
                .surname("Surname")
                .middleName("Middle name")
                .address("Address")
                .phone("Phone")
                .messengerContact("Messenger contact")
                .birthDate(LocalDate.of(2023, 2, 1))
                .preferentialCategory("Preferential category")
                .build();
        when(patientRepository.findById(id)).thenReturn(Optional.ofNullable(patient));
        PatientDto patientDto = patientService.findPatientById(id);
        Assertions.assertNotNull(patientDto);
    }

    @Test
    public void PatientService_Update_ReturnsPatientResponseDto() {
        Long id = 1L;
        Patient patient = Patient.builder()
                .name("Name")
                .surname("Surname")
                .middleName("Middle name")
                .address("Address")
                .phone("Phone")
                .messengerContact("Messenger contact")
                .birthDate(LocalDate.of(2023, 2, 1))
                .preferentialCategory("Preferential category")
                .build();
        when(patientRepository.findById(id)).thenReturn(Optional.ofNullable(patient));
        when(patientRepository.save(Mockito.any(Patient.class))).thenReturn(patient);
        PatientDto patientRequestDto = PatientDto.builder()
                .name("Enam")
                .surname("Surname")
                .middleName("Middle name")
                .address("Address")
                .phone("Phone")
                .messengerContact("Messenger contact")
                .birthDate(LocalDate.of(2023, 2, 1))
                .preferentialCategory("Preferential category")
                .build();
        PatientDto patientDto = patientService.updatePatient(patientRequestDto, id);
        Assertions.assertNotNull(patientDto);
        Assertions.assertEquals(patient.getName(), patientRequestDto.getName());
    }

    @Test
    public void PatientService_Delete_ReturnsVoid(){
        Long id = 1L;
        patientService.deletePatient(id);
        Mockito.verify(patientRepository, Mockito.times(1)).deleteById(id);
    }
}
