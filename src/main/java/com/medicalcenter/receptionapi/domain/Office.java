package com.medicalcenter.receptionapi.domain;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "office")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Office {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "number", nullable = false)
  private Integer number;

  @Column(name = "name", nullable = false)
  private String name;

  @OneToMany(mappedBy = "office", orphanRemoval = false)
  private Set<Doctor> doctors = new LinkedHashSet<>();
}
