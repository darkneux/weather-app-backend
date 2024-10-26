package com.example.weathermonitoring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long cityId;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    private double temp;
    private double feelsLike;
    private double pressure;
    private int humidity;

    @Column(nullable = false)
    private String weatherMain;

    private String weatherDescription;


}
