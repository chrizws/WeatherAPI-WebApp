package dev.cjsun.weatherapi.repository;

import dev.cjsun.weatherapi.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
}
