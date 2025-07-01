package com.dladeji.earthquake.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dladeji.earthquake.Pair;
import com.dladeji.earthquake.QuakeRepository;
import com.dladeji.earthquake.dtos.QuakeCountDto;
import com.dladeji.earthquake.dtos.QuakeDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuakeService {
    private final double HIGH_MAGNITUDE_VAL = 5;
    private final double EARTH_RADIUS_KM = 6371;
    private final double CLOSE_PROXIMITY_DIST = 50;
    private final QuakeRepository quakeRepository;

    /* TODO: Create a logger, especially for SQL queries */

    public List<QuakeCountDto> getQuakeCounts(){
        return quakeRepository.getCountByType();
    }

    public List<Pair> getHighMagQuakes(){
        var highMagnQuakes = quakeRepository.findByMagGreaterThan(HIGH_MAGNITUDE_VAL);
        var result = getCloseQuakePairings(highMagnQuakes);
        return result;
    }

    public List<Pair> getCloseQuakePairings(List<QuakeDto> quakes){
        List<Pair> closeQuakes = new ArrayList<>();
        for (int i=0; i<quakes.size(); i++){
            for (int j=i+1; j<quakes.size(); j++){
                QuakeDto quake1 = quakes.get(i);
                QuakeDto quake2 = quakes.get(j);

                if (Math.floor(quake1.getLatitude()) == Math.floor(quake2.getLatitude()) && Math.floor(quake1.getLongtitude()) == Math.floor(quake2.getLongtitude())){
                    double distance = getProximityBetweenPoints(quake1, quake2);
                    if (distance < CLOSE_PROXIMITY_DIST)
                        closeQuakes.add(new Pair(quake1, quake2, distance));
                }
            }
        }

        return closeQuakes;
    }

    // Haversine function
    private double getProximityBetweenPoints(QuakeDto q1, QuakeDto q2) {
        double lat1 = Math.toRadians(q1.getLatitude());
        double lon1 = Math.toRadians(q1.getLongtitude());
        double lat2 = Math.toRadians(q2.getLatitude());
        double lon2 = Math.toRadians(q2.getLongtitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.pow(Math.sin(dLat / 2), 2)
                 + Math.cos(lat1) * Math.cos(lat2)
                 * Math.pow(Math.sin(dLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }
}
