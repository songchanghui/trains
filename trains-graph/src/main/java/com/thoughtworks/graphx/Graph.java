package com.thoughtworks.graphx;

import com.thoughtworks.graphx.factory.GraphProxyFactory;
import com.thoughtworks.graphx.util.ParmUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 图
 * Created by songchanghui on 2019/1/29.
 */
public class Graph implements Serializable {
    private static final long serialVersionUID = 6452406590666681886L;
    private List<Edge> edges;//边
    private List<Vertex> vertices;//顶点
    private Map<String, Vertex> vertexMap;//顶点map
    private Map<String, List<Edge>> edgeMap;//边map
    private Map<String, List<EdgeTriplet>> edgeTripletsMap;//边三元组map
    private Map<String, EdgeTriplet> edgeTripletMap;//边三元组map key:srcName+dstName,

    public Graph(List<Edge> edges, List<Vertex> vertices) {
        this.edges = edges;
        this.vertices = vertices;
    }

    public void updateEdges(int travelledTakes){
        this.edges.forEach(edge -> edge.setAttr(Long.valueOf(travelledTakes)));
    }

    public void updateVertexs(int stopsTime){
        this.vertices.forEach(vertex -> vertex.setAttr(Long.valueOf(stopsTime)));
    }
    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public Map<String, Vertex> getVertexMap() {
        return vertexMap;
    }

    public void setVertexMap(Map<String, Vertex> vertexMap) {
        this.vertexMap = vertexMap;
    }

    public Map<String, List<Edge>> getEdgeMap() {
        return edgeMap;
    }

    public void setEdgeMap(Map<String, List<Edge>> edgeMap) {
        this.edgeMap = edgeMap;
    }

    public Map<String, List<EdgeTriplet>> getEdgeTripletsMap() {
        return edgeTripletsMap;
    }

    public void setEdgeTripletsMap(Map<String, List<EdgeTriplet>> edgeTripletsMap) {
        this.edgeTripletsMap = edgeTripletsMap;
    }

    public Map<String, EdgeTriplet> getEdgeTripletMap() {
        return edgeTripletMap;
    }

    public void setEdgeTripletMap(Map<String, EdgeTriplet> edgeTripletMap) {
        this.edgeTripletMap = edgeTripletMap;
    }



    /**
     * 获取path的
     *
     * @return
     */
    public long getDuration(String path) {
        String[] paths = path.split(GraphProxyFactory.GRAPH_FILE_SPLIT);
        Map<String, EdgeTriplet> edgeTripletsMap = getEdgeTripletMap();
        long duration = ParmUtil.ZERO;
        for (int i = 0; i < paths.length - 1; i++) {
            duration += edgeTripletsMap.get(paths[i] + "_" + paths[i + 1]).getAttr();
            if(i< paths.length - 2){
                duration += getVertexMap().get(paths[i + 1]).getAttr();
            }
        }
        return duration;
    }
}
