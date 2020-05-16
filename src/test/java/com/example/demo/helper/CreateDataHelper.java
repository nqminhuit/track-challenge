package com.example.demo.helper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import com.example.demo.domain.Metadata;
import com.example.demo.domain.Track;
import com.example.demo.domain.TrackPoint;
import com.example.demo.domain.TrackSegment;
import com.example.demo.domain.Waypoint;
import com.example.demo.dto.LinkDto;
import com.example.demo.dto.MetadataDto;
import com.example.demo.dto.WaypointDto;

public class CreateDataHelper {

    public static WaypointDto createWaypointDto(BigDecimal lat, BigDecimal lon, String name, String sym) {
        WaypointDto dto = new WaypointDto();
        dto.setLat(lat);
        dto.setLon(lon);
        dto.setName(name);
        dto.setSym(sym);
        return dto;
    }

    public static LinkDto createLinkDto(String text, String href) {
        LinkDto result = new LinkDto();
        result.setText(text);
        result.setHref(href);
        return result;
    }

    private static XMLGregorianCalendar getXMLGregorianCalendar(Date time)
        throws DatatypeConfigurationException {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(time);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
    }

    public static MetadataDto createMetadataDto(String author, String desc, String name, String linkText,
        String linkHref, Date time) throws DatatypeConfigurationException {

        MetadataDto dto = new MetadataDto();
        dto.setAuthor(author);
        dto.setDesc(desc);
        dto.setName(name);
        dto.setTime(getXMLGregorianCalendar(time));
        dto.setLink(CreateDataHelper.createLinkDto(linkText, linkHref));
        return dto;
    }

    public static Waypoint createWaypointEntity(Long id, Double lat, Double lon, String name, String sym) {
        Waypoint entity = new Waypoint();
        entity.setId(id);
        entity.setLat(lat);
        entity.setLon(lon);
        entity.setName(name);
        entity.setSym(sym);
        entity.setGps(null);
        return entity;
    }

    public static Metadata createMetadataEntity(String author, String description, Long id, String linkHref,
        String linkText, String name, Date time) {

        Metadata entity = new Metadata();
        entity.setAuthor(author);
        entity.setDescription(description);
        entity.setId(id);
        entity.setLinkHref(linkHref);
        entity.setLinkText(linkText);
        entity.setName(name);
        entity.setTime(time);
        return entity;
    }

    public static Track createTrackEntity() {
        Track track = new Track();
        track.getTrackSegments().add(createTrackSegment());
        return track;
    }

    public static TrackSegment createTrackSegment() {
        TrackSegment trackSegment = new TrackSegment();
        for (Long i = 1L; i < 5; i++) {
            trackSegment.getTrackPoints().add(createTrackPoint(123.456D, i, 12.34D, -12.34D, new Date()));
        }
        return trackSegment;
    }

    public static TrackPoint createTrackPoint(Double ele, Long id, Double lat, Double lon, Date time) {
        TrackPoint trackPoint = new TrackPoint();
        trackPoint.setEle(ele);
        trackPoint.setId(id);
        trackPoint.setLat(lat);
        trackPoint.setLon(lon);
        trackPoint.setTime(time);
        return trackPoint;
    }
}
