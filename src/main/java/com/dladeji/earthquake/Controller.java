package com.dladeji.earthquake;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;


@RestController
@AllArgsConstructor
public class Controller {

    private final ApiService apiService;

    @GetMapping("/url")
    public ResponseEntity<?> fetchQuakes() {
        // TODO: Return Better Success Message
        var responseSize = apiService.fetchDataFromApi();
        return ResponseEntity.ok().body(responseSize);
    }
    
    
}

    
        
    