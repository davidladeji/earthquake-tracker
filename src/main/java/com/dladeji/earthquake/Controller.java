package com.dladeji.earthquake;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.dladeji.earthquake.dtos.ApiRequest;
import com.dladeji.earthquake.dtos.ApiResponse;



@RestController
public class Controller {

    private final WebClient webClient;
    private final String BASEURL = "https://earthquake.usgs.gov/fdsnws/event/1/";
    private final String simpleQueryUri = "/query?format=geojson&starttime=2025-06-20&endtime=2026-06-20&alertlevel=green";

    public Controller(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BASEURL).build();
    }

    @GetMapping("/url")
    public ApiResponse fetchApiData(@RequestBody ApiRequest request) {
        var response = webClient.get()
                .uri(simpleQueryUri)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .block();

        // var quakeData = response.getFeatures();
        // for (int i=0; i<quakeData.length; i++){
        //     epochTimeConverter(quakeData[i].getProperties().getTime());
        // }
        
        return response;
    }

    // Method prints out Time objects, doesn't return it
    private void epochTimeConverter(long epochTimeMillis) {
        // 1. Create an Instant from the epoch milliseconds
        Instant instant = Instant.ofEpochMilli(epochTimeMillis);

        // 2. Convert the Instant to a LocalDateTime in a specific time zone
        //    Using ZoneId.systemDefault() for the local system's default time zone
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();

        // 3. (Optional) Format the LocalDateTime for display
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);

        // System.out.println("Epoch time (milliseconds): " + epochTimeMillis);
        System.out.println("Converted LocalDateTime: " + localDateTime);
        System.out.println("Formatted LocalDateTime: " + formattedDateTime);
    }
    
}

    
        
    