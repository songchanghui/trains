package com.thoughtworks.graphx.trains;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * 业务接口
 * Created by songchanghui on 2019/2/8.
 */
public interface TrainsAPI {

    /**
     * The distance of the route A-B-C
     *
     * @param vertex
     * @return
     */
    String getDistance(String[] vertex) throws ClassNotFoundException, URISyntaxException, InstantiationException, IllegalAccessException, IOException;

    /**
     * The number of trips starting at C and ending at C with a maximum of 3 stops.
     * In the sample data below, there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops).
     *
     * @param startVertex
     * @param endVertex
     * @param maxStops
     * @return
     */
    String getTripsLessInStops(String startVertex, String endVertex, int maxStops) throws ClassNotFoundException, URISyntaxException, InstantiationException, IllegalAccessException, IOException;

    /**
     * The number of trips starting at A and ending at C with exactly 4 stops.
     * In the sample data below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).
     *
     * @param startVertex
     * @param endVertex
     * @param maxStops
     * @return
     */
    String getTripsInExactlyStops(String startVertex, String endVertex, int maxStops) throws ClassNotFoundException, URISyntaxException, InstantiationException, IllegalAccessException, IOException;

    /**
     * The length of the shortest route (in terms of distance to travel) from A to C.
     *
     * @param startVertex
     * @param endVertex
     * @return
     */
    String getShortestDistance(String startVertex, String endVertex) throws ClassNotFoundException, URISyntaxException, InstantiationException, IllegalAccessException, IOException;

    /**
     * The number of different routes from C to C with a distance of less than 30.
     * In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.
     *
     * @param startVertex
     * @param endVertex
     * @param maxDistance
     * @return
     */
    String getTripsLessInDistance(String startVertex, String endVertex, int maxDistance) throws ClassNotFoundException, URISyntaxException, InstantiationException, IllegalAccessException, IOException;

    /**
     *
     * @param towns
     * @return
     */
    int getDuration(List<String> towns) throws Exception;
}
