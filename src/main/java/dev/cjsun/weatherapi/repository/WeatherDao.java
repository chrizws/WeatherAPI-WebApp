package dev.cjsun.weatherapi.repository;

import dev.cjsun.weatherapi.model.Weather;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface WeatherDao {

    Weather saveWeather(Weather weather);
    List<Weather> saveAllWeather(List<Weather> weather);
    Optional<Weather> getWeatherById(Long id);
    List<Weather> getAllWeather();
    boolean deleteWeatherById(Long id);
    Weather updateWeather(Long id, Weather weather);
}
