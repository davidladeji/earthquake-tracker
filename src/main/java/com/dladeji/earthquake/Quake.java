package com.dladeji.earthquake;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.dladeji.earthquake.dtos.Property;

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

    public Quake(Property quakeData){
        this.country = "N/A";
        this.city = "N/A";
        this.title = quakeData.getTitle();
        this.mag = quakeData.getMag();
        this.type = quakeData.getType();
        this.place = quakeData.getPlace();
        this.time = epochTimeConverter(quakeData.getTime());

        // Parse place value
        String[] places = place.split(",");
        if (places.length > 1){
            this.country = places[1].trim();
            this.city = places[0].split("of")[1].trim(); 
        }
    }

    // Method prints out Time objects, doesn't return it
    private LocalDateTime epochTimeConverter(long epochTimeMillis) {
        // 1. Create an Instant from the epoch milliseconds
        Instant instant = Instant.ofEpochMilli(epochTimeMillis);

        // 2. Convert the Instant to a LocalDateTime in a specific time zone
        //    Using ZoneId.systemDefault() for the local system's default time zone
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

        // 3. (Optional) Format the LocalDateTime for display
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);

        // System.out.println("Epoch time (milliseconds): " + epochTimeMillis);
        // System.out.println("Converted LocalDateTime: " + localDateTime);
        // System.out.println("Formatted LocalDateTime: " + formattedDateTime);
        return localDateTime;
    }

}
