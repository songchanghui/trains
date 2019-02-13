package com.thoughtworks.graphx.handler;

import com.thoughtworks.exception.GraphException;
import com.thoughtworks.graphx.Edge;
import com.thoughtworks.graphx.EdgeTriplet;
import com.thoughtworks.graphx.Graph;
import com.thoughtworks.graphx.Vertex;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 核心处理类
 * 用于Granphload后 构建Graph图结构
 * Created by songchanghui on 2019/2/11.
 */
public class GraphLoaderHandler implements Handler {

    @Override
    public void after(Object[] args) {
        if (args[0] == null || !(args[0] instanceof Graph)) {
            throw new GraphException("Graph 对象创建失败");
        } else {
            Graph graph = (Graph) args[0];
            //edgeMap Edge分组，key:源顶点id
            Map<String, List<Edge>> edgeMap = graph.getEdges().stream().collect(Collectors.groupingBy(Edge::getSrcId));
            //vertexMap Vertex分组，key:源顶点id
            Map<String, Vertex> vertexMap = graph.getVertices()
                    .stream()
                    .collect(Collectors.toMap(Vertex::getId, vertex -> vertex, (key1, key2) -> key2));
            //EdgeTriplet EdgeTriplet三元组分组，key:源顶点id
            Map<String, List<EdgeTriplet>> edgeTripletsMap = graph.getEdges().stream().map(edge -> buildEdgeTriplet(edge)).collect(Collectors.groupingBy(EdgeTriplet::getSrcId));
            //EdgeTriplet EdgeTriplet三元组分组，key:源顶点id+"_"+目标顶点id
            Map<String, EdgeTriplet> edgeTripletMap = graph.getEdges()
                    .stream()
                    .map(edge -> buildEdgeTriplet(edge))
                    .collect(Collectors.toMap(edgeTriplet -> edgeTriplet.getSrcName() + "_" + edgeTriplet.getDstName(), edgeTriplet -> edgeTriplet, (key1, key2) -> key2));
            //设置图属性
            graph.setEdgeMap(edgeMap);
            graph.setVertexMap(vertexMap);
            graph.setEdgeTripletsMap(edgeTripletsMap);
            graph.setEdgeTripletMap(edgeTripletMap);
        }
    }

    /**
     * build 三元组
     *
     * @param edge
     * @return
     */
    private EdgeTriplet buildEdgeTriplet(Edge edge) {
        return new EdgeTriplet(edge.getId(), edge.getName(), edge.getSrcId(), edge.getSrcName(), edge.getDstName(), edge.getDstId(), edge.getAttr());
    }
}
