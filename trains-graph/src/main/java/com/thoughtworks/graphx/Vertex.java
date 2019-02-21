package com.thoughtworks.graphx;

import java.io.Serializable;

/**
 * 顶点
 * Created by songchanghui on 2019/1/29.
 */
public class Vertex implements Serializable {
    private static final long serialVersionUID = 7071182029959793993L;
    private String id;//顶点id
    private String name;//顶点名称
    private Long attr;//顶点属性值
    private boolean active;//活跃状态

    public Vertex(String id, String name) {
        this.id = id;
        this.name = name;
        this.attr = 0L;
    }

    public Vertex(String id, String name, Long attr) {
        this.id = id;
        this.name = name;
        this.attr = attr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAttr() {
        return attr;
    }

    public void setAttr(Long attr) {
        this.attr = attr;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        final Vertex vertex = (Vertex) obj;
        if (this == vertex) {
            return true;
        } else {
            return this.id.equals(vertex.id);
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
        return "Vertex{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", attr=" + attr +
                ", active=" + active +
                '}';
    }
}
