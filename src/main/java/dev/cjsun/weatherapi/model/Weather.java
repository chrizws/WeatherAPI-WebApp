package dev.cjsun.weatherapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @CreationTimestamp
//    private LocalDateTime createdOn;

//    @UpdateTimestamp
//    private LocalDateTime updatedOn;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="weather_id")
    //@OnDelete(action = OnDeleteAction.CASCADE)
    private List<Day> days = new ArrayList<>();

    private String name;
    private String region;
    private String country;
    private double lat;
    private double lon;
    private String timezone;
    private String local_time;
    private String last_updated;
    private double temp_c;
    private double temp_f;
    private double humidity;
    private String weatherStatus;
    private String weatherIcon;

}
