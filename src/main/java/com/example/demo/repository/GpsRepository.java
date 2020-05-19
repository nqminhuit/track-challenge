package com.example.demo.repository;

import java.util.List;
import com.example.demo.domain.GPS;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GpsRepository extends CrudRepository<GPS, Long> {

    @Query("SELECT gps FROM GPS gps " +
        "JOIN FETCH gps.metadata " +
        "JOIN FETCH gps.waypoints " +
        "JOIN FETCH gps.tracks tracks " +
        "JOIN FETCH tracks.trackSegments trackSegments " +
        "JOIN FETCH trackSegments.trackPoints " +
        "where gps.id = :id")
    GPS getFetchedGpsById(@Param("id") Long id);

    @Query("SELECT gps FROM GPS gps JOIN FETCH gps.metadata metadata order by metadata.time desc")
    List<GPS> getAllGpsOnlyFetchMetadata(Pageable pageable);



}
