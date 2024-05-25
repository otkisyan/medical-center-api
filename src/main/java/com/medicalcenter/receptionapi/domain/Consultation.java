package com.medicalcenter.receptionapi.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "consultation")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Consultation {

    @Id
    @Column(name = "appointment_id", nullable = false)
    private Long id;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "symptoms")
    private String symptoms;

    @Column(name = "medical_recommendations")
    private String medicalRecommendations;

    @OneToOne(cascade = CascadeType.REMOVE)
    @MapsId
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
}
