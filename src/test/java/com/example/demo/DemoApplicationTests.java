package com.example.demo;

import static com.example.demo.helper.Constants.BY_TRACKPOINT_ID;
import static org.assertj.core.api.Assertions.assertThat;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import com.example.demo.controller.GpsController;
import com.example.demo.domain.GPS;
import com.example.demo.domain.Track;
import com.example.demo.domain.TrackPoint;
import com.example.demo.domain.TrackSegment;
import com.example.demo.domain.Waypoint;
import com.example.demo.repository.GpsRepository;
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

    private static final SimpleDateFormat SDT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");

    @Autowired
    private GpsController gpsController;

    @Autowired
    private GpsRepository gpsRepository;

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

    public void setup() throws FileNotFoundException {
        loadResources();
    }

    private Date parseTime(String time) {
        try {
            return SDT.parse(time);
        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Test
    public void loadGpsEntity_ShouldFetchAllListObjects() {
        // given:
        Long id = 1L;

        // when:
        GPS gps = gpsRepository.getFetchedGpsById(id);

        // then:
        assertThat(gps.getId()).isEqualTo(1L);

        assertThat(gps.getMetadata().getId()).isEqualTo(1L);
        assertThat(gps.getMetadata().getName()).isEqualTo("Washington DC");
        assertThat(gps.getMetadata().getDescription()).isEqualTo("GPS of Washington DC, this is a test GPS!");
        assertThat(gps.getMetadata().getAuthor()).isEqualTo("Bruce Wayne");
        assertThat(gps.getMetadata().getLinkHref()).isEqualTo("https://www.google.com");
        assertThat(gps.getMetadata().getLinkText()).isEqualTo("WashingtonMap");
        assertThat(gps.getMetadata().getTime()).isEqualToIgnoringMillis(parseTime("2020-05-16 19:00:52.00"));

        assertThat(gps.getTracks().size()).isEqualTo(1);
        Track track = gps.getTracks().iterator().next();
        assertThat(track.getId()).isEqualTo(1);

        assertThat(track.getTrackSegments().size()).isEqualTo(1);
        TrackSegment trackSegment = track.getTrackSegments().iterator().next();
        assertThat(trackSegment.getId()).isEqualTo(1);

        assertThat(trackSegment.getTrackPoints().size()).isEqualTo(6);
        List<TrackPoint> trackPoints = new ArrayList<>();
        trackPoints.addAll(trackSegment.getTrackPoints());
        trackPoints.sort(BY_TRACKPOINT_ID);
        assertThat(trackPoints)
            .extracting("id").containsExactly(1L, 2L, 3L, 4L, 5L, 6L);
        assertThat(trackPoints)
            .extracting("ele").containsExactly(11.11D, 11.12D, 11.13D, 11.12D, 11.13D, 11.11D);
        assertThat(trackPoints)
            .extracting("lat").containsExactly(10.0D, 10.1D, 10.2D, 10.3D, 10.4D, 10.5D);
        assertThat(trackPoints)
            .extracting("lon").containsExactly(-10.0D, -10.1D, -10.2D, -10.3D, -10.4D, -10.5D);

        assertThat(trackPoints.get(0).getTime()).isEqualToIgnoringMillis(parseTime("2020-05-16 18:47:52.00"));
        assertThat(trackPoints.get(1).getTime()).isEqualToIgnoringMillis(parseTime("2020-05-16 18:48:52.00"));
        assertThat(trackPoints.get(2).getTime()).isEqualToIgnoringMillis(parseTime("2020-05-16 18:49:52.00"));
        assertThat(trackPoints.get(3).getTime()).isEqualToIgnoringMillis(parseTime("2020-05-16 18:50:52.00"));
        assertThat(trackPoints.get(4).getTime()).isEqualToIgnoringMillis(parseTime("2020-05-16 18:51:52.00"));
        assertThat(trackPoints.get(5).getTime()).isEqualToIgnoringMillis(parseTime("2020-05-16 18:52:52.00"));

        List<Waypoint> waypoints = new ArrayList<>();
        waypoints.addAll(gps.getWaypoints());
        assertThat(waypoints.size()).isEqualTo(4);
        assertThat(gps.getWaypoints())
            .extracting("id")
            .containsExactlyInAnyOrder(1L, 2L, 3L, 4L);
        assertThat(gps.getWaypoints())
            .extracting("lat")
            .containsExactlyInAnyOrder(55D, 65D, 75D, 85D);
        assertThat(gps.getWaypoints())
            .extracting("lon")
            .containsExactly(-55D, -55D, -55D, -55D);
        assertThat(gps.getWaypoints())
            .extracting("name")
            .containsExactlyInAnyOrder("waypoint 1", "waypoint 2", "waypoint 3", "waypoint 4");
    }
}
