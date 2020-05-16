package com.example.demo.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "gps")
public class GPS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "metadata_id")
    private Metadata metadata;

    @OneToMany(mappedBy = "gps", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Waypoint> waypoints;

    @OneToMany(mappedBy = "gps", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Track> tracks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<Waypoint> getWaypoints() {
        if (waypoints == null) {
            waypoints = new ArrayList<>();
        }
        return waypoints;
    }

    public List<Track> getTracks() {
        if (tracks == null) {
            tracks = new ArrayList<>();
        }
        return tracks;
    }

}
