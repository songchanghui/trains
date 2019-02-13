package com.thoughtworks.graphx.lib;

import com.thoughtworks.graphx.EdgeTriplet;
import com.thoughtworks.graphx.Pregel;
import com.thoughtworks.graphx.Vertex;


/**
 * 获取由起始至终止顶点间的不同路径方案
 * Created by songchanghui on 2019/2/12.
 */
public class TripsMaxDistance extends Pregel {
    private String startVertex;
    private String endVertex;
    private int maxDistance;//最大距离

    public TripsMaxDistance(String startVertex, String endVertex, int maxDistance) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.maxDistance = maxDistance;
        setMaxDistance(maxDistance);
        setRemovePaths(true);
    }

    @Override
    public void initialMessage() {
        sendMsg(startVertex);
    }

    @Override
    public void vertexProgram(EdgeTriplet edgeTriplet) {
        //获取源点
        Vertex srcVertex = getGraph().getVertexMap().get(edgeTriplet.getSrcId());
        //获取目标顶点
        Vertex dstVertex = getGraph().getVertexMap().get(edgeTriplet.getDstId());
        Long dstAttr = dstVertex.getAttr();
        Long min = Math.min(edgeTriplet.getAttr() + edgeTriplet.getSrcAttr(), dstAttr);
        //目标顶点值为0
        if (dstVertex.getAttr() == 0) {
            dstVertex.setAttr(edgeTriplet.getAttr() + edgeTriplet.getSrcAttr());
        } else {
            if (min < dstAttr) {
                dstVertex.setAttr(min);
            }
        }
        dstVertex.setActive(true);
    }

    @Override
    public boolean pathFilter(String path) {
        return path.startsWith(startVertex)
                && path.endsWith(endVertex)
                && getDistance(path) < maxDistance;
    }


}
