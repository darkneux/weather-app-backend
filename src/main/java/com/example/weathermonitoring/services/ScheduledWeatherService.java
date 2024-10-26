package com.example.weathermonitoring.services;

import com.example.weathermonitoring.domain.CityCoordinatesProvider;
import com.example.weathermonitoring.entity.City;
import com.example.weathermonitoring.entity.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Map;

@Service
public class ScheduledWeatherService {

    private final WeatherService weatherService;
    private final CityCoordinatesProvider cityCoordinatesProvider;
    private final ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    public ScheduledWeatherService(WeatherService weatherService, CityCoordinatesProvider cityCoordinatesProvider, ThreadPoolTaskScheduler taskScheduler) {
        this.weatherService = weatherService;
        this.cityCoordinatesProvider = cityCoordinatesProvider;
        this.taskScheduler = taskScheduler;
    }

    @PostConstruct
    public void scheduleWeatherUpdates() {
        for (Map.Entry<String, CityCoordinatesProvider.CityCoordinates> entry : cityCoordinatesProvider.getCities().entrySet()) {
            String cityName = entry.getKey();
            CityCoordinatesProvider.CityCoordinates coordinates = entry.getValue();

            taskScheduler.scheduleAtFixedRate(() -> fetchWeatherForCity(cityName, coordinates),
                    Duration.ofMinutes(5));
        }
    }

    private void fetchWeatherForCity(String cityName, CityCoordinatesProvider.CityCoordinates coordinates) {


        try {
            WeatherData weatherData = weatherService.fetchAndSaveWeatherData(coordinates.getLatitude(), coordinates.getLongitude(), "hourly",1L);
            System.out.println("Fetched and saved weather for " + cityName + ": " + weatherData);
        } catch (Exception e) {
            System.out.println("Error fetching weather for " + cityName + ": " + e.getMessage());
        }
    }
}
