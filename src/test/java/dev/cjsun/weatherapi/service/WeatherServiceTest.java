package dev.cjsun.weatherapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cjsun.weatherapi.dto.SearchCriteria;
import dev.cjsun.weatherapi.dto.WeatherDto;
import dev.cjsun.weatherapi.exception.WeatherNotFoundException;
import dev.cjsun.weatherapi.mapper.DtoMapperTest;
import dev.cjsun.weatherapi.mapper.DtoMapperToWeather;
import dev.cjsun.weatherapi.model.Weather;
import dev.cjsun.weatherapi.proxy.WeatherProxy;
import dev.cjsun.weatherapi.repository.WeatherDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;


import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @Mock
    private WeatherDao weatherRepository;

    @Mock
    private WeatherProxy weatherProxy;

    @InjectMocks
    private WeatherService weatherService;

    private static WeatherDto weatherDto;

    @Value("${weather.api}")
    private String API_KEY;

    @BeforeAll
    public static void setDtoUp() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        weatherDto = mapper.readValue(new File("src/test/java/dev/cjsun/weatherapi/resources/weather.json"), WeatherDto.class);
    }

    @Test
    public void testGetWeather() {
        //given
        SearchCriteria searchCriteria = new SearchCriteria(
                "Oavkille",
                "5",
                "no",
                "no"
        );

        //when
        when(weatherProxy.getForecastWeather(API_KEY,
                searchCriteria.location(),
                searchCriteria.aqi(),
                searchCriteria.alerts(),
                searchCriteria.days()))
                .thenReturn(weatherDto);

        Weather weather = DtoMapperToWeather.mapToWeather(weatherDto);

        when(weatherRepository.saveWeather(Mockito.any(Weather.class))).thenReturn(weather);

        Weather weather2 = weatherService.getWeather(searchCriteria);

        //then
        Assertions.assertThat(weather2).isNotNull();
        Assertions.assertThat(weather2.getName()).isNotEmpty();

    }

    @Test
    public void testSaveWeather() {

        Weather weather = DtoMapperTest.mapToWeather(weatherDto);
        when(weatherRepository.saveWeather(Mockito.any())).thenReturn(weather);

        Weather saved = weatherService.saveWeather(weather);

        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getName()).isNotEmpty();
    }

    @Test
    public void testGetAllWeather() {

        List<Weather> weatherList = new ArrayList<>();
        Weather weather = DtoMapperTest.mapToWeather(weatherDto);
        Weather weather2 = DtoMapperTest.mapToWeather(weatherDto);
        weatherList.add(weather);
        weatherList.add(weather2);

        when(weatherRepository.getAllWeather()).thenReturn(weatherList);

        List<Weather> weatherList2 = weatherService.getAllWeather();

        Assertions.assertThat(weatherList2.size()).isEqualTo(2);

    }

    @Test
    public void testGetWeatherById() {

        Weather weather = DtoMapperTest.mapToWeather(weatherDto);
        weather.setId(123L);
        Weather weather2 = DtoMapperTest.mapToWeather(weatherDto);
        weather2.setId(999L);
        weather2.setName("DC");

        when(weatherRepository.getWeatherById(123L)).thenReturn(Optional.of(weather));
        when(weatherRepository.getWeatherById(999L)).thenReturn(Optional.of(weather2));


        Weather w1 = weatherService.getWeatherById(123L);
        Assertions.assertThat(w1.getId()).isEqualTo(123L);
        Assertions.assertThat(w1.getName()).isNotEmpty();

        Weather w2 = weatherService.getWeatherById(999L);
        Assertions.assertThat(w2.getName()).isNotEmpty();

        Assertions.assertThatThrownBy(() ->
                weatherService.getWeatherById(333L))
                .isInstanceOf(WeatherNotFoundException.class);
    }

    @Test
    public void testDeleteWeatherById() {
        Weather weather = DtoMapperTest.mapToWeather(weatherDto);
        weather.setId(123L);

        when(weatherRepository.deleteWeatherById(123L)).thenReturn(true);
        when(weatherRepository.deleteWeatherById(222L)).thenReturn(false);

        Assertions.assertThat(weatherService.deleteWeatherById(123L)).isTrue();
        Assertions.assertThat(weatherService.deleteWeatherById(222L)).isFalse();
    }
}
