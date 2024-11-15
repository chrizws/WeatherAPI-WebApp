package dev.cjsun.weatherapi.proxy;

import dev.cjsun.weatherapi.dto.WeatherDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="current", url="${weather.url}")
public interface WeatherProxy {

    @GetMapping("/v1/forecast.json")
    WeatherDto getForecastWeather(@RequestParam String key,
                                  @RequestParam String q,
                                  @RequestParam String aqi,
                                  @RequestParam String alerts,
                                  @RequestParam String days);
}
