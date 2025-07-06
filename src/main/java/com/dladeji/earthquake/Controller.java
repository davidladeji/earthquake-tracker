package com.dladeji.earthquake;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dladeji.earthquake.dtos.QuakeCountDto;
import com.dladeji.earthquake.services.ApiService;
import com.dladeji.earthquake.services.QuakeService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@AllArgsConstructor
public class Controller {
    private final QuakeService quakeService;
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
        var result = quakeService.getQuakeCounts();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/mags")
    public ResponseEntity<?> getHighMagQuakes() {
        var result = quakeService.getHighMagQuakes();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/getQuakes")
    public ResponseEntity<?> getAllQuakes() {
        var response = quakeService.getAllQuakes();
        return ResponseEntity.ok().body(response);
    }
    
    
}

    
        
    