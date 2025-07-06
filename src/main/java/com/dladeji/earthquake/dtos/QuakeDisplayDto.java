package com.dladeji.earthquake.dtos;

import java.time.LocalDateTime;

public interface QuakeDisplayDto {
    String getTitle();
    String getPlace();
    double getMag();
    LocalDateTime getTime();
    String getType();
    double getLatitude();
    double getLongtitude();
}
