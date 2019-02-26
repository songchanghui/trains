package com.thoughtworks.graphx.lib;

import com.thoughtworks.graphx.EdgeTriplet;
import com.thoughtworks.graphx.Pregel;
import com.thoughtworks.graphx.Vertex;
import com.thoughtworks.graphx.factory.GraphProxyFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songchanghui on 2019/2/22.
 */
public class TrainsDuration extends Pregel {
    private List<String> towns ;

    public TrainsDuration(List<String> towns) {
        this.towns = towns;
    }

    public List<String> getTowns() {
        return towns;
    }

    public void setTowns(List<String> towns) {
        this.towns = towns;
    }

    /**
     * 初始化顶点消息
     */
    @Override
    public void initialMessage() {
        String key = towns.get(0) + "_" + towns.get(1) ;
        EdgeTriplet edgeTriplet = getGraph().getEdgeTripletMap().get(key);
        List edgeTriplets = new ArrayList<>();
        edgeTriplets.add(edgeTriplet);
        sendMsg( towns.get(0),edgeTriplets);
    }

    /**
     * path过滤结果集,使用此方法将 path集合  过滤至结果集
     *
     * @param path
     * @return
     */
    @Override
    public boolean pathFilter(String path) {
        return path.split(GraphProxyFactory.GRAPH_FILE_SPLIT).length < towns.size() + 1 && path.startsWith(towns.get(0)) && path.endsWith(towns.get(towns.size()-1));
    }

    /**
     * 顶点计算
     *
     * @param edgeTriplet
     */
    @Override
    public void vertexProgram(EdgeTriplet edgeTriplet) {
        int curIterations = getCurIterations() + 1;
        //获取源点
        Vertex srcVertex = getGraph().getVertexMap().get(edgeTriplet.getSrcId());
        //获取目标顶点
        Vertex dstVertex = getGraph().getVertexMap().get(edgeTriplet.getDstId());
        //只处理数组中的目标顶点id
        if (towns.get(curIterations).equals(edgeTriplet.getDstId())) {
            Long attr = srcVertex.getAttr() + edgeTriplet.getAttr();
            //设置目标顶点的值
            dstVertex.setAttr(attr);
            if (!towns.get(towns.size()-1).equals(edgeTriplet.getDstId())) {
                dstVertex.setActive(true);
            }
        }
    }
}
