package dev.cjsun.weatherapi.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cjsun.weatherapi.dto.WeatherDto;
import dev.cjsun.weatherapi.mapper.DtoMapperTest;
import dev.cjsun.weatherapi.model.Weather;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.io.File;
import java.io.IOException;
import java.util.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class WeatherRepositoryTest {

    private static WeatherDto weatherDto;

    @Autowired
    private WeatherRepository weatherRepository;

    @BeforeAll
    public static void setupDto_fromJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        weatherDto = mapper.readValue(new File("src/test/java/dev/cjsun/weatherapi/resources/weather.json"), WeatherDto.class);
        Assertions.assertThat(weatherDto).isNotNull();
    }

    @Test
    public void testSave_returnWeather() {
        Weather weather = DtoMapperTest.mapToWeather(weatherDto);
        Weather saved = weatherRepository.save(weather);

        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(weather.getName()).isEqualTo(saved.getName());


    }

    @Test
    public void testFindAll_getAllWeather() {

        Weather weather = DtoMapperTest.mapToWeather(weatherDto);
        Weather weather2 = DtoMapperTest.mapToWeather(weatherDto);
        Weather weather3 = DtoMapperTest.mapToWeather(weatherDto);
        weatherRepository.saveAll(List.of(weather, weather2, weather3));

        List<Weather> weatherList = weatherRepository.findAll();

        Assertions.assertThat(weatherList.size()).isEqualTo(3);

    }

    @Test
    public void testDeleteWeather() {
        Weather weather = DtoMapperTest.mapToWeather(weatherDto);
        Weather weather2 = DtoMapperTest.mapToWeather(weatherDto);

        weatherRepository.save(weather);
        weatherRepository.save(weather2);

        Long id = weather.getId();
        Long id2 = weather2.getId();

        weatherRepository.delete(weather);

        Weather deleted = weatherRepository.findById(id).orElse(null);
        Weather notDeleted = weatherRepository.findById(id2).orElse(null);

        Assertions.assertThat(deleted).isNull();

        Assertions.assertThat(notDeleted).isNotNull();

    }

    @Test
    public void testFindById_returnWeather() {
        Weather weather = DtoMapperTest.mapToWeather(weatherDto);
        weatherRepository.save(weather);
        Long id = weather.getId();

        Optional<Weather> found = weatherRepository.findById(id);
        Optional<Weather> notFound =weatherRepository.findById(999L);

        Assertions.assertThat(found).isPresent();
        Assertions.assertThat(found.get().getId()).isEqualTo(id);

        Assertions.assertThat(notFound).isEmpty();
    }

}
