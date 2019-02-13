package com.thoughtworks.graphx.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 序列列表
 * 通过此类可以直接使用 已定义自增序列
 * Created by songchanghui on 2019/2/11.
 */
public class Sequences {
    //自增序列 edgeId
    private static final AtomicLong edgeId = new AtomicLong();

    private static final AtomicLong vertexId = new AtomicLong();

    public static AtomicLong getEdgeId() {
        return edgeId;
    }

    public static AtomicLong getVertexId() {
        return vertexId;
    }
}
