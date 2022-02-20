package com.github.jcommon.logger;

/**
 * 日志输入接口
 *
 * @author shijian
 * @email shijianws@163.com
 * @date 2019-11-09
 */
public interface Logger {
    /**
     * 输出debug级别消息
     */
    void debug(String format, Object... arguments);

    /**
     * 输出debug级别异常消息
     */
    void debug(String msg, Throwable ex);

    /**
     * 输出debug级别异常消息
     */
    void debug(Throwable ex);

    /**
     * 输出info级别消息
     */
    void info(String format, Object... arguments);

    /**
     * 输出info级别异常消息
     */
    void info(String msg, Throwable ex);

    /**
     * 输出info级别异常消息
     */
    void info(Throwable ex);

    /**
     * 输出warn级别消息
     */
    void warn(String format, Object... arguments);

    /**
     * 输出warn级别异常消息
     */
    void warn(String msg, Throwable ex);

    /**
     * 输出warn级别异常消息
     */
    void warn(Throwable ex);

    /**
     * 输出error级别消息
     */
    void error(String format, Object... arguments);

    /**
     * 输出warn级别异常消息
     */
    void error(String msg, Throwable ex);

    /**
     * 输出warn级别异常消息
     */
    void error(Throwable ex);

    /**
     * 是否开启debug级别日期输出
     */
    boolean isDebugEnabled();

    /**
     * 是否开启info级别日期输出
     */
    boolean isInfoEnabled();

    /**
     * 是否开启warn级别日期输出
     */
    boolean isWarnEnabled();

    /**
     * 是否开启error级别日期输出
     */
    boolean isErrorEnabled();

    /**
     * 设置日志级别
     */
    default void setLevel(LoggerLevel level) {
        // todo
    }

    /**
     * 添加监听器
     */
    default void addListener(LoggerListener listener) {
        // todo
    }
}
