package com.example.demo.helper;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import com.example.demo.domain.TrackPoint;
import com.example.demo.domain.Waypoint;
import com.example.demo.dto.TrackPointDto;
import com.example.demo.dto.WaypointDto;

public class Constants {

    public static final SimpleDateFormat SDT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");

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

    public static final Comparator<TrackPointDto> BY_TRACKPOINTDTO_LAT = new Comparator<TrackPointDto>() {

        @Override
        public int compare(TrackPointDto tp1, TrackPointDto tp2) {
            return tp1.getLat().compareTo(tp2.getLat());
        }
    };

    public static final Comparator<Waypoint> BY_WAYPOINT_NAME = new Comparator<Waypoint>() {

        @Override
        public int compare(Waypoint wp1, Waypoint wp2) {
            return wp1.getName().compareTo(wp2.getName());
        }
    };

    public static final Comparator<WaypointDto> BY_WAYPOINTDTO_LAT = new Comparator<WaypointDto>() {

        @Override
        public int compare(WaypointDto wp1, WaypointDto wp2) {
            return wp1.getLat().compareTo(wp2.getLat());
        }
    };

}
