package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import com.example.demo.controller.GpsController;
import com.example.demo.domain.GPS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private GpsController gpsController;

    @Test
    public void contextLoads() {
        assertThat(gpsController).isNotNull();
    }

    @Test
    public void uploadGps() {
        // given:
        GPS gps = new GPS();

        // when:
        gpsController.uploadGps();

        // then:
    }

    @Test
    public void getLatestGpses() {
        // when:
        List<GPS> latestGpses = gpsController.getLatestGpses();

        // then:
        // assertThat(latestGpses).isNotEmpty();

    }

}
