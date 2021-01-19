package com.example.eindopdrachtcsdlarsrookenjaspervanes;

import com.example.eindopdrachtcsdlarsrookenjaspervanes.okhttp.OpenRouteService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertNotNull;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert .*;

    public class OpenRouteServiceTest {
//        private MockWebServer mockWebServer;
//
//        @Before
//        public void setUp() throws IOException, NoSuchAlgorithmException {
//            mockWebServer = new MockWebServer();
//            mockWebServer.start(0);
//        }
//
//        @After
//        public void breakDown() throws IOException {
//            mockWebServer.shutdown();
//        }
//
//        @Test
//        public void can_get_simple_directions() throws IOException {
//            // Arrange
//            mockWebServer.enqueue(new MockResponse()
//                    .setBody("{\"type\":\"FeatureCollection\",\"features\":[{\"bbox\":[4.777726,51.588325,4.778509,51.589644],\"type\":\"Feature\",\"properties\":{\"segments\":[{\"distance\":157.8,\"duration\":113.6,\"steps\":[{\"distance\":0.3,\"duration\":0.2,\"type\":11,\"instruction\":\"Head west on Catharinastraat\",\"name\":\"Catharinastraat\",\"way_points\":[0,1]},{\"distance\":156.5,\"duration\":112.7,\"type\":0,\"instruction\":\"Turn left onto Sint Annastraat\",\"name\":\"Sint Annastraat\",\"way_points\":[1,11]},{\"distance\":1.0,\"duration\":0.7,\"type\":1,\"instruction\":\"Turn right onto Sint Janstraat\",\"name\":\"Sint Janstraat\",\"way_points\":[11,12]},{\"distance\":0.0,\"duration\":0.0,\"type\":10,\"instruction\":\"Arrive at Sint Janstraat, straight ahead\",\"name\":\"-\",\"way_points\":[12,12]}]}],\"summary\":{\"distance\":157.8,\"duration\":113.6},\"way_points\":[0,12]},\"geometry\":{\"coordinates\":[[4.77773,51.589644],[4.777726,51.589643],[4.777797,51.589429],[4.777889,51.589198],[4.777956,51.589059],[4.777973,51.589026],[4.778135,51.588786],[4.778318,51.588538],[4.778373,51.588487],[4.778403,51.588459],[4.778481,51.588365],[4.778509,51.588332],[4.7785,51.588325]],\"type\":\"LineString\"}}],\"bbox\":[4.777726,51.588325,4.778509,51.589644],\"metadata\":{\"attribution\":\"openrouteservice.org | OpenStreetMap contributors\",\"service\":\"routing\",\"timestamp\":1607945975841,\"query\":{\"coordinates\":[[4.77773,51.589649],[4.778509,51.588321]],\"profile\":\"foot-walking\",\"format\":\"json\"},\"engine\":{\"version\":\"6.3.1\",\"build_date\":\"2020-12-10T15:35:43Z\",\"graph_date\":\"1970-01-01T00:00:00Z\"}}}"));
//
//            OpenRouteService openRouteService = new OpenRouteService();
//            GeoPoint start = new GeoPoint(51.58964907332292, 4.777730221422755);
//            GeoPoint end = new GeoPoint(51.58832121698855, 4.7785093444810505);
////
////            int expectedGeoPointAmount = 13;
////
////            // Act
////            SimpleDirectionsResponseBody simpleDirections = openRouteService.getSimpleDirections(start, end);
////
////            // Assert
////            assertNotNull(simpleDirections);
////            assertEquals(expectedGeoPointAmount, simpleDirections.getGeoPoints().size());
//        }
//
//        @Test
//        public void can_get_complex_directions() throws IOException {
//            // Arrange
//            mockWebServer.enqueue(new MockResponse()
//                    .setBody("{\"routes\":[{\"summary\":{\"distance\":306.1,\"duration\":220.39999999999998},\"segments\":[{\"distance\":157.8,\"duration\":113.6,\"steps\":[{\"distance\":0.3,\"duration\":0.2,\"type\":11,\"instruction\":\"Head west on Catharinastraat\",\"name\":\"Catharinastraat\",\"way_points\":[0,1]},{\"distance\":157.5,\"duration\":113.4,\"type\":0,\"instruction\":\"Turn left onto Sint Annastraat\",\"name\":\"Sint Annastraat\",\"way_points\":[1,12]},{\"distance\":0.0,\"duration\":0.0,\"type\":10,\"instruction\":\"Arrive at Sint Annastraat, on the left\",\"name\":\"-\",\"way_points\":[12,12]}]},{\"distance\":148.3,\"duration\":106.8,\"steps\":[{\"distance\":148.2,\"duration\":106.7,\"type\":11,\"instruction\":\"Head southeast on Molenstraat\",\"name\":\"Molenstraat\",\"way_points\":[12,17]},{\"distance\":0.1,\"duration\":0.1,\"type\":2,\"instruction\":\"Turn sharp left onto Kloosterplein\",\"name\":\"Kloosterplein\",\"way_points\":[17,18]},{\"distance\":0.0,\"duration\":0.0,\"type\":10,\"instruction\":\"Arrive at Kloosterplein, straight ahead\",\"name\":\"-\",\"way_points\":[18,18]}]}],\"bbox\":[4.777726,51.587453,4.78013,51.589644],\"geometry\":\"gb{yHycd\\\\??h@Ml@QZMDAn@a@p@c@HIDERODE?A\\\\e@p@qA`AiCTq@FM??\",\"way_points\":[0,12,18]}],\"bbox\":[4.777726,51.587453,4.78013,51.589644],\"metadata\":{\"attribution\":\"openrouteservice.org | OpenStreetMap contributors\",\"service\":\"routing\",\"timestamp\":1607946392476,\"query\":{\"coordinates\":[[4.777730221422755,51.58964907332292],[4.7785093444810505,51.58832121698855],[4.780131432187284,51.58745359236834]],\"profile\":\"foot-walking\",\"format\":\"json\"},\"engine\":{\"version\":\"6.3.1\",\"build_date\":\"2020-12-10T15:35:43Z\",\"graph_date\":\"1970-01-01T00:00:00Z\"}}}"));
//
//            OpenRouteService openRouteService = new OpenRouteService("", String.format("http://%s:%d", mockWebServer.getHostName(), mockWebServer.getPort()));
//            List<GeoPoint> geoPoints = new ArrayList<>(Arrays.asList(
//                    new GeoPoint(51.58964907332292, 4.777730221422755),
//                    new GeoPoint(51.58832121698855, 4.7785093444810505),
//                    new GeoPoint(51.58745359236834, 4.780131432187284)
//            ));
//
//            int expectedDistance = 306;
//            int expectedGeoPointAmount = 19;
//
////            // Act
////            ComplexDirectionsResponseBody complexDirections = openRouteService.getComplexDirections(geoPoints);
////
////            // Assert
////            assertNotNull(complexDirections);
////            assertEquals(expectedDistance, complexDirections.getDistance());
////            assertEquals(expectedGeoPointAmount, complexDirections.getGeoPoints().size());
//        }
//
//        @Test
//        public void throws_exception_when_no_connection() throws IOException {
//            // Arrange
//            mockWebServer.shutdown();
//
//            OpenRouteService openRouteService = new OpenRouteService("", String.format("http://%s:%d", mockWebServer.getHostName(), mockWebServer.getPort()));
//            GeoPoint start = new GeoPoint(51.58964907332292, 4.777730221422755);
//            GeoPoint end = new GeoPoint(51.58832121698855, 4.7785093444810505);
//
//            // Act & Assert
//            assertThrows(IOException.class, () -> {
//                openRouteService.getSimpleDirections(start, end);
//            });
//        }
    }



