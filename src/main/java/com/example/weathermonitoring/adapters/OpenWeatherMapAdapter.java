package com.example.weathermonitoring.adapters;

import com.example.weathermonitoring.dto.OpenWeatherMapApiResponse;
import com.example.weathermonitoring.dto.WeatherApiResponse;
import com.example.weathermonitoring.mappers.WeatherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenWeatherMapAdapter implements WeatherServicePort {

    private final RestTemplate restTemplate;
    private final WeatherMapper weatherMapper;

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    public OpenWeatherMapAdapter(RestTemplate restTemplate, WeatherMapper weatherMapper) {
        this.restTemplate = restTemplate;
        this.weatherMapper = weatherMapper; // Initialize WeatherMapper
    }

    @Override
    public WeatherApiResponse fetchWeatherData(double lat, double lon, String exclude) {
        String url = String.format("%s?lat=%f&lon=%f&exclude=%s&appid=%s",
                weatherApiUrl, lat, lon, exclude, apiKey);

        System.out.println("Requesting Weather Data from URL: " + url);


        OpenWeatherMapApiResponse apiResponse = restTemplate.getForObject(url, OpenWeatherMapApiResponse.class);

        System.out.println(apiResponse);


        if (apiResponse != null && apiResponse.getCurrent() != null) {
            return weatherMapper.mapToInternal(apiResponse);
        } else {
            System.out.println("Error: Response was null or invalid.");
            return null;
        }
    }



}
