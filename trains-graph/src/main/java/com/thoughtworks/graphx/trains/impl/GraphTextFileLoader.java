package com.thoughtworks.graphx.trains.impl;


import com.thoughtworks.exception.GraphException;
import com.thoughtworks.graphx.Edge;
import com.thoughtworks.graphx.Graph;
import com.thoughtworks.graphx.Vertex;
import com.thoughtworks.graphx.trains.GraphLoader;
import com.thoughtworks.graphx.util.Sequences;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 有向图-装载 Text File
 * Created by songchanghui on 2019/2/8.
 */
public class GraphTextFileLoader implements GraphLoader {
    //装载图信息，此方法被代理 用于构建图信息
    public Graph loadFile(String file_path, String regex, int limit) throws IOException, GraphException, URISyntaxException {
        Path path = Paths.get(ClassLoader.getSystemResource(file_path).toURI());
        //读取文件
        BufferedReader bfr = Files.newBufferedReader(path);
        Stream<String> lines = bfr.lines();
        //创建 边集合
        List<Edge> edges = lines
                .map(line -> line.split(regex, limit))
                .map(edge -> buildEdge(edge))
                .collect(Collectors.toList());
        //创建顶点集合
        List<Vertex> vertices = edges.stream()
                .map(edge -> buildVertex(edge))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        //创建graph对象
        Graph graph = new Graph(edges, vertices);
        bfr.close();
        return graph;
    }

    /**
     * build 边对象
     *
     * @param edges
     * @return
     */
    private Edge buildEdge(String[] edges) {
        if (edges == null || edges.length != 3) {
            throw new GraphException("数据文件格式不正确");
        } else {
            Long edgeId = Sequences.getEdgeId().incrementAndGet();
            return new Edge(edgeId, edgeId.toString(), edges[0], edges[0], edges[1], edges[1], Long.parseLong(edges[2]));
        }
    }

    /**
     * build 顶点对象数组
     *
     * @param edge
     * @return
     */
    private Vertex[] buildVertex(Edge edge) {
        Vertex[] vertices = new Vertex[2];
        vertices[0] = new Vertex(edge.getSrcId(), edge.getSrcName());
        vertices[1] = new Vertex(edge.getDstId(), edge.getDstName());

        return vertices;
    }
}
