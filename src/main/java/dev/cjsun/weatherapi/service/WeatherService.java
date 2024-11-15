package dev.cjsun.weatherapi.service;

import dev.cjsun.weatherapi.dto.SearchCriteria;
import dev.cjsun.weatherapi.dto.WeatherDto;
import dev.cjsun.weatherapi.exception.WeatherNotFoundException;
import dev.cjsun.weatherapi.mapper.DtoMapperToWeather;
import dev.cjsun.weatherapi.model.Weather;
import dev.cjsun.weatherapi.proxy.WeatherProxy;
import dev.cjsun.weatherapi.repository.WeatherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WeatherService {

    private final WeatherDao weatherRepository;
    private final WeatherProxy weatherProxy;

    @Value("${weather.api}")
    private String API_KEY;

    @Autowired
    public WeatherService(WeatherDao weatherRepository, WeatherProxy weatherProxy) {
        this.weatherRepository = weatherRepository;
        this.weatherProxy = weatherProxy;
    }

    public Weather getWeather(SearchCriteria searchCriteria) {
        WeatherDto dto = weatherProxy.getForecastWeather(API_KEY,
                searchCriteria.location(),
                searchCriteria.aqi(),
                searchCriteria.alerts(),
                searchCriteria.days());

        Weather weather = DtoMapperToWeather.mapToWeather(dto);
        return saveWeather(weather);
    }


    public Weather saveWeather(Weather weather) {
        return weatherRepository.saveWeather(weather);
    }


    public List<Weather> getAllWeather() {
        return weatherRepository.getAllWeather();
    }


    public Weather getWeatherById(Long id) {
        Optional<Weather> weather = weatherRepository.getWeatherById(id);
        if (weather.isPresent()) {
            return weather.get();
        }
        throw new WeatherNotFoundException("Weather Not Found");
    }


    public boolean deleteWeatherById(Long id) {
        return weatherRepository.deleteWeatherById(id);
    }
}
