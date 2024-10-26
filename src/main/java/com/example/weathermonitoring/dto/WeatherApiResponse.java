package com.example.weathermonitoring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherApiResponse {
    private long dt;
    private double temp;
    private double feelsLike;
    private double pressure;
    private int humidity;
    private String weatherMain;
    private String weatherDescription;
}
