package dev.cjsun.weatherapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherDto(

        Location location,
        Current current,
        @JsonProperty("forecast")
        ForecastDto forecast
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Location (
        String name,
        String region,
         String country,
         double lat,
         double lon,
         String tz_id,
         String localtime
        ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Current (
        String last_updated,
        double temp_c,
        double temp_f,
        int is_day,
        double humidity,
        @JsonProperty("condition")
        ConditionDto condition
){}
}
