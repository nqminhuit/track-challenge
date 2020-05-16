package com.example.demo.helper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import java.util.List;
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

    public static void assertWaypoints(List<Waypoint> entities, List<WaypointDto> dtos) {
        int entitySize = entities.size();
        assertThat(entitySize).isEqualTo(dtos.size());
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

    public static void assertTracks(List<Track> entities, List<TrackDto> dtos) {
        int entitySize = entities.size();
        assertThat(entitySize).isEqualTo(dtos.size());
        for (int i = 0; i < entitySize; ++i) {
            assertTrackSegments(entities.get(i).getTrackSegments(), dtos.get(i).getTrkseg());
        }
    }

    private static void assertTrackSegments(List<TrackSegment> entities, List<TrackSegmentDto> dtos) {
        int entitySize = entities.size();
        assertThat(entitySize).isEqualTo(dtos.size());
        for (int i = 0; i < entitySize; ++i) {
            assertTrackPoints(entities.get(i).getTrackPoints(), dtos.get(i).getTrkpt());
        }
    }

    private static void assertTrackPoints(List<TrackPoint> entities, List<TrackPointDto> dtos) {
        int entitySize = entities.size();
        assertThat(entitySize).isEqualTo(dtos.size());
        for (int i = 0; i < entitySize; ++i) {
            assertThat(entities.get(i).getEle()).isEqualTo(dtos.get(i).getEle().doubleValue());
            assertThat(entities.get(i).getLat()).isEqualTo(dtos.get(i).getLat().doubleValue());
            assertThat(entities.get(i).getLon()).isEqualTo(dtos.get(i).getLon().doubleValue());
            assertThat(entities.get(i).getTime())
                .isEqualToIgnoringSeconds(dtos.get(i).getTime().toGregorianCalendar().getTime());
        }
    }
}
