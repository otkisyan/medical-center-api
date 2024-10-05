package com.medicalcenter.receptionapi.repository;

import com.medicalcenter.receptionapi.domain.Patient;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PatientRepositoryTest {

  @Autowired private PatientRepository patientRepository;

  @Test
  public void PatientRepository_Save_ReturnsSavedPatient() {
    Patient patient =
        Patient.builder()
            .name("Name")
            .surname("Surname")
            .middleName("Middle name")
            .address("Address")
            .phone("Phone")
            .messengerContact("Messenger contact")
            .birthDate(LocalDate.of(2023, 2, 1))
            .preferentialCategory("Preferential category")
            .build();
    Patient savedPatient = patientRepository.save(patient);
    Assertions.assertNotNull(savedPatient);
    Assertions.assertTrue(savedPatient.getId() > 0);
  }

  @Test
  public void PatientRepository_Update_ReturnsPatient() {
    Patient patient =
        Patient.builder()
            .name("Name")
            .surname("Surname")
            .middleName("Middle name")
            .address("Address")
            .phone("Phone")
            .messengerContact("Messenger contact")
            .birthDate(LocalDate.of(2023, 2, 1))
            .preferentialCategory("Preferential category")
            .build();
    patientRepository.save(patient);
    Patient savedPatient = patientRepository.findById(patient.getId()).get();
    savedPatient.setName("Eman");
    Patient updatedPatient = patientRepository.save(savedPatient);
    Assertions.assertEquals(updatedPatient.getName(), "Eman");
  }

  @Test
  public void PatientRepository_FindAll_ReturnsMoreThanOnePatient() {
    Patient patient =
        Patient.builder()
            .name("Name")
            .surname("Surname")
            .middleName("Middle name")
            .address("Address")
            .phone("Phone")
            .messengerContact("Messenger contact")
            .birthDate(LocalDate.of(2023, 2, 1))
            .preferentialCategory("Preferential category")
            .build();
    Patient patient2 =
        Patient.builder()
            .name("Name")
            .surname("Surname")
            .middleName("Middle name")
            .address("Address")
            .phone("Phone")
            .messengerContact("Messenger contact")
            .birthDate(LocalDate.of(2023, 2, 1))
            .preferentialCategory("Preferential category")
            .build();
    patientRepository.save(patient);
    patientRepository.save(patient2);
    List<Patient> patients = patientRepository.findAll();
    Assertions.assertNotNull(patients);
    Assertions.assertEquals(2, patients.size());
  }

  @Test
  public void PatientRepository_FindById_ReturnsPatient() {
    Patient patient =
        Patient.builder()
            .name("Name")
            .surname("Surname")
            .middleName("Middle name")
            .address("Address")
            .phone("Phone")
            .messengerContact("Messenger contact")
            .birthDate(LocalDate.of(2023, 2, 1))
            .preferentialCategory("Preferential category")
            .build();
    patientRepository.save(patient);
    Patient patientById = patientRepository.findById(patient.getId()).get();
    Assertions.assertNotNull(patientById);
  }

  @Test
  public void PatientRepository_DeleteById_ReturnsEmpty() {
    Patient patient =
        Patient.builder()
            .name("Name")
            .surname("Surname")
            .middleName("Middle name")
            .address("Address")
            .phone("Phone")
            .messengerContact("Messenger contact")
            .birthDate(LocalDate.of(2023, 2, 1))
            .preferentialCategory("Preferential category")
            .build();
    patientRepository.save(patient);
    patientRepository.deleteById(patient.getId());
    Optional<Patient> patientReturn = patientRepository.findById(patient.getId());
    Assertions.assertTrue(patientReturn.isEmpty());
  }
}
