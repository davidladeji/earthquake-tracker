package com.dladeji.earthquake;

import com.dladeji.earthquake.dtos.QuakeDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pair{
    private QuakeDto q1;
    private QuakeDto q2;
    private double distance;
}

// May need to make this accept generic objects
// May need to transition away from Pair in the future