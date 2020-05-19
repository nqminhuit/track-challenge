package com.example.demo.controller;

import static java.util.stream.Collectors.toList;
import java.util.List;
import com.example.demo.converter.DtoConverter;
import com.example.demo.converter.EntityConverter;
import com.example.demo.domain.GPS;
import com.example.demo.dto.Gpx;
import com.example.demo.repository.GpsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gps")
public class GpsController {

    @Autowired
    private GpsRepository gpsRepository;

    @Autowired
    private EntityConverter entityConverter;

    @Autowired
    private DtoConverter dtoConverter;

    @GetMapping("/latest")
    public List<Gpx> getLatestGpses(@RequestParam("page") int page, @RequestParam("size") int size) {
        return gpsRepository.getAllGpsOnlyFetchMetadata(new PageRequest(page, size))
            .stream()
            .map(entity -> dtoConverter.toDtoOnlyMetadata(entity))
            .collect(toList());
    }

    @GetMapping("/{id}")
    public Gpx getGpxById(@PathVariable("id") Long id) {
        GPS gps = gpsRepository.getFetchedGpsById(id);
        return dtoConverter.toDto(gps);
    }

    @PostMapping(path = "/upload", consumes = MediaType.TEXT_XML_VALUE)
    public void uploadGps(@RequestBody Gpx gpx) {
        GPS gps = entityConverter.toEntity(gpx);
        gpsRepository.save(gps);
    }

}
