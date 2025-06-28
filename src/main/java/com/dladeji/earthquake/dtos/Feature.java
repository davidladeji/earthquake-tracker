package com.dladeji.earthquake.dtos;

import java.util.Map;

import lombok.Data;

@Data
public class Feature{
    private String id;
    private String type;
    private Map<String, Object> properties;
    private Map<String, Object> geometry;
}