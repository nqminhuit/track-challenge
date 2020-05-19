package com.example.demo.helper;

import static java.util.Collections.singletonList;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import com.example.demo.domain.GPS;
import com.example.demo.domain.Metadata;
import com.example.demo.domain.Track;
import com.example.demo.domain.TrackPoint;
import com.example.demo.domain.TrackSegment;
import com.example.demo.domain.Waypoint;
import com.example.demo.dto.Gpx;
import com.example.demo.dto.LinkDto;
import com.example.demo.dto.MetadataDto;
import com.example.demo.dto.TrackDto;
import com.example.demo.dto.TrackPointDto;
import com.example.demo.dto.TrackSegmentDto;
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
            trackSegment.getTrackPoints().add(createTrackPoint(i, 123.456D, 12.34D, -12.34D, new Date()));
        }
        return trackSegment;
    }

    public static TrackPoint createTrackPoint(Long id, Double ele, Double lat, Double lon, Date time) {
        TrackPoint trackPoint = new TrackPoint();
        trackPoint.setEle(ele);
        trackPoint.setId(id);
        trackPoint.setLat(lat);
        trackPoint.setLon(lon);
        trackPoint.setTime(time);
        return trackPoint;
    }

    public static TrackDto createTrackDto() throws DatatypeConfigurationException {
        TrackDto dto = new TrackDto();
        dto.getTrkseg().add(createTrackSegmentDto());
        return dto;
    }

    private static TrackSegmentDto createTrackSegmentDto() throws DatatypeConfigurationException {
        TrackSegmentDto trkSeg = new TrackSegmentDto();
        trkSeg.getTrkpt()
            .add(createTrackPointDto(
                new BigDecimal(123.456),
                new BigDecimal(12.34),
                new BigDecimal(-12.34),
                getXMLGregorianCalendar(new Date())));

        trkSeg.getTrkpt()
            .add(createTrackPointDto(
                new BigDecimal(121.456),
                new BigDecimal(11.34),
                new BigDecimal(-11.34),
                getXMLGregorianCalendar(new Date())));

        trkSeg.getTrkpt()
            .add(createTrackPointDto(
                new BigDecimal(120.456),
                new BigDecimal(10.34),
                new BigDecimal(-10.34),
                getXMLGregorianCalendar(new Date())));

        trkSeg.getTrkpt()
            .add(createTrackPointDto(
                new BigDecimal(113.456),
                new BigDecimal(11.34),
                new BigDecimal(-1.34),
                getXMLGregorianCalendar(new Date())));
        return trkSeg;
    }

    private static TrackPointDto createTrackPointDto(BigDecimal ele, BigDecimal lat, BigDecimal lon,
        XMLGregorianCalendar time) {

        TrackPointDto dto = new TrackPointDto();
        dto.setEle(ele);
        dto.setLat(lat);
        dto.setLon(lon);
        dto.setTime(time);
        return dto;
    }

    public static Date createTime(String time) {
        try {
            return Constants.SDT.parse(time);
        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Gpx createGpx() throws DatatypeConfigurationException {
        Gpx gpx = new Gpx();
        gpx.setMetadata(
            createMetadataDto("Jack Sparrow",
                "This is the GPS of the Black Pearl!",
                "Black Pearl Travel",
                "BlackPearl",
                "https://www.blackpearl.com",
                new Date()));

        gpx.getWpt().add(createWaypointDto(
            new BigDecimal(110), new BigDecimal(10), "pointway 1", "/point/way/something"));
        gpx.getWpt().add(createWaypointDto(
            new BigDecimal(120), new BigDecimal(11), "pointway 2", "/point/way/something"));
        gpx.getWpt().add(createWaypointDto(
            new BigDecimal(130), new BigDecimal(10), "pointway 3", "/point/way/something"));

        List<TrackDto> trks = gpx.getTrk();
        trks.add(new TrackDto());
        List<TrackSegmentDto> trksegs = trks.get(0).getTrkseg();
        trksegs.add(new TrackSegmentDto());
        List<TrackPointDto> trkpts = trksegs.get(0).getTrkpt();
        trkpts.add(createTrackPointDto(
            new BigDecimal(1000),
            new BigDecimal(500),
            new BigDecimal(30),
            getXMLGregorianCalendar(new Date())));

        trkpts.add(createTrackPointDto(
            new BigDecimal(1010),
            new BigDecimal(501),
            new BigDecimal(30),
            getXMLGregorianCalendar(new Date())));

        trkpts.add(createTrackPointDto(
            new BigDecimal(1020),
            new BigDecimal(502),
            new BigDecimal(35),
            getXMLGregorianCalendar(new Date())));

        trkpts.add(createTrackPointDto(
            new BigDecimal(1030),
            new BigDecimal(502),
            new BigDecimal(30),
            getXMLGregorianCalendar(new Date())));

        return gpx;
    }

    public static GPS createGps() throws DatatypeConfigurationException {
        GPS gps = new GPS();
        gps.setMetadata(
            createMetadataEntity("Jack Sparrow",
                "This is the GPS of the Black Pearl!",
                1L,
                "https://www.blackpearl.com",
                "BlackPearl",
                "Black Pearl Travel",
                createTime("2020-01-01 12:34:56.78")));

        gps.addAllWaypoints(
            singletonList(createWaypointEntity(1L, 110D, 10D, "pointway 1", "/point/way/something")));

        List<Track> trks = new ArrayList<>();

        TrackSegment trackSegment = new TrackSegment();
        trackSegment.addAllTrackPoints(singletonList(createTrackPoint(1L,1000D,500D,30D,new Date())));

        Track track = new Track();
        track.addAllTrackSegments(singletonList(trackSegment));

        gps.addAllTracks(trks);
        return gps;
    }
}
