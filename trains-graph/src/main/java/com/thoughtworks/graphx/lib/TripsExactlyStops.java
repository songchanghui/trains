package com.thoughtworks.graphx.lib;

import com.thoughtworks.graphx.EdgeTriplet;
import com.thoughtworks.graphx.Pregel;
import com.thoughtworks.graphx.Vertex;
import com.thoughtworks.graphx.factory.GraphProxyFactory;
import com.thoughtworks.graphx.util.ParmUtil;


/**
 * 获取由起始至终止顶点间的不同路径方案
 * Created by songchanghui on 2019/2/12.
 */
public class TripsExactlyStops extends Pregel {
    private String startVertex;
    private String endVertex;
    int maxStops;

    public TripsExactlyStops(String startVertex, String endVertex, int eactlyStops) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.maxStops = eactlyStops;
        setMaxIterations(maxStops);
    }

    @Override
    public void initialMessage() {
        sendMsg(startVertex);
    }

    @Override
    public void vertexProgram(EdgeTriplet edgeTriplet) {
        //获取目标顶点
        Vertex dstVertex = getGraph().getVertexMap().get(edgeTriplet.getDstId());
        dstVertex.setActive(true);
    }

    @Override
    public boolean pathFilter(String path) {
        if (path.split(GraphProxyFactory.GRAPH_FILE_SPLIT).length == maxStops + 1 && path.startsWith(startVertex) && path.endsWith(endVertex)) {
            return true;
        }
        return false;
    }
}
