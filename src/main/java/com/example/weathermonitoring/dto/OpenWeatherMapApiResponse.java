package com.example.weathermonitoring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenWeatherMapApiResponse {
    private Current current; // Include current

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Current {
        private long dt;
        private double temp;
        private double feels_like;
        private double pressure;
        private int humidity;
        private List<Weather> weather;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Weather {
            private String main;
            private String description;
        }
    }
}
