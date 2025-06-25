package com.dladeji.earthquake.dtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApiResponse {
    private String type;
    private Object metadata;
    private Feature[] features;
    private int[] bbox;
}

