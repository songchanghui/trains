package com.thoughtworks.graphx.handler;

/**
 * 切面接口
 * Created by songchanghui on 2019/2/7.
 */
public interface Handler {
    /**
     * 切面方法 执行前
     */
    default void before(Object[] args) {
    }

    /**
     * 切面方法 执行后
     */
    default void after(Object[] args) {
    }
}
