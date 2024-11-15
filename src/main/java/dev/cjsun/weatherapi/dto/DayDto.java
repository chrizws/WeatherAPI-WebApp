package dev.cjsun.weatherapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public record DayDto(
        double maxtemp_c,
        double maxtemp_f,
        double mintemp_c,
        double mintemp_f,
        double avgtemp_c,
        double avgtemp_f,
        double totalsnow_cm,
        double totalprecip_mm,
        double avghumidity,
        double daily_chance_of_rain,
        double daily_chance_of_snow,

        @JsonProperty("condition")
        ConditionDto condition
) {
}
