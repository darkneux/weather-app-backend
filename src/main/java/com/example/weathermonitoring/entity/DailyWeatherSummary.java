package com.example.weathermonitoring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyWeatherSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long cityId;

    @Column(nullable = false)
    private LocalDate date;

    private double avgTemp;
    private double maxTemp;
    private double minTemp;

    @Column(nullable = false)
    private String dominantWeather;

    public DailyWeatherSummary(Long cityId, LocalDate date, double avgTemp, double maxTemp, double minTemp, String dominantWeather) {
        this.cityId = cityId;
        this.date = date;
        this.avgTemp = avgTemp;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.dominantWeather = dominantWeather;
    }
}
