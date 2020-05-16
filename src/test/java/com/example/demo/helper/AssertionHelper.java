package com.example.demo.helper;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import com.example.demo.domain.Metadata;
import com.example.demo.domain.Waypoint;
import com.example.demo.dto.MetadataDto;
import com.example.demo.dto.WaypointDto;

public class AssertionHelper {

    public static void assertWaypoint(List<Waypoint> entities, List<WaypointDto> dtos) {
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
}
