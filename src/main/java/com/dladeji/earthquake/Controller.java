package com.dladeji.earthquake;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;



@RestController
public class Controller {

    private final WebClient webClient;
    private final String BASEURL = "https://earthquake.usgs.gov/fdsnws/event/1/";
    private final String simpleQueryUri = "/query?format=geojson&starttime=2024-01-01&endtime=2024-01-02&alertlevel=green";

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

        var quakeData = response.getFeatures();
        for (int i=0; i<quakeData.length; i++){
            System.out.println(quakeData[i]);
        }
        
        return response;
    }
    
}

    
        
    