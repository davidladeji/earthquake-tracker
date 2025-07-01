package com.dladeji.earthquake.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dladeji.earthquake.QuakeRepository;
import com.dladeji.earthquake.dtos.QuakeCountDto;
import com.dladeji.earthquake.dtos.QuakeDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuakeService {
    private final double HIGH_MAGNITUDE_VAL = 5;
    private final QuakeRepository quakeRepository;

    public List<QuakeCountDto> getQuakeCounts(){
        return quakeRepository.getCountByType();
    }

    public List<QuakeDto> getHighMagQuakes(){
        var result = quakeRepository.findByMagGreaterThan(HIGH_MAGNITUDE_VAL);
        return result;
    }
}
