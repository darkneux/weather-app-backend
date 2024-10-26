package com.example.weathermonitoring.services;

import com.example.weathermonitoring.alerts.AlertNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class WeatherMonitoringService {

    private AlertNotification alertNotification;


    public WeatherMonitoringService(AlertNotification alertNotification) {
        this.alertNotification = alertNotification;
    }

    public void checkWeatherAndSendAlert(double temperature) {
        if (temperature > 35) {
            alertNotification.sendAlert("Temperature exceeded 35 degrees Celsius!");
        }

    }
}
