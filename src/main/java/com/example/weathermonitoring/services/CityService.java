package com.example.weathermonitoring.services;

import com.example.weathermonitoring.domain.CityCoordinatesProvider.CityCoordinates;
import com.example.weathermonitoring.entity.City;
import com.example.weathermonitoring.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }


    @Cacheable("cities")
    public List<String> getAllCities() {
        return cityRepository.findAll().stream()
                .map(City::getName)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "cityCoordinates", key = "#name")
    public CityCoordinates getCityCoordinates(String name) {
        City city = cityRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("City not found: " + name));
        return new CityCoordinates(city.getLatitude(), city.getLongitude());
    }

    @CacheEvict(value = {"cities", "cityCoordinates"}, allEntries = true)
    public void addCity(String name, CityCoordinates coordinates) {
        Optional<City> existingCity = cityRepository.findByName(name);
        if (existingCity.isPresent()) {
            throw new RuntimeException("City already exists: " + name);
        }
        City city = new City();
        city.setName(name);
        city.setLatitude(coordinates.getLatitude());
        city.setLongitude(coordinates.getLongitude());
        cityRepository.save(city);
    }

    @CacheEvict(value = {"cities", "cityCoordinates"}, allEntries = true)
    public boolean updateCityCoordinates(String name, CityCoordinates coordinates) {
        Optional<City> cityOpt = cityRepository.findByName(name);
        if (cityOpt.isPresent()) {
            City city = cityOpt.get();
            city.setLatitude(coordinates.getLatitude());
            city.setLongitude(coordinates.getLongitude());
            cityRepository.save(city);
            return true;
        }
        return false;
    }

    @CacheEvict(value = {"cities", "cityCoordinates"}, allEntries = true)
    public boolean deleteCity(String name) {
        Optional<City> cityOpt = cityRepository.findByName(name);
        if (cityOpt.isPresent()) {
            cityRepository.delete(cityOpt.get());
            return true;
        }
        return false;
    }
}
