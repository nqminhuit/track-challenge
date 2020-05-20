package com.example.demo.helper;

import static com.example.demo.helper.Constants.BY_WAYPOINT_NAME;
import static com.example.demo.helper.CreateDataHelper.createTime;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.example.demo.domain.GPS;
import com.example.demo.domain.Metadata;
import com.example.demo.domain.Track;
import com.example.demo.domain.TrackSegment;
import com.example.demo.domain.Waypoint;
import com.example.demo.dto.Gpx;
import com.example.demo.dto.MetadataDto;
import com.example.demo.dto.TrackDto;
import com.example.demo.dto.TrackSegmentDto;
import com.example.demo.dto.WaypointDto;

public class AssertionHelper {

    public static void assertWaypoints(Set<Waypoint> waypoints, List<WaypointDto> dtos) {
        int entitySize = waypoints.size();
        assertThat(entitySize).isEqualTo(dtos.size());
        List<Waypoint> entities = new ArrayList<>();
        entities.addAll(waypoints);
        entities.sort(BY_WAYPOINT_NAME);
        for (int i = 0; i < entitySize; ++i) {
            assertThat(entities.get(i).getName()).isEqualTo(dtos.get(i).getName());
            assertThat(entities.get(i).getSym()).isEqualTo(dtos.get(i).getSym());
            assertThat(entities.get(i).getLat()).isEqualTo(dtos.get(i).getLat().doubleValue());
            assertThat(entities.get(i).getLon()).isEqualTo(dtos.get(i).getLon().doubleValue());
        }
    }

    public static void assertMetadata(Metadata entity, MetadataDto dto) {
        assertThat(entity.getName()).isEqualTo(dto.getName());
        assertThat(entity.getAuthor()).isEqualTo(dto.getAuthor());
        assertThat(entity.getDescription()).isEqualTo(dto.getDesc());
        assertThat(entity.getLinkText()).isEqualTo(dto.getLink().getText());
        assertThat(entity.getLinkHref()).isEqualTo(dto.getLink().getHref());
        assertThat(entity.getTime()).isEqualToIgnoringMillis(dto.getTime().toGregorianCalendar().getTime());
    }

    public static void assertTracks(Set<Track> tracks, List<TrackDto> dtos) {
        int entitySize = tracks.size();
        assertThat(entitySize).isEqualTo(dtos.size());
        List<Track> entities = new ArrayList<>();
        entities.addAll(tracks);
        for (int i = 0; i < entitySize; ++i) {
            assertTrackSegments(entities.get(i).getTrackSegments(), dtos.get(i).getTrkseg());
        }
    }

    private static void assertTrackSegments(Set<TrackSegment> trackSegments, List<TrackSegmentDto> dtos) {
        int entitySize = trackSegments.size();
        assertThat(entitySize).isEqualTo(dtos.size());
        List<TrackSegment> entities = new ArrayList<>();
        entities.addAll(trackSegments);
        for (int i = 0; i < entitySize; ++i) {
            assertTrackPoints(entities.get(i).getTrackPoints(), dtos.get(i).getTrkpt());
        }
    }

    private static void assertTrackPoints(Set<Waypoint> trackPoints, List<WaypointDto> dtos) {
        int entitySize = trackPoints.size();
        assertThat(entitySize).isEqualTo(dtos.size());
        List<Waypoint> entities = new ArrayList<>();
        entities.addAll(trackPoints);

        List<Double> eleDtos = dtos.stream().map(dto -> dto.getEle().doubleValue()).collect(toList());
        List<Double> latDtos = dtos.stream().map(dto -> dto.getLat().doubleValue()).collect(toList());
        List<Double> lonDtos = dtos.stream().map(dto -> dto.getLon().doubleValue()).collect(toList());

        assertThat(entities).extracting("ele").containsExactlyInAnyOrder(eleDtos.toArray());
        assertThat(entities).extracting("lat").containsExactlyInAnyOrder(latDtos.toArray());
        assertThat(entities).extracting("lon").containsExactlyInAnyOrder(lonDtos.toArray());

        entities.sort(Constants.BY_WAYPOINT_TIME);
        Iterator<Waypoint> entityIterator = entities.iterator();
        Iterator<WaypointDto> dtoIterator = dtos.iterator();

        while (entityIterator.hasNext()) {
            Date entityTime = entityIterator.next().getTime();
            Date dtoTime = dtoIterator.next().getTime().toGregorianCalendar().getTime();
            assertThat(entityTime).isEqualToIgnoringMillis(dtoTime);
        }
    }

    /**
     * data defined at: src/main/resources/data.sql
     */
    public static void assertThatGpxRetrievedAndConvertedCorrectly(Gpx gpx) {
        assertThat(gpx.getMetadata().getName()).isEqualTo("Washington DC");
        assertThat(gpx.getMetadata().getDesc()).isEqualTo("GPS of Washington DC, this is a test GPS!");
        assertThat(gpx.getMetadata().getAuthor()).isEqualTo("Bruce Wayne");
        assertThat(gpx.getMetadata().getLink().getHref()).isEqualTo("https://www.google.com");
        assertThat(gpx.getMetadata().getLink().getText()).isEqualTo("WashingtonMap");
        assertThat(gpx.getMetadata().getTime().toGregorianCalendar().getTime())
            .isEqualToIgnoringMillis(createTime("2020-05-16 19:00:52.00"));

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

        waypoints.sort(Constants.BY_WAYPOINTDTO_LAT);
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

        List<TrackDto> tracks = gpx.getTrk();
        assertThat(tracks).hasSize(1);
        List<TrackSegmentDto> trackSegments = tracks.iterator().next().getTrkseg();
        assertThat(trackSegments).hasSize(1);
        List<WaypointDto> trackPoints = trackSegments.iterator().next().getTrkpt();

        trackPoints.sort(Constants.BY_WAYPOINTDTO_LAT);
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

        assertThat(trackPoints.get(0).getTime().toGregorianCalendar().getTime())
            .isEqualToIgnoringMillis("2020-05-16 18:47:52.00");
        assertThat(trackPoints.get(1).getTime().toGregorianCalendar().getTime())
            .isEqualToIgnoringMillis("2020-05-16 18:48:52.00");
        assertThat(trackPoints.get(2).getTime().toGregorianCalendar().getTime())
            .isEqualToIgnoringMillis("2020-05-16 18:49:52.00");
        assertThat(trackPoints.get(3).getTime().toGregorianCalendar().getTime())
            .isEqualToIgnoringMillis("2020-05-16 18:50:52.00");
        assertThat(trackPoints.get(4).getTime().toGregorianCalendar().getTime())
            .isEqualToIgnoringMillis("2020-05-16 18:51:52.00");
        assertThat(trackPoints.get(5).getTime().toGregorianCalendar().getTime())
            .isEqualToIgnoringMillis("2020-05-16 18:52:52.00");
    }

    /**
     * data defined at: src/main/resources/data.sql
     */
    public static void assertThatGpsRetrieveCorrectlyFromDB(GPS gps) {
        Metadata metadata = gps.getMetadata();
        assertThat(metadata.getId()).isEqualTo(-1L);
        assertThat(metadata.getName()).isEqualTo("Washington DC");
        assertThat(metadata.getDescription()).isEqualTo("GPS of Washington DC, this is a test GPS!");
        assertThat(metadata.getAuthor()).isEqualTo("Bruce Wayne");
        assertThat(metadata.getLinkHref()).isEqualTo("https://www.google.com");
        assertThat(metadata.getLinkText()).isEqualTo("WashingtonMap");
        assertThat(metadata.getTime()).isEqualToIgnoringMillis(createTime("2020-05-16 19:00:52.00"));

        Set<Track> tracks = gps.getTracks();
        assertThat(tracks).hasSize(1);
        Track track = tracks.iterator().next();
        assertThat(track.getId()).isEqualTo(-1L);

        Set<TrackSegment> trackSegments = track.getTrackSegments();
        assertThat(trackSegments).hasSize(1);
        TrackSegment trackSegment = trackSegments.iterator().next();
        assertThat(trackSegment.getId()).isEqualTo(-1L);

        Set<Waypoint> trackSegmentPoints = trackSegment.getTrackPoints();
        assertThat(trackSegmentPoints).hasSize(6);
        List<Waypoint> trackPoints = new ArrayList<>();
        trackPoints.addAll(trackSegmentPoints);
        trackPoints.sort(Constants.BY_WAYPOINT_ID);
        assertThat(trackPoints)
            .extracting("id").containsExactly(-10L, -9L, -8L, -7L, -6L, -5L);
        assertThat(trackPoints)
            .extracting("ele").containsExactly(11.11D, 11.13D, 11.12D, 11.13D, 11.12D, 11.11D);
        assertThat(trackPoints)
            .extracting("lat").containsExactly(10.5D, 10.4D, 10.3D, 10.2D, 10.1D, 10.0D);
        assertThat(trackPoints)
            .extracting("lon").containsExactly(-10.5D, -10.4D, -10.3D, -10.2D, -10.1D, -10.0D);

        assertThat(trackPoints.get(0).getTime())
            .isEqualToIgnoringMillis(createTime("2020-05-16 18:52:52.00"));
        assertThat(trackPoints.get(1).getTime())
            .isEqualToIgnoringMillis(createTime("2020-05-16 18:51:52.00"));
        assertThat(trackPoints.get(2).getTime())
            .isEqualToIgnoringMillis(createTime("2020-05-16 18:50:52.00"));
        assertThat(trackPoints.get(3).getTime())
            .isEqualToIgnoringMillis(createTime("2020-05-16 18:49:52.00"));
        assertThat(trackPoints.get(4).getTime())
            .isEqualToIgnoringMillis(createTime("2020-05-16 18:48:52.00"));
        assertThat(trackPoints.get(5).getTime())
            .isEqualToIgnoringMillis(createTime("2020-05-16 18:47:52.00"));

        List<Waypoint> waypoints = new ArrayList<>();
        waypoints.addAll(gps.getWaypoints());
        assertThat(waypoints).hasSize(4);
        assertThat(waypoints)
            .extracting("id")
            .containsExactlyInAnyOrder(-1L, -2L, -3L, -4L);
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

}
