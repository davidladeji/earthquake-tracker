package com.dladeji.earthquake.dtos;

public interface QuakeDto {
    String getTitle();
    double getMag();
    String getPlace();
    double getLatitude();
    double getLongtitude();
    // depth needed??
}
