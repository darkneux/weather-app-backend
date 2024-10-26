package com.example.weathermonitoring.repository;

import com.example.weathermonitoring.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    List<WeatherData> findByCityIdAndTimestampBetween(Long cityId, LocalDate startOfDay, LocalDate endOfDay);

    List<WeatherData> findByCityIdAndTimestampBetween(Long cityId, LocalDateTime localDateTime, LocalDateTime localDateTime1);

    @Query("SELECT DISTINCT wd.cityId FROM WeatherData wd")
    List<Long> findDistinctCityIds();
}