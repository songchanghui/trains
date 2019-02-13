package com.thoughtworks.graphx;


import java.io.Serializable;

/**
 * 图
 * Created by songchanghui on 2019/1/29.
 */
public class Edge implements Serializable {
    private static final long serialVersionUID = -2264865738144119751L;
    private Long id;//边id
    private String name;//边名称
    private String srcId;//源顶点id
    private String srcName;//源顶点名称
    private String dstName;//目标顶点名称
    private String dstId;//目标顶点id
    private Long attr;//边属性值

    public Edge(Long id, String name, String srcId, String srcName, String dstName, String dstId, Long attr) {
        this.id = id;
        this.name = name;
        this.srcId = srcId;
        this.srcName = srcName;
        this.dstName = dstName;
        this.dstId = dstId;
        this.attr = attr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public String getDstId() {
        return dstId;
    }

    public void setDstId(String dstId) {
        this.dstId = dstId;
    }

    public String getSrcName() {
        return srcName;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }

    public String getDstName() {
        return dstName;
    }

    public void setDstName(String dstName) {
        this.dstName = dstName;
    }

    public Long getAttr() {
        return attr;
    }

    public void setAttr(Long attr) {
        this.attr = attr;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        final Edge edge = (Edge) obj;
        if (this == edge) {
            return true;
        } else {
            return this.id.equals(edge.id);
        }
    }

    @Override
    public int hashCode() {
        int hashno = 7;
        hashno = 13 * hashno + (id == null ? 0 : id.hashCode());
        return hashno;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", srcId='" + srcId + '\'' +
                ", srcName='" + srcName + '\'' +
                ", dstName='" + dstName + '\'' +
                ", dstId='" + dstId + '\'' +
                ", attr=" + attr +
                '}';
    }

}
