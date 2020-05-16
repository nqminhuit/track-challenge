package com.example.demo.converter;

import static com.example.demo.helper.AssertionHelper.assertMetadata;
import static com.example.demo.helper.AssertionHelper.assertWaypoints;
import static com.example.demo.helper.AssertionHelper.assertTracks;
import static com.example.demo.helper.CreateDataHelper.createMetadataDto;
import static com.example.demo.helper.CreateDataHelper.createWaypointDto;
import static com.example.demo.helper.CreateDataHelper.createTrackDto;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import javax.xml.datatype.DatatypeConfigurationException;
import com.example.demo.domain.GPS;
import com.example.demo.domain.Metadata;
import com.example.demo.domain.Waypoint;
import com.example.demo.dto.Gpx;
import com.example.demo.dto.MetadataDto;
import com.example.demo.dto.WaypointDto;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

public class EntityConverterTest {

    private EntityConverter converter;

    @Before
    public void setup() {
        converter = new EntityConverter();
        converter.setModelMapper(new ModelMapper());
    }

    @Test
    public void waypointDtoToEntity() {
        // given:
        WaypointDto dto = createWaypointDto(
            new BigDecimal(100), new BigDecimal(-100), "waypoint 1", "/static/wpt/Waypoint");

        // when:
        Waypoint entity = converter.toEntity(dto);

        // then:
        assertWaypoints(Collections.singletonList(entity), Collections.singletonList(dto));
    }

    @Test
    public void metadataDtoToEntity() throws DatatypeConfigurationException {
        // given:
        Date currentTimestamp = new Date();
        MetadataDto dto = createMetadataDto("John", "this is John's GPX", "John's GPX",
            "john-gpx", "https://www.johngpx.com", currentTimestamp);

        // when:
        Metadata entity = converter.toEntity(dto);

        // then:
        assertMetadata(entity, dto);
    }

    @Test
    public void gpsDtoToEntity() throws DatatypeConfigurationException {
        // given:
        Date currentTimestamp = new Date();
        Gpx dto = new Gpx();

        dto.setMetadata(createMetadataDto("John", "this is John's GPX",
            "John's GPX",
            "john-gpx", "https://www.johngpx.com", currentTimestamp));

        dto.getWpt().add(createWaypointDto(new BigDecimal(100), new BigDecimal(-100),
            "waypoint 1", "/static/wpt/Waypoint1"));
        dto.getWpt().add(createWaypointDto(new BigDecimal(200), new BigDecimal(-200),
            "waypoint 2", "/static/wpt/Waypoint2"));
        dto.getWpt().add(createWaypointDto(new BigDecimal(300), new BigDecimal(-300),
            "waypoint 3", "/static/wpt/Waypoint3"));

        dto.getTrk().add(createTrackDto());

        // when:
        GPS entity = converter.toEntity(dto);

        // then:
        assertMetadata(entity.getMetadata(), dto.getMetadata());
        assertWaypoints(entity.getWaypoints(), dto.getWpt());
        assertTracks(entity.getTracks(), dto.getTrk());
    }

}
