package com.thoughtworks.graphx.lib;

import com.thoughtworks.graphx.EdgeTriplet;
import com.thoughtworks.graphx.Pregel;
import com.thoughtworks.graphx.Vertex;
import com.thoughtworks.graphx.factory.GraphProxyFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取指定路径（给定顶点）的距离
 * Created by songchanghui on 2019/2/12.
 */
public class Distance extends Pregel {
    private String[] vertexs;

    public Distance(String[] vertexs) {
        this.vertexs = vertexs;
        setMaxIterations(vertexs.length - 1);
    }

    @Override
    public void initialMessage() {
        String key = vertexs[0] + "_" + vertexs[1];
        EdgeTriplet edgeTriplet = getGraph().getEdgeTripletMap().get(key);
        List edgeTriplets = new ArrayList<>();
        edgeTriplets.add(edgeTriplet);
        sendMsg(vertexs[0], edgeTriplets);
    }

    @Override
    public boolean pathFilter(String path) {
        if (path.split(GraphProxyFactory.GRAPH_FILE_SPLIT).length < vertexs.length + 1 && path.startsWith(vertexs[0]) && path.endsWith(vertexs[vertexs.length - 1])) {
            return true;
        }
        return false;
    }

    @Override
    public void vertexProgram(EdgeTriplet edgeTriplet) {
        int curIterations = getCurIterations() + 1;
        //获取源点
        Vertex srcVertex = getGraph().getVertexMap().get(edgeTriplet.getSrcId());
        //获取目标顶点
        Vertex dstVertex = getGraph().getVertexMap().get(edgeTriplet.getDstId());
        //只处理数组中的目标顶点id
        if (vertexs[curIterations].equals(edgeTriplet.getDstId())) {
            Long attr = srcVertex.getAttr() + edgeTriplet.getAttr();
            //设置目标顶点的值
            dstVertex.setAttr(attr);
            if (!vertexs[vertexs.length - 1].equals(edgeTriplet.getDstId())) {
                dstVertex.setActive(true);
            }
        }
    }

    public String[] getVertexs() {
        return vertexs;
    }

    public void setVertexs(String[] vertexs) {
        this.vertexs = vertexs;
    }
}
