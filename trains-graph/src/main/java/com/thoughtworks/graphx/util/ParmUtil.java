package com.thoughtworks.graphx.util;

import com.thoughtworks.graphx.Graph;

/**
 * 参数处理工具类
 * Created by songchanghui on 2019/2/11.
 */
public class ParmUtil {
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;

    /**
     * 往数组指定位置添加 element
     * @param objects
     * @param element
     * @param index
     * @return
     */
    public static Object[] addElement(Object[] objects, Object element, int index) {
        int length = objects.length;
        Object destination[] = new Object[length + 1];
        System.arraycopy(objects, 0, destination, 0, index);
        destination[index] = element;
        System.arraycopy(objects, index, destination, index
                + 1, length - index);
        return destination;
    }
}
