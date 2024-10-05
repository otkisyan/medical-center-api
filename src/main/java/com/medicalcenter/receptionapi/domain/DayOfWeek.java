package com.medicalcenter.receptionapi.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "day_of_week")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayOfWeek {
  @Id
  @Column(name = "day_number", nullable = false)
  private Integer id;

  @Column(name = "name", nullable = false, length = 30)
  private String name;

  @OneToMany(mappedBy = "dayOfWeek", orphanRemoval = true)
  private List<WorkSchedule> workSchedules = new ArrayList<>();
}
