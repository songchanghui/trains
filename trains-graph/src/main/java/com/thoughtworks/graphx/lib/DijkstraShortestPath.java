package com.thoughtworks.graphx.lib;

import com.thoughtworks.graphx.EdgeTriplet;
import com.thoughtworks.graphx.Pregel;
import com.thoughtworks.graphx.Vertex;

/**
 * Dijkstra 算法
 * 获取2个顶点之间的最短距离
 * Created by songchanghui on 2019/2/12.
 */
public class DijkstraShortestPath extends Pregel {
    /**
     * 发送消息
     *
     * @param vertexId
     */
    @Override
    public void sendMsg(String vertexId) {
        super.sendMsg(vertexId);
    }

    private String startVertex;
    private String endVertex;

    public DijkstraShortestPath(String startVertex, String endVertex) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
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
        //目标顶点值为0 则赋值原顶点 属性及边权值
        if (dstVertex.getAttr() == 0) {
            dstVertex.setAttr(edgeTriplet.getAttr() + edgeTriplet.getSrcAttr());
            dstVertex.setActive(true);
        } else {
            //迭代取最小值 赋值并 设置顶点为活跃
            if (min < dstAttr) {
                dstVertex.setAttr(min);
                dstVertex.setActive(true);
            }
        }
    }

    @Override
    public boolean pathFilter(String path) {
        if (path.startsWith(startVertex) && path.endsWith(endVertex)) {
            return true;
        }
        return false;
    }
}
