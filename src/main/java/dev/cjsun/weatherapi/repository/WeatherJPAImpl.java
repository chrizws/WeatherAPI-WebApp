package dev.cjsun.weatherapi.repository;

import dev.cjsun.weatherapi.exception.WeatherNotFoundException;
import dev.cjsun.weatherapi.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class WeatherJPAImpl implements WeatherDao {

    private final WeatherRepository weatherRepository;

    @Autowired
    public WeatherJPAImpl(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Override
    public Weather saveWeather(Weather weather) {
        return weatherRepository.save(weather);
    }

    @Override
    public List<Weather> saveAllWeather(List<Weather> weather) {
        return weatherRepository.saveAll(weather);
    }

    @Override
    public Optional<Weather> getWeatherById(Long id) {
        return weatherRepository.findById(id);
    }

    @Override
    public List<Weather> getAllWeather() {
        return weatherRepository.findAll();
    }

    @Override
    public boolean deleteWeatherById(Long id) {
        try {
            weatherRepository.deleteById(id);
            return true;
        } catch(Exception e) {
            throw new WeatherNotFoundException("Delete Failed - Weather Not Found");
        }
    }

    @Override
    public Weather updateWeather(Long id, Weather weather) {
        return weatherRepository.save(weather);
    }
}
