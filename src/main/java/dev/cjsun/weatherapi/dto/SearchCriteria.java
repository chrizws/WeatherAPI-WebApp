package dev.cjsun.weatherapi.dto;

public record SearchCriteria(
    String location,
    String days,
    String aqi,
    String alerts
) {
}
