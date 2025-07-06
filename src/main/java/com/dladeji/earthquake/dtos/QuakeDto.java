package com.dladeji.earthquake.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuakeDto{
    private double mag;
    private String title;
    private String type;
    private LocalDateTime time;
    private String place;
    private double latitude;
    private double longtitude;
}