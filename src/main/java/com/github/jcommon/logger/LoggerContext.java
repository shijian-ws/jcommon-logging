package com.github.jcommon.logger;

/**
 * 日志提供者
 *
 * @author shijian
 * @email shijianws@163.com
 * @date 2019-11-09
 */
public interface LoggerContext {
    /**
     * 获取日志输出对象
     */
    Logger getLogger(Class<?> clazz);

    /**
     * 获取日志输出对象
     */
    Logger getLogger(String name);

    /**
     * 获取日志的上下文
     */
    MDC getContext();

    /**
     * 添加监听器
     */
    default void addListener(LoggerContextListener listener) {
        // todo
    }
}
