package com.dladeji.earthquake;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApiResponse {
    private String type;
    private Object metadata;
    private Object[] features;
    private int[] bbox;
    /*
     * "metadata": {
"generated": 1750882335000,
"url": "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2024-01-01&endtime=2024-01-02&alertlevel=green",
"title": "USGS Earthquakes",
"status": 200,
"api": "1.14.1",
"count": 7
},
     */
}
