package com.example.demo.repository;

import com.example.demo.domain.GPS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GpsRepository extends JpaRepository<GPS, Long> {

    @Query("SELECT gps FROM GPS gps " +
        "JOIN FETCH gps.metadata " +
        "JOIN FETCH gps.waypoints " +
        "JOIN FETCH gps.tracks tracks " +
        "JOIN FETCH tracks.trackSegments trackSegments " +
        "JOIN FETCH trackSegments.trackPoints " +
        "where gps.id = :id")
    GPS getFetchedGpsById(@Param("id") Long id);

}
