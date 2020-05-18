package com.example.demo.helper;

import static com.example.demo.helper.Constants.BY_WAYPOINT_NAME;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.example.demo.domain.Metadata;
import com.example.demo.domain.Track;
import com.example.demo.domain.TrackPoint;
import com.example.demo.domain.TrackSegment;
import com.example.demo.domain.Waypoint;
import com.example.demo.dto.MetadataDto;
import com.example.demo.dto.TrackDto;
import com.example.demo.dto.TrackPointDto;
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
        assertThat(entity.getTime()).isEqualToIgnoringSeconds(dto.getTime().toGregorianCalendar().getTime());
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

    private static void assertTrackPoints(Set<TrackPoint> trackPoints, List<TrackPointDto> dtos) {
        int entitySize = trackPoints.size();
        assertThat(entitySize).isEqualTo(dtos.size());
        List<TrackPoint> entities = new ArrayList<>();
        entities.addAll(trackPoints);

        List<Double> eleDtos = dtos.stream().map(dto -> dto.getEle().doubleValue()).collect(toList());
        List<Double> latDtos = dtos.stream().map(dto -> dto.getLat().doubleValue()).collect(toList());
        List<Double> lonDtos = dtos.stream().map(dto -> dto.getLon().doubleValue()).collect(toList());

        assertThat(entities).extracting("ele").containsExactlyInAnyOrder(eleDtos.toArray());
        assertThat(entities).extracting("lat").containsExactlyInAnyOrder(latDtos.toArray());
        assertThat(entities).extracting("lon").containsExactlyInAnyOrder(lonDtos.toArray());

        entities.sort(Constants.BY_TRACKPOINT_TIME);
        Iterator<TrackPoint> entityIterator = entities.iterator();
        Iterator<TrackPointDto> dtoIterator = dtos.iterator();

        while (entityIterator.hasNext()) {
            Date entityTime = entityIterator.next().getTime();
            Date dtoTime = dtoIterator.next().getTime().toGregorianCalendar().getTime();
            assertThat(entityTime).isEqualToIgnoringMillis(dtoTime);
        }
    }
}
