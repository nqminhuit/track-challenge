package com.example.demo.converter;

import java.util.List;
import java.util.stream.Collectors;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityConverter {

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GPS toEntity(Gpx dto) {
        GPS gps = new GPS();
        gps.setMetadata(toEntity(dto.getMetadata()));

        List<Waypoint> waypoints = dto.getWpt().stream().map(this::toEntity).collect(Collectors.toList());
        gps.getWaypoints().addAll(waypoints);

        List<Track> tracks = dto.getTrk().stream().map(this::toEntity).collect(Collectors.toList());
        gps.getTracks().addAll(tracks);
        return gps;
    }

    public Metadata toEntity(MetadataDto dto) {
        return modelMapper.typeMap(MetadataDto.class, Metadata.class)
            .addMappings(mapper -> {
                mapper.map(src -> src.getDesc(), Metadata::setDescription);
            })
            .addMappings(mapper -> {
                mapper.map(src -> src.getLink().getHref(), Metadata::setLinkHref);
            })
            .addMappings(mapper -> {
                mapper.map(src -> src.getLink().getText(), Metadata::setLinkText);
            })
            .map(dto);
    }

    public Waypoint toEntity(WaypointDto dto) {
        return modelMapper.map(dto, Waypoint.class);
    }

    public TrackPoint toEntity(TrackPointDto dto) {
        return modelMapper.map(dto, TrackPoint.class);
    }

    public TrackSegment toEntity(TrackSegmentDto dto) {
        TrackSegment entity = new TrackSegment();
        List<TrackPoint> trackPoints = dto.getTrkpt().stream().map(this::toEntity).collect(Collectors.toList());
        entity.getTrackPoints().addAll(trackPoints);
        return entity;
    }

    public Track toEntity(TrackDto dto) {
        Track entity = new Track();
        List<TrackSegment> trackSegments = dto.getTrkseg().stream().map(this::toEntity).collect(Collectors.toList());
        entity.getTrackSegments().addAll(trackSegments);
        return entity;
    }

}
