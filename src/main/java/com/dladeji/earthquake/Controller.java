package com.dladeji.earthquake;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dladeji.earthquake.dtos.QuakeCountDto;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
public class Controller {

    private final ApiService apiService;

    // TODO: Make fetch occur at start of app
    @GetMapping("/url")
    public ResponseEntity<?> fetchQuakes() {
        // TODO: Return Better Success Message
        var responseSize = apiService.fetchDataFromApi();
        return ResponseEntity.ok().body(responseSize);
    }
    
    
    @GetMapping("/getCounts")
    public ResponseEntity<List<QuakeCountDto>> getQuakeCounts() {
        var result = apiService.getQuakeCounts();
        return ResponseEntity.ok().body(result);
    }
}

    
        
    