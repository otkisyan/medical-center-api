package com.medicalcenter.receptionapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "patient")
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
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

  @Column(name = "preferential_category", nullable = true)
  private String preferentialCategory;

  @JsonIgnore
  @OneToMany(mappedBy = "patient", orphanRemoval = true)
  private List<Appointment> appointments = new ArrayList<>();
}
