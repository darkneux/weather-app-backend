// File: /home/darkneux/Desktop/weathermonitoring/src/main/java/com/example/weathermonitoring/domain/CityCoordinatesProvider.java
package com.example.weathermonitoring.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CityCoordinatesProvider {

    private final Map<String, CityCoordinates> cities = new HashMap<>();

    public CityCoordinatesProvider() {
        // Add city coordinates manually here
        cities.put("delhi", new CityCoordinates(28.6139, 77.2090));
//        cities.put("mumbai", new CityCoordinates(19.0760, 72.8777));
//        cities.put("chennai", new CityCoordinates(13.0827, 80.2707));
//        cities.put("bangalore", new CityCoordinates(12.9716, 77.5946));
//        cities.put("kolkata", new CityCoordinates(22.5726, 88.3639));
//        cities.put("hyderabad", new CityCoordinates(17.3850, 78.4867));
    }

    public Map<String, CityCoordinates> getCities() {
        return cities;
    }

    @Data
    public static class CityCoordinates {
        private final double latitude;
        private final double longitude;
    }
}
