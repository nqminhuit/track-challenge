package com.example.demo.converter;

import static java.util.stream.Collectors.toList;
import java.util.List;
import com.example.demo.domain.GPS;
import com.example.demo.domain.Metadata;
import com.example.demo.domain.Track;
import com.example.demo.domain.TrackSegment;
import com.example.demo.domain.Waypoint;
import com.example.demo.dto.Gpx;
import com.example.demo.dto.MetadataDto;
import com.example.demo.dto.TrackDto;
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
        GPS gps = modelMapper.map(dto, GPS.class);
        gps.setMetadata(toEntity(dto.getMetadata()));

        List<Waypoint> waypoints = dto.getWpt().stream().map(this::toEntity).collect(toList());
        gps.addAllWaypoints(waypoints);

        List<Track> tracks = dto.getTrk().stream().map(this::toEntity).collect(toList());
        gps.addAllTracks(tracks);
        return gps;
    }

    public Metadata toEntity(MetadataDto dto) {
        Metadata entity = modelMapper.typeMap(MetadataDto.class, Metadata.class)
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
        entity.setTime(dto.getTime().toGregorianCalendar().getTime());
        return entity;
    }

    public Waypoint toEntity(WaypointDto dto) {
        Waypoint entity = modelMapper.map(dto, Waypoint.class);
        if (dto.getTime() != null) {
            entity.setTime(dto.getTime().toGregorianCalendar().getTime());
        }
        return entity;
    }

    public TrackSegment toEntity(TrackSegmentDto dto) {
        TrackSegment entity = new TrackSegment();
        List<Waypoint> trackPoints = dto.getTrkpt().stream().map(this::toEntity).collect(toList());
        entity.addAllTrackPoints(trackPoints);
        return entity;
    }

    public Track toEntity(TrackDto dto) {
        Track entity = new Track();
        List<TrackSegment> trackSegments = dto.getTrkseg().stream().map(this::toEntity).collect(toList());
        entity.addAllTrackSegments(trackSegments);
        return entity;
    }

}
