package com.thoughtworks.graphx;


import com.thoughtworks.graphx.factory.GraphProxyFactory;
import com.thoughtworks.graphx.util.ParmUtil;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * 使用pregel 计算模型
 * Created by songchanghui on 2019/2/11.
 */
public abstract class Pregel {
    public static Logger logger = Logger.getLogger(Pregel.class.toString());
    public static final int DEFAULT_MAXITERATIONS = 50;//默认最大迭代数
    public static final int DEFAULT_MAXDISTANCE = 0;//0 表示不判断距离
    private int maxIterations;//最大迭代次数
    private int curIterations;//当前迭代数
    private int maxDistance;//最大距离
    private Graph graph;//图
    public static LinkedBlockingDeque<EdgeTriplet> linkedBlockingDeque = new LinkedBlockingDeque();//消息队列
    private List<String> paths;//路径集合
    private List<String> results;//路径结果集
    private boolean isRemovePaths;//results中添加集合时 是否从 paths中移除

    /**
     * 初始化顶点消息
     */
    public abstract void initialMessage();

    /**
     * 发送消息
     *
     * @param vertexId
     */
    public void sendMsg(String vertexId) {
        List<EdgeTriplet> edgeTriplets = graph.getEdgeTripletsMap().get(vertexId);
        sendMsg(vertexId, edgeTriplets);
    }

    /**
     * 向顶点发送 发送三元组消息
     *
     * @param edgeTriplets
     */
    public void sendMsg(String vertexId, List<EdgeTriplet> edgeTriplets) {
        addPaths(vertexId, edgeTriplets);
        edgeTriplets.stream().forEach(edgeTriplet -> {
            try {
                //设置原顶点属性
                edgeTriplet.setSrcAttr(getVertexAttr(edgeTriplet.getSrcId()));
                //设置目的顶点属性
                edgeTriplet.setDstAttr(getVertexAttr(edgeTriplet.getDstId()));
                //关闭顶点活跃
                graph.getVertexMap().get(edgeTriplet.getSrcId()).setActive(false);
                linkedBlockingDeque.put(edgeTriplet);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取指定顶点的  属性值
     *
     * @param vertexId
     * @return
     */
    public Long getVertexAttr(String vertexId) {
        return graph.getVertexMap().get(vertexId).getAttr();
    }

    /**
     * 添加路径，使用此方法，将消息传递路径添加进 paths
     *
     * @param vertexId
     * @param edgeTriplets
     */
    public void addPaths(String vertexId, List<EdgeTriplet> edgeTriplets) {
        //记录 图消息发送路径 并作为 结果集的 数据源使用
        if (paths == null) {
            paths = new ArrayList<>();
            results = new ArrayList<>();
            paths = edgeTriplets.stream()
                    .map(edgeTriplet -> edgeTriplet.getSrcName() + GraphProxyFactory.GRAPH_FILE_SPLIT + edgeTriplet.getDstName())
                    .collect(Collectors.toList());
        } else {
            paths = paths.stream()
                    .map(s -> buildPath(s, vertexId, edgeTriplets))
                    .flatMap(List::stream)
                    .filter(s1 -> s1.split(GraphProxyFactory.GRAPH_FILE_SPLIT).length < getMaxIterations() + ParmUtil.THREE)
                    .collect(Collectors.toList());
        }
        //结果集添加符合条件的path
        results.addAll(paths.stream().filter(path -> pathFilter(path)).collect(Collectors.toList()));
        //移除 超过距离的 path
        if (maxDistance != DEFAULT_MAXDISTANCE) {
            paths = paths.stream().filter(path -> getDistance(path) < maxDistance).collect(Collectors.toList());
        }
        if (!isRemovePaths) {
            paths = paths.stream().filter(path -> !pathFilter(path)).collect(Collectors.toList());
        }
    }

    /**
     * 构建路径 用于lamda Map
     *
     * @param path
     * @param edgeTriplets
     * @return
     */
    public List<String> buildPath(String path, String vertexId, List<EdgeTriplet> edgeTriplets) {
        if (path.endsWith(vertexId)) {
            return edgeTriplets.stream()
                    .map(edgeTriplet -> path + GraphProxyFactory.GRAPH_FILE_SPLIT + edgeTriplet.getDstName())
                    .collect(Collectors.toList());
        } else {
            List<String> pl = new ArrayList<>();
            pl.add(path);
            return pl;
        }
    }

    /**
     * path过滤结果集,使用此方法将 path集合  过滤至结果集
     *
     * @param path
     * @return
     */
    public abstract boolean pathFilter(String path);

    /**
     * pregel 运行
     */
    public void run() {
        curIterations = ParmUtil.ZERO;
        if (maxIterations == ParmUtil.ZERO) {
            maxIterations = DEFAULT_MAXITERATIONS;
        }
        logger.info("初始化消息体");
        initialMessage();
        EdgeTriplet edgeTriplet;
        logger.info("pregel超步，迭代开始");
        while (curIterations < maxIterations) {
            while ((edgeTriplet = linkedBlockingDeque.poll()) != null) {
                vertexProgram(edgeTriplet);
            }
            //当前超步 全部活跃节点
            List<Vertex> activeVertex = graph.getVertices().stream()
                    .filter(vertex -> vertex.isActive()).collect(Collectors.toList());
            //当前无活跃顶点  或 当前全部路径已超过最大距离
            if (activeVertex.size() == ParmUtil.ZERO || paths.size() == ParmUtil.ZERO) {
                break;
            }
            logger.info("源顶点发送消息");
            //遍历活跃节点发送消息
            activeVertex.stream().forEach(vertex -> sendMsg(vertex.getId()));

            curIterations++;
        }
    }

    /**
     * 获取path的距离
     *
     * @return
     */
    public long getDistance(String path) {
        String[] paths = path.split(GraphProxyFactory.GRAPH_FILE_SPLIT);
        Map<String, EdgeTriplet> edgeTripletsMap = getGraph().getEdgeTripletMap();
        long distance = ParmUtil.ZERO;
        for (int i = 0; i < paths.length - 1; i++) {
            distance += edgeTripletsMap.get(paths[i] + "_" + paths[i + 1]).getAttr();
        }
        return distance;
    }

    /**
     * 顶点计算
     */
    public abstract void vertexProgram(EdgeTriplet edgeTriplet);

    public int getMaxIterations() {
        return maxIterations;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public int getCurIterations() {
        return curIterations;
    }

    public void setCurIterations(int curIterations) {
        this.curIterations = curIterations;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public boolean isRemovePaths() {
        return isRemovePaths;
    }

    public void setRemovePaths(boolean removePaths) {
        isRemovePaths = removePaths;
    }
}
