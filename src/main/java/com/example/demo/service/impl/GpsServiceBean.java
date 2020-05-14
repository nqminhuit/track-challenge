package com.example.demo.service.impl;

import java.util.Collections;
import java.util.List;
import com.example.demo.domain.GPS;
import com.example.demo.service.GpsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GpsServiceBean implements GpsService {

    private final Logger LOG = LoggerFactory.getLogger(GpsServiceBean.class);

    @Override
    public void addGps(GPS gps) {
        LOG.info("add GPS");
    }

    @Override
    public List<GPS> getLatestGpses() {
        LOG.info("get latest GPS");
        return Collections.emptyList();
    }

    @Override
    public GPS getGpsById(Long id) {
        LOG.info("get GPS by ID");
        return null;
    }

}
