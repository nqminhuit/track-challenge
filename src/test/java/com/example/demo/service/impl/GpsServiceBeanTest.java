package com.example.demo.service.impl;

import static org.junit.Assert.assertEquals;
import java.util.List;
import com.example.demo.domain.GPS;
import org.junit.Before;
import org.junit.Test;

public class GpsServiceBeanTest {

    private GpsServiceBean gpsServiceBean;

    @Before
    public void setup() {
        gpsServiceBean = new GpsServiceBean();
    }

    @Test
    public void addGps() {
        // given:
        GPS gps = new GPS();

        // when:
        gpsServiceBean.addGps(gps);

        // then:
    }

    @Test
    public void getLatestGpses() {
        // given:

        // when:
        List<GPS> gpsess = gpsServiceBean.getLatestGpses();

        // then:
    }

    @Test
    public void getGpsById() {
        // given:
        Long id = -1L;

        // when:
        GPS gps = gpsServiceBean.getGpsById(id);

        // then:
    }
}
