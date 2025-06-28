package com.dladeji.earthquake;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

import com.dladeji.earthquake.dtos.Feature;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "earthquakes")
@Entity
public class Quake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "mag")
    private double mag;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private String type;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "place")
    private String place;

    public Quake(Feature featureObj){
        Map<String, Object> quakeData = featureObj.getProperties();

        this.country = "N/A";
        this.city = "N/A";
        this.title = (String)quakeData.get("title");
        this.mag = Double.valueOf(String.valueOf(quakeData.get("mag")));
        this.type = (String)quakeData.get("type");
        this.place = (String)quakeData.get("place");
        this.time = epochTimeConverter((long)quakeData.get("time"));

        // Parse place value
        String[] places = place.split(",");
        if (places.length > 1){
            this.country = places[1].trim();
            // City isn't really important right now
        }
    }

    private LocalDateTime epochTimeConverter(long epochTimeMillis) {
        Instant instant = Instant.ofEpochMilli(epochTimeMillis);
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime;
    }

}
