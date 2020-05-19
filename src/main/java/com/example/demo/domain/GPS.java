package com.example.demo.domain;

import java.util.Collection;
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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "metadata_id")
    private Metadata metadata;

    @OneToMany(mappedBy = "gps", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Waypoint> waypoints = new HashSet<>();

    @OneToMany(mappedBy = "gps", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Track> tracks = new HashSet<>();

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
        return waypoints;
    }

    public void addAllWaypoints(Collection<Waypoint> waypoints) {
        waypoints.forEach(waypoint -> {
            waypoint.setGps(this);
            this.waypoints.add(waypoint);
        });
    }

    public Set<Track> getTracks() {
        return tracks;
    }

    public void addAllTracks(Collection<Track> tracks) {
        tracks.forEach(track -> {
            track.setGps(this);
            this.tracks.add(track);
        });
    }

}
