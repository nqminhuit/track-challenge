package com.example.demo.domain;

import java.util.HashSet;
import java.util.Set;
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
    private Set<Waypoint> waypoints;

    @OneToMany(mappedBy = "gps", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Track> tracks;

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

    public Set<Waypoint> getWaypoints() {
        if (waypoints == null) {
            waypoints = new HashSet<>();
        }
        return waypoints;
    }

    public Set<Track> getTracks() {
        if (tracks == null) {
            tracks = new HashSet<>();
        }
        return tracks;
    }

}
