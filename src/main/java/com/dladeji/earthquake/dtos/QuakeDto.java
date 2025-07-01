package com.dladeji.earthquake.dtos;

import java.math.BigDecimal;

public interface QuakeDto {
    String getTitle();
    double getMag();
    String getPlace();
    BigDecimal getLatitude();
    BigDecimal getLongtitude();
    // depth needed??
}
