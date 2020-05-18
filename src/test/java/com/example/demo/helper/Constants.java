package com.example.demo.helper;

import java.util.Comparator;
import com.example.demo.domain.TrackPoint;
import com.example.demo.domain.Waypoint;

public class Constants {

    public static final Comparator<TrackPoint> BY_TRACKPOINT_ID = new Comparator<TrackPoint>() {

        @Override
        public int compare(TrackPoint tp1, TrackPoint tp2) {
            return tp1.getId().compareTo(tp2.getId());
        }
    };

    public static final Comparator<TrackPoint> BY_TRACKPOINT_TIME = new Comparator<TrackPoint>() {

        @Override
        public int compare(TrackPoint tp1, TrackPoint tp2) {
            return tp1.getTime().compareTo(tp2.getTime());
        }
    };

    public static final Comparator<Waypoint> BY_WAYPOINT_NAME = new Comparator<Waypoint>() {

        @Override
        public int compare(Waypoint wp1, Waypoint wp2) {
            return wp1.getName().compareTo(wp2.getName());
        }
    };

}
