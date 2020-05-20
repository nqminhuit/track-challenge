package com.example.demo.helper;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import com.example.demo.domain.Waypoint;
import com.example.demo.dto.WaypointDto;

public class Constants {

    public static final SimpleDateFormat SDT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");

    public static final Comparator<Waypoint> BY_WAYPOINT_ID = new Comparator<Waypoint>() {

        @Override
        public int compare(Waypoint tp1, Waypoint tp2) {
            return tp1.getId().compareTo(tp2.getId());
        }
    };

    public static final Comparator<Waypoint> BY_WAYPOINT_TIME = new Comparator<Waypoint>() {

        @Override
        public int compare(Waypoint tp1, Waypoint tp2) {
            return tp1.getTime().compareTo(tp2.getTime());
        }
    };

    public static final Comparator<WaypointDto> BY_WAYPOINTDTO_LAT = new Comparator<WaypointDto>() {

        @Override
        public int compare(WaypointDto tp1, WaypointDto tp2) {
            return tp1.getLat().compareTo(tp2.getLat());
        }
    };

    public static final Comparator<Waypoint> BY_WAYPOINT_NAME = new Comparator<Waypoint>() {

        @Override
        public int compare(Waypoint wp1, Waypoint wp2) {
            return wp1.getName().compareTo(wp2.getName());
        }
    };

}
