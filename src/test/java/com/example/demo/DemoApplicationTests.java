package com.example.demo;

import static com.example.demo.helper.AssertionHelper.assertThatGpsRetrieveCorrectlyFromDB;
import static com.example.demo.helper.AssertionHelper.assertThatGpxRetrievedAndConvertedCorrectly;
import static com.example.demo.helper.CreateDataHelper.createGpx;
import static com.example.demo.helper.CreateDataHelper.createTime;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Set;
import javax.xml.datatype.DatatypeConfigurationException;
import com.example.demo.controller.GpsController;
import com.example.demo.domain.GPS;
import com.example.demo.domain.Metadata;
import com.example.demo.domain.Track;
import com.example.demo.domain.TrackSegment;
import com.example.demo.dto.Gpx;
import com.example.demo.repository.GpsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private GpsController gpsController;

    @Autowired
    private GpsRepository gpsRepository;

    @Test
    public void loadGpsEntity_ShouldFetchAllListObjects() {
        // given:
        Long id = -1L;

        // when:
        GPS gps = gpsRepository.getFetchedGpsById(id);

        // then:
        assertThatGpsRetrieveCorrectlyFromDB(gps);
    }

    @Test
    public void getGpxById() {
        // given:
        Long id = -1L;

        // when:
        Gpx gpx = gpsController.getGpxById(id);

        // then:
        assertThatGpxRetrievedAndConvertedCorrectly(gpx);
    }

    @Test
    public void uploadGpx() throws DatatypeConfigurationException {
        // given:
        Gpx gpx = createGpx();

        // when:
        gpsController.uploadGps(gpx);

        // then:
        GPS gps = gpsRepository.getFetchedGpsById(1L);

        Metadata metadata = gps.getMetadata();
        assertThat(metadata.getAuthor()).isEqualTo("Jack Sparrow");
        assertThat(metadata.getDescription()).isEqualTo("This is the GPS of the Black Pearl!");
        assertThat(metadata.getName()).isEqualTo("Black Pearl Travel");
        assertThat(metadata.getLinkText()).isEqualTo("BlackPearl");
        assertThat(metadata.getLinkHref()).isEqualTo("https://www.blackpearl.com");
        assertThat(metadata.getTime()).isEqualToIgnoringMillis(createTime("2020-01-01 12:34:56.78"));

        // only need to test availability b/c
        // we already had test for the details at gpsRepository#getFetchedGpsById
        assertThat(gps.getWaypoints()).hasSize(3);
        Set<Track> tracks = gps.getTracks();
        assertThat(tracks).hasSize(1);
        Set<TrackSegment> trackSegments = tracks.iterator().next().getTrackSegments();
        assertThat(trackSegments).hasSize(1);
        assertThat(trackSegments.iterator().next().getTrackPoints()).hasSize(4);
    }

    @Test
    public void getLatestGpx() throws DatatypeConfigurationException {
        // given:
        gpsController.uploadGps(createGpx());
        gpsController.uploadGps(createGpx());
        gpsController.uploadGps(createGpx());
        gpsController.uploadGps(createGpx());

        // when:
        List<Gpx> gpxes = gpsController.getLatestGpses();

        // then:
        assertThat(gpxes).hasSize(5);
    }
}
