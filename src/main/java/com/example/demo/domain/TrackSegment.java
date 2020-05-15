package com.example.demo.domain;

import java.sql.Clob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// @Entity
// @Table(name = "track-segment")
public class TrackSegment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trkpts")
    private Clob trackPoints;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Clob getTrackPoints() {
        return trackPoints;
    }

    public void setTrackPoints(Clob trackPoints) {
        this.trackPoints = trackPoints;
    }

}
