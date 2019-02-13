package com.thoughtworks.exception;

/**
 * 图-数据文件 异常类
 * Created by songchanghui on 2019/2/11.
 */
public class GraphException extends RuntimeException  {
    static final long serialVersionUID = 7818375828146090155L;

    public GraphException() {
        super();
    }

    /**
     * Constructs an GraphException with the specified detail message.
     * @param message
     */
    public GraphException(String message) {
        super(message);
    }

    /**
     * Constructs an GraphException with the specified detail message
     * and cause.
     *
     * @param message
     * @param cause
     *
     * @since 1.6
     */
    public GraphException(String message, Throwable cause) {
        super(message, cause);
    }

    public GraphException(Throwable cause) {
        super(cause);
    }

}
