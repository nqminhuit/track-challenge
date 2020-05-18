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
public class DtoConverter {

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Gpx toDto(GPS entity) {
        List<WaypointDto> waypointDtos = entity.getWaypoints().stream().map(this::toDto).collect(Collectors.toList());
        List<TrackDto> trackDto = entity.getTracks().stream().map(this::toDto).collect(Collectors.toList());

        Gpx gpx = new Gpx();
        gpx.setMetadata(toDto(entity.getMetadata()));
        gpx.getWpt().addAll(waypointDtos);
        gpx.getTrk().addAll(trackDto);
        return gpx;
    }

    public MetadataDto toDto(Metadata entity) {
        MetadataDto dto = modelMapper.typeMap(Metadata.class, MetadataDto.class)
            .addMappings(mapper -> {
                mapper.map(src -> src.getDescription(), MetadataDto::setDesc);
            })
            .map(entity);
        LinkDto linkDto = new LinkDto();
        linkDto.setHref(entity.getLinkHref());
        linkDto.setText(entity.getLinkText());
        dto.setLink(linkDto);
        return dto;
    }

    public WaypointDto toDto(Waypoint entity) {
        return modelMapper.map(entity, WaypointDto.class);
    }

    private TrackDto toDto(Track entity) {
        TrackDto dto = new TrackDto();
        List<TrackSegmentDto> trackSegs = entity.getTrackSegments().stream().map(this::toDto).collect(Collectors.toList());
        dto.getTrkseg().addAll(trackSegs);
        return dto;
    }

    private TrackSegmentDto toDto(TrackSegment entity) {
        TrackSegmentDto dto = new TrackSegmentDto();
        List<TrackPointDto> points = entity.getTrackPoints().stream().map(this::toDto).collect(Collectors.toList());
        dto.getTrkpt().addAll(points);
        return dto;
    }

    private TrackPointDto toDto(TrackPoint entity) {
        return modelMapper.map(entity, TrackPointDto.class);
    }

}
