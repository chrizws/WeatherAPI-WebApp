package dev.cjsun.weatherapi.controller;

import dev.cjsun.weatherapi.dto.SearchCriteria;
import dev.cjsun.weatherapi.model.Weather;
import dev.cjsun.weatherapi.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }



    @GetMapping("/getAllWeather")
    public List<Weather> getAllWeather() {
        return weatherService.getAllWeather();
    }

    @PostMapping("/getWeather")
    public Weather getWeather(@RequestBody SearchCriteria searchCriteria) {
        return weatherService.getWeather(searchCriteria);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean deleteWeatherById(@PathVariable Long id) {
        return weatherService.deleteWeatherById(id);
    }
}
