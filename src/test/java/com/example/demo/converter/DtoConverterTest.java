package com.example.demo.converter;

import static com.example.demo.helper.AssertionHelper.assertMetadata;
import static com.example.demo.helper.AssertionHelper.assertWaypoints;
import static com.example.demo.helper.CreateDataHelper.createMetadataEntity;
import static com.example.demo.helper.CreateDataHelper.createWaypointEntity;
import java.util.Collections;
import java.util.Date;
import com.example.demo.domain.Metadata;
import com.example.demo.domain.Waypoint;
import com.example.demo.dto.MetadataDto;
import com.example.demo.dto.WaypointDto;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

public class DtoConverterTest {

    private DtoConverter converter;

    @Before
    public void setup() {
        converter = new DtoConverter();
        converter.setModelMapper(new ModelMapper());
    }

    @Test
    public void waypointEntityToDto() {
        // given:
        Waypoint entity = createWaypointEntity(-1L, 100.1D, -12.3D,
            "waypoint entity 1",
            "/waypoint/entity/1");

        // when:
        WaypointDto dto = converter.toDto(entity);

        // then:
        assertWaypoints(Collections.singletonList(entity), Collections.singletonList(dto));
    }

    @Test
    public void metadataEntityToDto() {
        // given:
        Metadata entity = createMetadataEntity("Batman", "This is Batman's metadata", -1L,
            "https://www.google.com", "BatmanMetadata", "Batman's", new Date());

        // when:
        MetadataDto dto = converter.toDto(entity);

        // then:
        assertMetadata(entity, dto);
    }
}
