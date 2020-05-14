package com.example.demo.controller;

import java.util.List;
import com.example.demo.domain.GPS;
import com.example.demo.service.GpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gps")
public class GpsController {

    @Autowired
    private GpsService gpsService;

    @GetMapping("/latest")
    public List<GPS> getLatestGpses() {
        return gpsService.getLatestGpses();
    }

    @GetMapping("/{id}")
    public GPS getGpsById(@PathVariable("id") Long id) {
        return gpsService.getGpsById(id);
    }

    @PostMapping("/upload")
    public void uploadGps() {
        gpsService.addGps(null);
    }

}
