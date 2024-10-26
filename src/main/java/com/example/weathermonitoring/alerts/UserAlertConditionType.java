package com.example.weathermonitoring.alerts;

public enum UserAlertConditionType {
    TEMPERATURE,
    RAIN,
    CLOUDS,
    SNOW;


    public static UserAlertConditionType fromString(String conditionType) {
        switch (conditionType.toUpperCase()) {
            case "TEMPERATURE":
                return TEMPERATURE;
            case "RAIN":
                return RAIN;
            case "CLOUDS":
                return CLOUDS;
            case "SNOW":
                return SNOW;
            default:
                throw new IllegalArgumentException("Unknown condition type: " + conditionType);
        }
    }
}
