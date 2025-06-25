package com.dladeji.earthquake.dtos;

import lombok.Data;

@Data
public class Feature{
    private String type;
    private Property properties;
    private Object geometry;
    private String id;
}