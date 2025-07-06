package com.dladeji.earthquake.entities;

import com.dladeji.earthquake.dtos.QuakeDisplayDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pair{
    private QuakeDisplayDto q1;
    private QuakeDisplayDto q2;
    private double distance;
}

// May need to make this accept generic objects
// May need to transition away from Pair in the future