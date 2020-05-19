package com.example.demo.converter;

import static com.example.demo.helper.AssertionHelper.assertMetadata;
import static com.example.demo.helper.AssertionHelper.assertWaypoints;
import static com.example.demo.helper.CreateDataHelper.createGps;
import static com.example.demo.helper.CreateDataHelper.createMetadataEntity;
import static com.example.demo.helper.CreateDataHelper.createWaypointEntity;
import static org.assertj.core.api.Assertions.assertThat;
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
        assertWaypoints(Collections.singleton(entity), Collections.singletonList(dto));
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

    @Test
    public void gpsEntityToDtoFetchOnlyMetadata() throws DatatypeConfigurationException {
        // given:
        GPS entity = createGps();

        // when:
        Gpx dto = converter.toDtoOnlyMetadata(entity);

        // then:
        assertThat(dto.getTrk()).isEmpty();
        assertThat(dto.getWpt()).isEmpty();
        assertThat(dto.getMetadata().getAuthor()).isEqualTo("Jack Sparrow");
        assertThat(dto.getMetadata().getDesc()).isEqualTo("This is the GPS of the Black Pearl!");
        assertThat(dto.getMetadata().getLink().getHref()).isEqualTo("https://www.blackpearl.com");
        assertThat(dto.getMetadata().getLink().getText()).isEqualTo("BlackPearl");
        assertThat(dto.getMetadata().getName()).isEqualTo("Black Pearl Travel");
        assertThat(dto.getMetadata().getTime().toGregorianCalendar().getTime())
            .isEqualToIgnoringMillis("2020-01-01 12:34:56.78");
    }
}
