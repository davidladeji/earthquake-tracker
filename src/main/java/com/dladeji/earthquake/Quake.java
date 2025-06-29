package com.dladeji.earthquake;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
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

    @Column(name = "quake_key")
    private String quakeKey;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longtitude")
    private BigDecimal longtitude;

    @Column(name = "depth")
    private double depth;

    public Quake(Feature featureObj){
        Map<String, Object> quakeData = featureObj.getProperties();
        Map<String, Object> quakeLocationData = featureObj.getGeometry();

        this.country = "N/A";
        this.city = "N/A";
        this.title = (String)quakeData.get("title");
        this.mag = Double.valueOf(String.valueOf(quakeData.get("mag")));
        this.type = (String)quakeData.get("type");
        this.place = (String)quakeData.get("place");
        this.time = epochTimeConverter((long)quakeData.get("time"));
        this.quakeKey = title + String.valueOf(quakeData.get("time"));

        // TODO: Multiple casts need multiple error messages. Think of extracting to another func
        List<?> coordinateArr = (List<?>) quakeLocationData.get("coordinates");
        if (coordinateArr != null && coordinateArr.size() >= 3) {
            this.latitude = new BigDecimal(String.valueOf(coordinateArr.get(1)));
            this.longtitude = new BigDecimal(String.valueOf(coordinateArr.get(0)));
            this.depth = Double.valueOf(String.valueOf(coordinateArr.get(2)));
        } else {
            System.out.println("Coordinate Array error occured");
        }

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
