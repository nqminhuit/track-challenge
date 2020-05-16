package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import com.example.demo.controller.GpsController;
import com.example.demo.domain.GPS;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    private static final Logger LOG = LoggerFactory.getLogger(DemoApplicationTests.class);

    @Autowired
    private GpsController gpsController;

    private void loadResources() throws FileNotFoundException {
        String resourceName = "sample-test-small.gpx";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            LOG.info(scanner.nextLine());
        }
        scanner.close();
    }

    @Before
    public void setup() throws FileNotFoundException {
        loadResources();
    }

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

    @Test
    public void getGpsById() {
        // given:
        Long id = -1L;

        // when:
        GPS gps = gpsController.getGpsById(id);

        // then:
        // assertThat(gps).isNotNull();
    }

}
