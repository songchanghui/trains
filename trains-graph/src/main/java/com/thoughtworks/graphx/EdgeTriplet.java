package com.thoughtworks.graphx;

/**
 * 边-三元组  记录两顶点之间的路径和向量
 * Created by songchanghui on 2019/2/6.
 */
public class EdgeTriplet extends Edge {
    private Long srcAttr;//源顶点属性值
    private Long dstAttr;//目标顶点属性值

    public EdgeTriplet(Long id, String name, String srcId, String srcName, String dstName, String dstId, Long attr) {
        super(id, name, srcId, srcName, dstName, dstId, attr);
    }

    public Long getSrcAttr() {
        return srcAttr;
    }

    public void setSrcAttr(Long srcAttr) {
        this.srcAttr = srcAttr;
    }

    public Long getDstAttr() {
        return dstAttr;
    }

    public void setDstAttr(Long dstAttr) {
        this.dstAttr = dstAttr;
    }
}
