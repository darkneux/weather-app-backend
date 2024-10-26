package com.example.weathermonitoring.controllers;

import com.example.weathermonitoring.entity.WeatherData;
import com.example.weathermonitoring.services.WeatherMonitoringService;
import com.example.weathermonitoring.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private final WeatherService weatherService;
    private final WeatherMonitoringService weatherMonitoringService;

    @Autowired
    public WeatherController(WeatherService weatherService, WeatherMonitoringService weatherMonitoringService) {
        this.weatherService = weatherService;
        this.weatherMonitoringService = weatherMonitoringService;
    }

    @GetMapping("/api/weather")
    public WeatherData getWeather(@RequestParam double lat, @RequestParam double lon,
                                  @RequestParam(required = false, defaultValue = "hourly") String exclude) {

        WeatherData weatherData = weatherService.fetchAndSaveWeatherData(lat, lon, exclude,1L);
        weatherMonitoringService.checkWeatherAndSendAlert(weatherData.getTemp());

        return weatherData;
    }
}
