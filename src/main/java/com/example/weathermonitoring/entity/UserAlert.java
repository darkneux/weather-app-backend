package com.example.weathermonitoring.entity;

import com.example.weathermonitoring.alerts.UserAlertConditionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_alerts",
        indexes = {
                @Index(name = "idx_condition_type", columnList = "condition_type"),
                @Index(name = "idx_threshold", columnList = "threshold")
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "condition_type"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_type", nullable = false)
    private UserAlertConditionType conditionType;

    @Column(name = "threshold", nullable = false)
    private Double threshold;
}
