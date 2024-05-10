package com.medicalcenter.receptionapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "doctor")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "messenger_contact", nullable = true)
    private String messengerContact;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "medical_specialty", nullable = false)
    private String medicalSpecialty;

    @Column(name = "qualification_category", nullable = true)
    private String qualificationCategory;

    @OneToMany(mappedBy = "doctor", orphanRemoval = true)
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", orphanRemoval = true)
    private List<WorkSchedule> workSchedules = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "office_id")
    private Office office;

    @OneToOne(cascade = CascadeType.REMOVE)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}