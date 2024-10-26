package com.example.weathermonitoring.mappers;

import com.example.weathermonitoring.dto.OpenWeatherMapApiResponse;
import com.example.weathermonitoring.dto.WeatherApiResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WeatherMapper {

    public WeatherApiResponse mapToInternal(OpenWeatherMapApiResponse apiResponse) {
        WeatherApiResponse internalResponse = new WeatherApiResponse();

        OpenWeatherMapApiResponse.Current current = apiResponse.getCurrent();

        if (current != null) {
            internalResponse.setDt(current.getDt());
            internalResponse.setTemp(current.getTemp());
            internalResponse.setFeelsLike(current.getFeels_like());
            internalResponse.setPressure(current.getPressure());
            internalResponse.setHumidity(current.getHumidity());
            if (current.getWeather() != null && !current.getWeather().isEmpty()) {
                OpenWeatherMapApiResponse.Current.Weather weatherCondition = current.getWeather().get(0);
                internalResponse.setWeatherMain(weatherCondition.getMain());
                internalResponse.setWeatherDescription(weatherCondition.getDescription());
            }
        }

        return internalResponse;
    }
}
