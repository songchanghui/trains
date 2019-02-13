package com.thoughtworks.exception;

/**
 * 图-数据文件 异常类
 * Created by songchanghui on 2019/2/11.
 */
public class GraphException extends RuntimeException  {
    static final long serialVersionUID = 7818375828146090155L;
    /**
     * Constructs an {@code GraphException} with {@code null}
     * as its error detail message.
     */
    public GraphException() {
        super();
    }

    /**
     * Constructs an {@code GraphException} with the specified detail message.
     *
     * @param message
     *        The detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method)
     */
    public GraphException(String message) {
        super(message);
    }

    /**
     * Constructs an {@code GraphException} with the specified detail message
     * and cause.
     *
     * <p> Note that the detail message associated with {@code cause} is
     * <i>not</i> automatically incorporated into this exception's detail
     * message.
     *
     * @param message
     *        The detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method)
     *
     * @param cause
     *        The cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A null value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     *
     * @since 1.6
     */
    public GraphException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an {@code GraphException} with the specified cause and a
     * detail message of {@code (cause==null ? null : cause.toString())}
     * (which typically contains the class and detail message of {@code cause}).
     * This constructor is useful for GraphFile exceptions that are little more
     * than wrappers for other throwables.
     *
     * @param cause
     *        The cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A null value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     *
     * @since 1.6
     */
    public GraphException(Throwable cause) {
        super(cause);
    }

}
