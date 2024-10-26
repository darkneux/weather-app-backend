package com.example.weathermonitoring.adapters;

import com.example.weathermonitoring.dto.WeatherApiResponse;
public interface WeatherServicePort {
    WeatherApiResponse fetchWeatherData(double lat, double lon, String exclude);
}