package com.example.weathermonitoring.controllers;

import com.example.weathermonitoring.domain.CityCoordinatesProvider.CityCoordinates;
import com.example.weathermonitoring.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllCities() {
        List<String> cities = cityService.getAllCities();
        return ResponseEntity.ok(cities);
    }


    @GetMapping("/{name}")
    public ResponseEntity<CityCoordinates> getCityCoordinates(@PathVariable String name) {
        CityCoordinates coordinates = cityService.getCityCoordinates(name.toLowerCase());
        return coordinates != null ? ResponseEntity.ok(coordinates) : ResponseEntity.notFound().build();
    }


    @PostMapping
    public ResponseEntity<String> addCity(@RequestBody Map<String, Object> cityDetails) {
        String name = ((String) cityDetails.get("name")).toLowerCase();
        double latitude = ((Number) cityDetails.get("latitude")).doubleValue();
        double longitude = ((Number) cityDetails.get("longitude")).doubleValue();

        cityService.addCity(name, new CityCoordinates(latitude, longitude));
        return ResponseEntity.ok("City added successfully.");
    }

    @PutMapping("/{name}")
    public ResponseEntity<String> updateCityCoordinates(@PathVariable String name, @RequestBody Map<String, Object> updatedDetails) {
        double latitude = ((Number) updatedDetails.get("latitude")).doubleValue();
        double longitude = ((Number) updatedDetails.get("longitude")).doubleValue();

        boolean updated = cityService.updateCityCoordinates(name.toLowerCase(), new CityCoordinates(latitude, longitude));
        return updated ? ResponseEntity.ok("City coordinates updated successfully.") : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteCity(@PathVariable String name) {
        boolean deleted = cityService.deleteCity(name.toLowerCase());
        return deleted ? ResponseEntity.ok("City deleted successfully.") : ResponseEntity.notFound().build();
    }
}
