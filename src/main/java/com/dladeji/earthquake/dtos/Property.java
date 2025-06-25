package com.dladeji.earthquake.dtos;

import lombok.Data;

@Data
public class Property {
    private double mag;
        private String place;
        private long time;
        private long updated;
        private Integer tz;
        private String url;
        private String detail;
        private Integer felt;
        private Double cdi;
        private double mmi;
        private String alert;
        private String status;
        private int tsunami;
        private int sig;
        private String net;
        private String code;
        private String ids;
        private String sources;
        private String types;
        private int nst;
        private double dmin;
        private double rms;
        private int gap;
        private String magType;
        private String type;
        private String title;
}
