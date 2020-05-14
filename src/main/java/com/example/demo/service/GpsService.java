package com.example.demo.service;

import java.util.List;
import com.example.demo.domain.GPS;

public interface GpsService {

    void addGps(GPS gps);

    List<GPS> getLatestGpses();

    GPS getGpsById(Long id);
}
