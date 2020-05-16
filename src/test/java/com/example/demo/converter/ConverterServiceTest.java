package com.example.demo.converter;

import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import com.example.demo.domain.GPS;
import com.example.demo.domain.Metadata;
import com.example.demo.domain.Waypoint;
import com.example.demo.dto.Gpx;
import com.example.demo.dto.LinkDto;
import com.example.demo.dto.MetadataDto;
import com.example.demo.dto.WaypointDto;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

public class ConverterServiceTest {

    private ConverterService converter;

    @Before
    public void setup() {
        converter = new ConverterService();
        converter.setModelMapper(new ModelMapper());
    }

    private WaypointDto createWaypointDto(BigDecimal lat, BigDecimal lon, String name, String sym) {
        WaypointDto dto = new WaypointDto();
        dto.setLat(lat);
        dto.setLon(lon);
        dto.setName(name);
        dto.setSym(sym);
        return dto;
    }

    private void assertWaypoint(List<Waypoint> entities, List<WaypointDto> dtos) {
        int entitySize = entities.size();
        assertThat(entitySize).isEqualTo(dtos.size());
        for (int i = 0; i < entitySize; ++i) {
            assertThat(entities.get(i).getName()).isEqualTo(dtos.get(i).getName());
            assertThat(entities.get(i).getSym()).isEqualTo(dtos.get(i).getSym());
            assertThat(entities.get(i).getLat()).isEqualTo(dtos.get(i).getLat().doubleValue());
            assertThat(entities.get(i).getLon()).isEqualTo(dtos.get(i).getLon().doubleValue());
        }

    }

    @Test
    public void waypointDtoToEntity() {
        // given:
        WaypointDto dto = createWaypointDto(
            new BigDecimal(100), new BigDecimal(-100), "waypoint 1", "/static/wpt/Waypoint");

        // when:
        Waypoint entity = converter.toEntity(dto);

        // then:
        assertWaypoint(Collections.singletonList(entity), Collections.singletonList(dto));
    }

    private XMLGregorianCalendar getXMLGregorianCalendar(Date time) throws DatatypeConfigurationException {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(time);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
    }

    private LinkDto getLinkDto(String text, String href) {
        LinkDto result = new LinkDto();
        result.setText(text);
        result.setHref(href);
        return result;
    }

    private MetadataDto createMetadataDto(String author, String desc, String name, String linkText,
        String linkHref, Date time) throws DatatypeConfigurationException {

        MetadataDto dto = new MetadataDto();
        dto.setAuthor(author);
        dto.setDesc(desc);
        dto.setName(name);
        dto.setTime(getXMLGregorianCalendar(time));
        dto.setLink(getLinkDto(linkText, linkHref));
        return dto;
    }

    private void assertMetadata(Metadata entity, MetadataDto dto) {
        assertThat(entity.getName()).isEqualTo(dto.getName());
        assertThat(entity.getAuthor()).isEqualTo(dto.getAuthor());
        assertThat(entity.getDescription()).isEqualTo(dto.getDesc());
        assertThat(entity.getLinkText()).isEqualTo(dto.getLink().getText());
        assertThat(entity.getLinkHref()).isEqualTo(dto.getLink().getHref());
        assertThat(entity.getTime()).isEqualToIgnoringSeconds(dto.getTime().toGregorianCalendar().getTime());
    }

    @Test
    public void metadataDtoToEntity() throws DatatypeConfigurationException {
        // given:
        Date currentTimestamp = new Date();
        MetadataDto dto = createMetadataDto("John", "this is John's GPX", "John's GPX", "john-gpx",
            "https://www.johngpx.com", currentTimestamp);

        // when:
        Metadata entity = converter.toEntity(dto);

        // then:
        assertMetadata(entity, dto);
    }

    @Test
    public void GpsDtoToEntity() throws DatatypeConfigurationException {
        // given:
        Date currentTimestamp = new Date();
        Gpx dto = new Gpx();

        dto.setMetadata(createMetadataDto("John", "this is John's GPX", "John's GPX", "john-gpx",
            "https://www.johngpx.com", currentTimestamp));

        dto.getWpt().add(createWaypointDto(new BigDecimal(100), new BigDecimal(-100), "waypoint 1",
            "/static/wpt/Waypoint1"));
        dto.getWpt().add(createWaypointDto(new BigDecimal(200), new BigDecimal(-200), "waypoint 2",
            "/static/wpt/Waypoint2"));
        dto.getWpt().add(createWaypointDto(new BigDecimal(300), new BigDecimal(-300), "waypoint 3",
            "/static/wpt/Waypoint3"));


        // when:
        GPS entity = converter.toEntity(dto);

        // then:
        assertMetadata(entity.getMetadata(), dto.getMetadata());
        assertWaypoint(entity.getWaypoints(), dto.getWpt());
    }
}
