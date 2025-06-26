package com.dladeji.earthquake;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.dladeji.earthquake.dtos.ApiResponse;
import com.dladeji.earthquake.dtos.Feature;

import lombok.AllArgsConstructor;



@RestController
@AllArgsConstructor
public class Controller {

    private final WebClient webClient = WebClient.create("https://earthquake.usgs.gov/fdsnws/event/1/");
    private final String simpleQueryUri = "/query?format=geojson&starttime=2025-06-20&endtime=2026-06-20&alertlevel=green";
    private final QuakeRepository quakeRepository;
    

    @GetMapping("/url")
    public ApiResponse fetchApiData() {
        var response = webClient.get()
                .uri(simpleQueryUri)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .block();

        createEarthquakeInstances(response.getFeatures());
        
        return response;
    }

    

    private void createEarthquakeInstances(Feature[] quakeData){
        for (int i=0; i<quakeData.length; i++){
            var quake = new Quake(quakeData[i].getProperties());
            quakeRepository.save(quake);
        }
    }
    
}

    
        
    