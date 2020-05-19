package com.example.demo;

import static com.example.demo.helper.AssertionHelper.assertThatGpsRetrieveCorrectlyFromDB;
import static com.example.demo.helper.AssertionHelper.assertThatGpxRetrievedAndConvertedCorrectly;
import static com.example.demo.helper.CreateDataHelper.createGpx;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Date;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
        assertThat(metadata.getTime()).isEqualToIgnoringSeconds(new Date());

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
    public void getLatestGpxes() throws DatatypeConfigurationException {
        // given: upload 4 more gpxes
        gpsController.uploadGps(createGpx());
        gpsController.uploadGps(createGpx());
        gpsController.uploadGps(createGpx());
        gpsController.uploadGps(createGpx());

        // when:
        List<Gpx> gpxes = gpsController.getLatestGpses(0, 10);

        // then:
        assertThat(gpxes).hasSize(5); // 4 + 1 pre-loaded
    }

    @Test
    public void getLatestGpxes_GpxesShouldBeInOrderByMetadataTime() throws DatatypeConfigurationException {
        // given:
        gpsController.uploadGps(createGpx());
        gpsController.uploadGps(createGpx());
        gpsController.uploadGps(createGpx());

        // when:
        List<Gpx> gpxes = gpsController.getLatestGpses(0, 10);

        // then:
        Date timeGpx0 = gpxes.get(0).getMetadata().getTime().toGregorianCalendar().getTime();
        Date timeGpx1 = gpxes.get(1).getMetadata().getTime().toGregorianCalendar().getTime();
        Date timeGpx2 = gpxes.get(2).getMetadata().getTime().toGregorianCalendar().getTime();
        Date timeGpx3 = gpxes.get(3).getMetadata().getTime().toGregorianCalendar().getTime();

        assertThat(timeGpx0).isAfter(timeGpx1);
        assertThat(timeGpx1).isAfter(timeGpx2);
        assertThat(timeGpx2).isAfter(timeGpx3);
    }

    @Test
    public void getLatestGpxesWithPagination() throws DatatypeConfigurationException {
        // given: upload 10 more gpxes
        gpsController.uploadGps(createGpx());
        gpsController.uploadGps(createGpx());
        gpsController.uploadGps(createGpx());
        gpsController.uploadGps(createGpx());
        gpsController.uploadGps(createGpx());
        gpsController.uploadGps(createGpx());
        gpsController.uploadGps(createGpx());
        gpsController.uploadGps(createGpx());
        gpsController.uploadGps(createGpx());
        gpsController.uploadGps(createGpx());

        // when:
        List<Gpx> gpxesFirstPage = gpsController.getLatestGpses(0, 10);
        List<Gpx> gpxesSecondPage = gpsController.getLatestGpses(1, 10);

        // then:
        assertThat(gpxesFirstPage).hasSize(10);
        assertThat(gpxesSecondPage).hasSize(1);
    }
}
