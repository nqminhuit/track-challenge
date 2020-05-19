insert into metadata (id, name, desc, author, link_href, link_text, time) values
(-1, 'Washington DC', 'GPS of Washington DC, this is a test GPS!', 'Bruce Wayne',
'https://www.google.com','WashingtonMap', {ts '2020-05-16 19:00:52.00'});

insert into gps (id, metadata_id) values (-1, -1);

insert into track (id, gps_id) values (-1, -1);

insert into waypoint (id, gps_id, lat, lon, name, sym) values
(-1, -1, 55, -55, 'waypoint 1', '/static/wpt/Waypoint'),
(-2, -1, 65, -55, 'waypoint 2', '/static/wpt/Waypoint'),
(-3, -1, 75, -55, 'waypoint 3', '/static/wpt/Waypoint'),
(-4, -1, 85, -55, 'waypoint 4', '/static/wpt/Waypoint');

insert into track_segment (id, track_id) values (-1, -1);

insert into track_point (id, track_seg_id, lat, lon, ele, time) values
(-1, -1, 10.0, -10.0, 11.11, {ts '2020-05-16 18:47:52.00'}),
(-2, -1, 10.1, -10.1, 11.12, {ts '2020-05-16 18:48:52.00'}),
(-3, -1, 10.2, -10.2, 11.13, {ts '2020-05-16 18:49:52.00'}),
(-4, -1, 10.3, -10.3, 11.12, {ts '2020-05-16 18:50:52.00'}),
(-5, -1, 10.4, -10.4, 11.13, {ts '2020-05-16 18:51:52.00'}),
(-6, -1, 10.5, -10.5, 11.11, {ts '2020-05-16 18:52:52.00'});
