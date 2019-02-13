package com.thoughtworks.graphx.util;

/**
 * 参数处理工具类
 * Created by songchanghui on 2019/2/11.
 */
public class ParmUtil {
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
