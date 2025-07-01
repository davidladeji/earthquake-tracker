package com.dladeji.earthquake;

import org.springframework.stereotype.Component;

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