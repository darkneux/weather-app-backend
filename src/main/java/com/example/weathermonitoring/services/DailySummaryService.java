package com.example.weathermonitoring.services;

import com.example.weathermonitoring.entity.DailyWeatherSummary;
import com.example.weathermonitoring.entity.WeatherData;
import com.example.weathermonitoring.repository.DailyWeatherSummaryRepository;
import com.example.weathermonitoring.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DailySummaryService {

    private final WeatherDataRepository weatherDataRepository;
    private final DailyWeatherSummaryRepository dailySummaryRepository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);


    public DailySummaryService(WeatherDataRepository weatherDataRepository, DailyWeatherSummaryRepository dailySummaryRepository) {
        this.weatherDataRepository = weatherDataRepository;
        this.dailySummaryRepository = dailySummaryRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void generateDailySummaries() {
        executorService.submit(this::calculateDailySummaries);
    }

    private void calculateDailySummaries() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        // Iterate through each city
        List<Long> cityIds = weatherDataRepository.findDistinctCityIds();

        for (Long cityId : cityIds) {
            List<WeatherData> dailyData = weatherDataRepository.findByCityIdAndTimestampBetween(cityId, yesterday.atStartOfDay(), yesterday.plusDays(1).atStartOfDay());

            if (!dailyData.isEmpty()) {
                double avgTemp = dailyData.stream().mapToDouble(WeatherData::getTemp).average().orElse(0.0);
                double maxTemp = dailyData.stream().mapToDouble(WeatherData::getTemp).max().orElse(0.0);
                double minTemp = dailyData.stream().mapToDouble(WeatherData::getTemp).min().orElse(0.0);
                String dominantWeather = getDominantWeather(dailyData);

                DailyWeatherSummary summary = new DailyWeatherSummary(cityId, yesterday, avgTemp, maxTemp, minTemp, dominantWeather);
                dailySummaryRepository.save(summary);
            }
        }
    }

    private String getDominantWeather(List<WeatherData> dailyData) {
        return dailyData.stream()
                .map(WeatherData::getWeatherMain)
                .reduce((a, b) -> a.equals(b) ? a : b)
                .orElse("Unknown");
    }
}
