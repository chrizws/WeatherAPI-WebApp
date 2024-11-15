package dev.cjsun.weatherapi.mapper;

import dev.cjsun.weatherapi.dto.ForecastDto;
import dev.cjsun.weatherapi.dto.WeatherDto;
import dev.cjsun.weatherapi.model.Day;
import dev.cjsun.weatherapi.model.Weather;

import java.util.*;

public class DtoMapperToWeather {

    public static Weather mapToWeather(WeatherDto dto) {

            Weather weather = Weather.builder()
                    .days(createDaysEntity(dto.forecast()))
                    .name(dto.location().name())
                    .region(dto.location().region())
                    .country(dto.location().country())
                    .lat(dto.location().lat())
                    .lon(dto.location().lon())
                    .timezone(dto.location().tz_id())
                    .local_time(dto.location().localtime())
                    .last_updated(dto.current().last_updated())
                    .temp_c(dto.current().temp_c())
                    .temp_f(dto.current().temp_f())
                    .humidity(dto.current().humidity())
                    .weatherIcon(dto.current().condition().icon())
                    .weatherStatus(dto.current().condition().text())
                    .build();

            return weather;
        }

        private static List<Day> createDaysEntity(ForecastDto forecastDto) {

            List<Day> days = new ArrayList<>();
            forecastDto.forecastdays().forEach(forecastday -> {
                Day day = Day.builder()
                        .date(forecastday.date())
                        //.weather(weather)
                        .avghumidity(forecastday.day().avghumidity())
                        .avgtemp_c(forecastday.day().avgtemp_c())
                        .avgtemp_f(forecastday.day().avgtemp_f())
                        .daily_chance_of_rain(forecastday.day().daily_chance_of_rain())
                        .daily_chance_of_snow(forecastday.day().daily_chance_of_snow())
                        .maxtemp_c(forecastday.day().maxtemp_c())
                        .maxtemp_f(forecastday.day().maxtemp_f())
                        .mintemp_c(forecastday.day().mintemp_c())
                        .mintemp_f(forecastday.day().mintemp_f())
                        .totalprecip_mm(forecastday.day().totalprecip_mm())
                        .totalsnow_cm(forecastday.day().totalsnow_cm())
                        .weatherIcon(forecastday.day().condition().icon())
                        .weatherStatus(forecastday.day().condition().text())
                        .weatherCode(forecastday.day().condition().code())
                        .build();

                days.add(day);
            });
            return days;
        }
}
