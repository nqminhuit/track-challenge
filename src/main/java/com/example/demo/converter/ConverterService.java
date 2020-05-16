package com.example.demo.converter;

import java.util.List;
import java.util.stream.Collectors;
import com.example.demo.domain.GPS;
import com.example.demo.domain.Metadata;
import com.example.demo.domain.Waypoint;
import com.example.demo.dto.Gpx;
import com.example.demo.dto.MetadataDto;
import com.example.demo.dto.WaypointDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConverterService {

    private ModelMapper modelMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GPS toEntity(Gpx dto) {
        GPS gps = new GPS();
        gps.setMetadata(toEntity(dto.getMetadata()));
        List<Waypoint> waypoints = dto.getWpt().stream().map(this::toEntity).collect(Collectors.toList());
        gps.setWaypoints(waypoints);
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

}
