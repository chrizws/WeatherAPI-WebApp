package dev.cjsun.weatherapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Days")
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;
    private double maxtemp_c;
    private double maxtemp_f;
    private double mintemp_c;
    private double mintemp_f;
    private double avgtemp_c;
    private double avgtemp_f;
    private double totalsnow_cm;
    private double totalprecip_mm;
    private double avghumidity;
    private double daily_chance_of_rain;
    private double daily_chance_of_snow;
    private String weatherStatus;
    private String weatherIcon;
    private int weatherCode;
    private String sunrise;
    private String sunset;
    private String moonrise;
    private String moonset;

}
