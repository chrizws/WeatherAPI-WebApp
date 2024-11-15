package dev.cjsun.weatherapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cjsun.weatherapi.dto.SearchCriteria;
import dev.cjsun.weatherapi.dto.WeatherDto;
import dev.cjsun.weatherapi.mapper.DtoMapperTest;
import dev.cjsun.weatherapi.model.Weather;
import dev.cjsun.weatherapi.service.WeatherService;
import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = WeatherController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
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
    void getAllWeather() throws Exception {
        List<Weather> weatherList = new ArrayList<>(List.of(DtoMapperTest.mapToWeather(weatherDto)));
        when(weatherService.getAllWeather())
                .thenReturn(weatherList);

        mockMvc.perform(get("/api/getAllWeather"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is(weatherDto.location().name())));
    }

    @Test
    void getWeather() throws Exception {
        SearchCriteria searchCriteria = new SearchCriteria(
                "Oavkille",
                "5",
                "no",
                "no"
        );

        String searchCriteriaJson = new ObjectMapper().writeValueAsString(searchCriteria);

        Weather weather = DtoMapperTest.mapToWeather(weatherDto);

        when(weatherService.getWeather(Mockito.any(SearchCriteria.class))).thenReturn(weather);

        ResultActions response = mockMvc.perform(post("/api/getWeather")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(searchCriteriaJson));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(weatherDto.location().name())));

    }

    @Test
    void deleteWeatherById() throws Exception {

        when(weatherService.deleteWeatherById(ArgumentMatchers.anyLong()))
                .thenReturn(true);

        ResultActions response = mockMvc.perform(delete("/api/delete/{id}", 123L));

        response.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());

    }
}
