package com.example.weathermonitoring.services;

import com.example.weathermonitoring.adapters.WeatherServicePort;
import com.example.weathermonitoring.dto.WeatherApiResponse;
import com.example.weathermonitoring.entity.WeatherData;
import com.example.weathermonitoring.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class WeatherService {

    private final WeatherServicePort weatherServicePort;
    private final WeatherDataRepository weatherDataRepository;

    @Autowired
    public WeatherService(WeatherServicePort weatherServicePort, WeatherDataRepository weatherDataRepository) {
        this.weatherServicePort = weatherServicePort;
        this.weatherDataRepository = weatherDataRepository;
    }

    public WeatherData fetchAndSaveWeatherData(double lat, double lon, String exclude ,Long cityId) {

        WeatherApiResponse apiResponse = weatherServicePort.fetchWeatherData(lat, lon, exclude);

        WeatherData weatherData = new WeatherData();
        weatherData.setCityId(cityId);
        weatherData.setTimestamp(LocalDateTime.now());
        weatherData.setTemp(apiResponse.getTemp());
        weatherData.setFeelsLike(apiResponse.getFeelsLike());
        weatherData.setPressure(apiResponse.getPressure());
        weatherData.setHumidity(apiResponse.getHumidity());
        weatherData.setWeatherMain(apiResponse.getWeatherMain());
        weatherData.setWeatherDescription(apiResponse.getWeatherDescription());

        return weatherDataRepository.save(weatherData);
    }
}
