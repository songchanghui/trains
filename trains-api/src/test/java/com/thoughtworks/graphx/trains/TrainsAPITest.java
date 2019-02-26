package com.thoughtworks.graphx.trains;

import com.thoughtworks.graphx.Graph;
import com.thoughtworks.graphx.factory.GraphProxyFactory;
import com.thoughtworks.graphx.handler.GraphLoaderHandler;
import com.thoughtworks.graphx.trains.impl.TrainsImpl;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

/**
 * Created by songchanghui on 2019/2/12.
 */
public class TrainsAPITest {
    public static Logger logger = Logger.getLogger(TrainsAPITest.class.toString());

    /**
     * The distance of the route A-B-C  Test
     */
    @Test
    public void getDistanceTest() {
        try {
            logger.info("创建TrainsAPI 实例");
            TrainsAPI trainsAPI = new TrainsImpl();

            String[] vertexsIn = {"A", "B", "C"};
            String distance = trainsAPI.getDistance(vertexsIn);
            logger.info("The distance of the route A-B-C  is " + distance);

        } catch (IllegalAccessException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (URISyntaxException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }
    }



    /**
     * The number of trips starting at C and ending at C with a maximum of 3 stops.
     * In the sample data below, there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops).
     */
    @Test
    public void getTripsMaxStops() throws Exception {
            TrainsAPI trainsAPI = new TrainsImpl();

            String[] vertexsIn = {"C", "C"};
            String number = trainsAPI.getTripsLessInStops(vertexsIn[0], vertexsIn[1], 3);

            assertEquals("2",number);
    }

    @Test
    public void should_get_duration_among_A_B_C() throws Exception {
        List<String> towns =  new ArrayList<>();
        towns.add("A");
        towns.add("B");
        towns.add("C");
        TrainsImpl trains = new TrainsImpl();
        int duration =  trains.getDuration(towns);
        assertEquals(11,duration);
    }
    @Test
    public void should_get_duration_among_A_d() throws Exception {
        List<String> towns =  new ArrayList<>();
        towns.add("A");
        towns.add("D");
        TrainsImpl trains = new TrainsImpl();
        int duration =  trains.getDuration(towns);
        assertEquals(5,duration);
    }
    @Test
    public void should_get_duration_among_A_B_C_D() throws Exception {
        List<String> towns =  new ArrayList<>();
        towns.add("A");
        towns.add("B");
        towns.add("C");
        towns.add("D");
        TrainsImpl trains = new TrainsImpl();
        int duration =  trains.getDuration(towns);
        assertEquals(15,duration);
    }

    /**
     * The number of trips starting at A and ending at C with exactly 4 stops.
     * In the sample data below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).
     */
    @Test
    public void getTripsExactlyStops() {
        try {
            logger.info("创建TrainsAPI 实例");
            TrainsAPI trainsAPI = new TrainsImpl();

            String[] vertexsIn = {"A", "C"};
            String number = trainsAPI.getTripsInExactlyStops(vertexsIn[0], vertexsIn[1], 4);
            logger.info("The number of trips starting at A and ending at C with exactly 4 stops is " + number);
        } catch (IllegalAccessException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (URISyntaxException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * The length of the shortest route (in terms of distance to travel) from A to C.
     */
    @Test
    public void getShortestDistanceTest() {
        try {
            logger.info("创建TrainsAPI 实例");
            TrainsAPI trainsAPI = new TrainsImpl();

            String[] vertexsIn = {"A", "C"};
            String length = trainsAPI.getShortestDistance(vertexsIn[0], vertexsIn[1]);
            logger.info("The length of the shortest route (in terms of distance to travel) from A to C is " + length);
        } catch (IllegalAccessException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (URISyntaxException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * The number of different routes from C to C with a distance of less than 30.
     * In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.
     */
    @Test
    public void getTripsMaxDistanceTest() {
        try {
            logger.info("创建TrainsAPI 实例");
            TrainsAPI trainsAPI = new TrainsImpl();

            String[] vertexsIn = {"C", "C"};
            String number = trainsAPI.getTripsLessInDistance(vertexsIn[0], vertexsIn[1], 30);
            logger.info("The length of the shortest route (in terms of distance to travel) from A to C is " + number);
        } catch (IllegalAccessException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (URISyntaxException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }
    }
}
