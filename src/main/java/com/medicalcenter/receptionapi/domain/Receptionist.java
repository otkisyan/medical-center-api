package com.medicalcenter.receptionapi.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "receptionist")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Receptionist {
  @Id
  @Column(name = "user_id", nullable = false)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "surname", nullable = false)
  private String surname;

  @Column(name = "middle_name", nullable = false)
  private String middleName;

  @Column(name = "birth_date", nullable = false)
  private LocalDate birthDate;

  @OneToOne(cascade = CascadeType.REMOVE)
  @MapsId
  @JoinColumn(name = "user_id")
  private User user;
}
