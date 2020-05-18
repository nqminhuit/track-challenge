package com.example.demo;

import static com.example.demo.helper.Constants.BY_TRACKPOINT_ID;
import static com.example.demo.helper.Constants.BY_WAYPOINTDTO_LAT;
import static com.example.demo.helper.Constants.SDT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import com.example.demo.controller.GpsController;
import com.example.demo.domain.GPS;
import com.example.demo.domain.Metadata;
import com.example.demo.domain.Track;
import com.example.demo.domain.TrackPoint;
import com.example.demo.domain.TrackSegment;
import com.example.demo.domain.Waypoint;
import com.example.demo.dto.Gpx;
import com.example.demo.dto.TrackDto;
import com.example.demo.dto.TrackPointDto;
import com.example.demo.dto.TrackSegmentDto;
import com.example.demo.dto.WaypointDto;
import com.example.demo.helper.Constants;
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

        Metadata metadata = gps.getMetadata();
        assertThat(metadata.getId()).isEqualTo(1L);
        assertThat(metadata.getName()).isEqualTo("Washington DC");
        assertThat(metadata.getDescription()).isEqualTo("GPS of Washington DC, this is a test GPS!");
        assertThat(metadata.getAuthor()).isEqualTo("Bruce Wayne");
        assertThat(metadata.getLinkHref()).isEqualTo("https://www.google.com");
        assertThat(metadata.getLinkText()).isEqualTo("WashingtonMap");
        assertThat(metadata.getTime()).isEqualToIgnoringMillis(parseTime("2020-05-16 19:00:52.00"));

        Set<Track> tracks = gps.getTracks();
        assertThat(tracks).hasSize(1);
        Track track = tracks.iterator().next();
        assertThat(track.getId()).isEqualTo(1);

        Set<TrackSegment> trackSegments = track.getTrackSegments();
        assertThat(trackSegments).hasSize(1);
        TrackSegment trackSegment = trackSegments.iterator().next();
        assertThat(trackSegment.getId()).isEqualTo(1);

        Set<TrackPoint> trackSegmentPoints = trackSegment.getTrackPoints();
        assertThat(trackSegmentPoints).hasSize(6);
        List<TrackPoint> trackPoints = new ArrayList<>();
        trackPoints.addAll(trackSegmentPoints);
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
        assertThat(waypoints).hasSize(4);
        assertThat(waypoints)
            .extracting("id")
            .containsExactlyInAnyOrder(1L, 2L, 3L, 4L);
        assertThat(waypoints)
            .extracting("lat")
            .containsExactlyInAnyOrder(55D, 65D, 75D, 85D);
        assertThat(waypoints)
            .extracting("lon")
            .containsExactly(-55D, -55D, -55D, -55D);
        assertThat(waypoints)
            .extracting("name")
            .containsExactlyInAnyOrder("waypoint 1", "waypoint 2", "waypoint 3", "waypoint 4");
        assertThat(waypoints)
            .extracting("sym")
            .containsOnly(
                "/static/wpt/Waypoint",
                "/static/wpt/Waypoint",
                "/static/wpt/Waypoint",
                "/static/wpt/Waypoint");
    }

    @Test
    public void getGpxById() {
        // given:
        Long id = 1L;

        // when:
        Gpx gpx = gpsController.getGpxById(id);

        // then:
        assertThat(gpx.getMetadata().getName()).isEqualTo("Washington DC");
        assertThat(gpx.getMetadata().getDesc()).isEqualTo("GPS of Washington DC, this is a test GPS!");
        assertThat(gpx.getMetadata().getAuthor()).isEqualTo("Bruce Wayne");
        assertThat(gpx.getMetadata().getLink().getHref()).isEqualTo("https://www.google.com");
        assertThat(gpx.getMetadata().getLink().getText()).isEqualTo("WashingtonMap");
        assertThat(gpx.getMetadata().getTime().toGregorianCalendar().getTime())
            .isEqualToIgnoringMillis(parseTime("2020-05-16 19:00:52.00"));

        List<WaypointDto> waypoints = new ArrayList<>();
        waypoints.addAll(gpx.getWpt());
        assertThat(waypoints).hasSize(4);

        assertThat(waypoints)
            .extracting("sym")
            .containsOnly(
                "/static/wpt/Waypoint",
                "/static/wpt/Waypoint",
                "/static/wpt/Waypoint",
                "/static/wpt/Waypoint");

        waypoints.sort(BY_WAYPOINTDTO_LAT);
        assertThat(waypoints.get(0).getLat().intValue()).isEqualTo(55);
        assertThat(waypoints.get(1).getLat().intValue()).isEqualTo(65);
        assertThat(waypoints.get(2).getLat().intValue()).isEqualTo(75);
        assertThat(waypoints.get(3).getLat().intValue()).isEqualTo(85);

        assertThat(waypoints.get(0).getLon().intValue()).isEqualTo(-55);
        assertThat(waypoints.get(1).getLon().intValue()).isEqualTo(-55);
        assertThat(waypoints.get(2).getLon().intValue()).isEqualTo(-55);
        assertThat(waypoints.get(3).getLon().intValue()).isEqualTo(-55);

        assertThat(waypoints.get(0).getName()).isEqualTo("waypoint 1");
        assertThat(waypoints.get(1).getName()).isEqualTo("waypoint 2");
        assertThat(waypoints.get(2).getName()).isEqualTo("waypoint 3");
        assertThat(waypoints.get(3).getName()).isEqualTo("waypoint 4");

        assertThat(waypoints.get(0).getSym()).isEqualTo("/static/wpt/Waypoint");
        assertThat(waypoints.get(1).getSym()).isEqualTo("/static/wpt/Waypoint");
        assertThat(waypoints.get(2).getSym()).isEqualTo("/static/wpt/Waypoint");
        assertThat(waypoints.get(3).getSym()).isEqualTo("/static/wpt/Waypoint");


        List<TrackDto> tracks = gpx.getTrk();
        assertThat(tracks).hasSize(1);
        List<TrackSegmentDto> trackSegments = tracks.iterator().next().getTrkseg();
        assertThat(trackSegments).hasSize(1);
        List<TrackPointDto> trackPoints = trackSegments.iterator().next().getTrkpt();

        trackPoints.sort(Constants.BY_TRACKPOINTDTO_LAT);
        assertThat(trackPoints.get(0).getLat().doubleValue()).isEqualTo(10.0D);
        assertThat(trackPoints.get(1).getLat().doubleValue()).isEqualTo(10.1D);
        assertThat(trackPoints.get(2).getLat().doubleValue()).isEqualTo(10.2D);
        assertThat(trackPoints.get(3).getLat().doubleValue()).isEqualTo(10.3D);
        assertThat(trackPoints.get(4).getLat().doubleValue()).isEqualTo(10.4D);
        assertThat(trackPoints.get(5).getLat().doubleValue()).isEqualTo(10.5D);

        assertThat(trackPoints.get(0).getLon().doubleValue()).isEqualTo(-10.0D);
        assertThat(trackPoints.get(1).getLon().doubleValue()).isEqualTo(-10.1D);
        assertThat(trackPoints.get(2).getLon().doubleValue()).isEqualTo(-10.2D);
        assertThat(trackPoints.get(3).getLon().doubleValue()).isEqualTo(-10.3D);
        assertThat(trackPoints.get(4).getLon().doubleValue()).isEqualTo(-10.4D);
        assertThat(trackPoints.get(5).getLon().doubleValue()).isEqualTo(-10.5D);

        assertThat(trackPoints.get(0).getEle().doubleValue()).isEqualTo(11.11D);
        assertThat(trackPoints.get(1).getEle().doubleValue()).isEqualTo(11.12D);
        assertThat(trackPoints.get(2).getEle().doubleValue()).isEqualTo(11.13D);
        assertThat(trackPoints.get(3).getEle().doubleValue()).isEqualTo(11.12D);
        assertThat(trackPoints.get(4).getEle().doubleValue()).isEqualTo(11.13D);
        assertThat(trackPoints.get(5).getEle().doubleValue()).isEqualTo(11.11D);

        assertThat(trackPoints.get(0).getTime().toGregorianCalendar().getTime()).isEqualToIgnoringMillis("2020-05-16 18:47:52.00");
        assertThat(trackPoints.get(1).getTime().toGregorianCalendar().getTime()).isEqualToIgnoringMillis("2020-05-16 18:48:52.00");
        assertThat(trackPoints.get(2).getTime().toGregorianCalendar().getTime()).isEqualToIgnoringMillis("2020-05-16 18:49:52.00");
        assertThat(trackPoints.get(3).getTime().toGregorianCalendar().getTime()).isEqualToIgnoringMillis("2020-05-16 18:50:52.00");
        assertThat(trackPoints.get(4).getTime().toGregorianCalendar().getTime()).isEqualToIgnoringMillis("2020-05-16 18:51:52.00");
        assertThat(trackPoints.get(5).getTime().toGregorianCalendar().getTime()).isEqualToIgnoringMillis("2020-05-16 18:52:52.00");
    }
}
