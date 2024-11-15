package dev.cjsun.weatherapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WeatherNotFoundException extends RuntimeException {

    public WeatherNotFoundException(String message) {
        super(message);
    }
}
