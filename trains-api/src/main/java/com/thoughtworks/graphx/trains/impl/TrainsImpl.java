package com.thoughtworks.graphx.trains.impl;

import com.thoughtworks.graphx.Edge;
import com.thoughtworks.graphx.Graph;
import com.thoughtworks.graphx.factory.GraphProxyFactory;
import com.thoughtworks.graphx.lib.*;
import com.thoughtworks.graphx.trains.TrainsAPI;
import com.thoughtworks.graphx.util.ParmUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * 业务接口实现类
 * Created by songchanghui on 2019/2/12.
 */
public class TrainsImpl implements TrainsAPI {
    public static Logger loger = Logger.getLogger(TrainsImpl.class.toString());

    /**
     * The distance of the route A-B-C
     *
     * @param vertex
     * @return
     */
    public String getDistance(String[] vertex) throws ClassNotFoundException, URISyntaxException, InstantiationException, IllegalAccessException, IOException {
        if (vertex.length <= 1) {
            return "0";
        }
        Distance distance = new Distance(vertex);
        distance.setGraph(GraphProxyFactory.getGraph());
        distance.run();
        List<String> paths = distance.getResults();
        if (paths.size() < 1 || !distance.pathFilter(paths.get(0))) {
            return GraphProxyFactory.NO_SUCH_ROUTE;
        }
        String[] ps = paths.get(0).split(GraphProxyFactory.GRAPH_FILE_SPLIT);
        return distance.getGraph().getVertexMap().get(ps[ps.length - 1]).getAttr().toString();
    }

    /**
     * The number of trips starting at C and ending at C with a maximum of 3 stops.
     * In the sample data below, there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops).
     *
     * @param startVertex
     * @param endVertex
     * @param maxStops
     * @return
     */
    public String getTripsMaxStops(String startVertex, String endVertex, int maxStops) throws ClassNotFoundException, URISyntaxException, InstantiationException, IllegalAccessException, IOException {
        TripsMaxStops tripsMaxStops = new TripsMaxStops(startVertex, endVertex, maxStops);
        tripsMaxStops.setGraph(GraphProxyFactory.getGraph());
        tripsMaxStops.run();
        return String.valueOf(tripsMaxStops.getResults().size());
    }

    /**
     * The length of the shortest route (in terms of distance to travel) from A to C.
     *
     * @param startVertex
     * @param endVertex
     * @return
     */
    public String getTripsExactlyStops(String startVertex, String endVertex, int exactlyStops) throws ClassNotFoundException, URISyntaxException, InstantiationException, IllegalAccessException, IOException {
        TripsExactlyStops tripsExactlyStops = new TripsExactlyStops(startVertex, endVertex, exactlyStops);
        tripsExactlyStops.setGraph(GraphProxyFactory.getGraph());
        tripsExactlyStops.run();
        return String.valueOf(tripsExactlyStops.getResults().size());
    }


    /**
     * The number of different routes from C to C with a distance of less than 30.
     * In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.
     *
     * @param startVertex
     * @param endVertex
     * @param maxDistance
     * @return
     */
    public String getTripsMaxDistance(String startVertex, String endVertex, int maxDistance) throws ClassNotFoundException, URISyntaxException, InstantiationException, IllegalAccessException, IOException {
        TripsMaxDistance tripsMaxDistance = new TripsMaxDistance(startVertex, endVertex, maxDistance);
        tripsMaxDistance.setGraph(GraphProxyFactory.getGraph());
        tripsMaxDistance.run();
        List<String> paths = tripsMaxDistance.getResults();
        paths = paths.stream().distinct().collect(Collectors.toList());
        return String.valueOf(paths.size());
    }

    /**
     * The length of the shortest route (in terms of distance to travel) from A to C.
     *
     * @param startVertex
     * @param endVertex
     * @return
     */
    public String getShortestDistance(String startVertex, String endVertex) throws ClassNotFoundException, URISyntaxException, InstantiationException, IllegalAccessException, IOException {
        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(startVertex, endVertex);
        dijkstraShortestPath.setGraph(GraphProxyFactory.getGraph());
        dijkstraShortestPath.run();
        return dijkstraShortestPath.getGraph().getVertexMap().get(endVertex).getAttr().toString();
    }
}
