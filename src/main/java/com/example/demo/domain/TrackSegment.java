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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "track_segment")
public class TrackSegment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "track_id")
    private Track track;

    @OneToMany(mappedBy = "trackSegment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Waypoint> trackPoints = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public Set<Waypoint> getTrackPoints() {
        return trackPoints;
    }

    public void addAllTrackPoints(Collection<Waypoint> trackPoints) {
        trackPoints.forEach(trackPoint -> {
            trackPoint.setTrackSegment(this);
            this.trackPoints.add(trackPoint);
        });
    }

}
