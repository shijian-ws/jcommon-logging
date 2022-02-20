package com.github.jcommon.logger.internals;

import com.github.jcommon.logger.Logger;
import com.github.jcommon.logger.LoggerContext;
import com.github.jcommon.logger.LoggerContextListener;
import com.github.jcommon.logger.MDC;

/**
 * 自动适配日志
 *
 * @author shijian
 * @email shijianws@163.com
 * @date 2019-11-23
 */
public class AdaptLoggerContext implements LoggerContext {
    /**
     * slf4j日志类的全限定名称
     */
    private static final String SLF4J_LOGGER_CLASS_NAME = "org.slf4j.Logger";

    /**
     * 日志提供者
     */
    private static final LoggerContext DELEGATE;

    static {
        if (isSupportLogger(SLF4J_LOGGER_CLASS_NAME)) {
            DELEGATE = new Slf4JLoggerContext();
        } else {
            DELEGATE = new ConsoleLoggerContext();
        }
    }

    /**
     * 是否支持指定日志类型
     */
    private static boolean isSupportLogger(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Logger getLogger(Class<?> clazz) {
        return DELEGATE.getLogger(clazz);
    }

    @Override
    public Logger getLogger(String name) {
        return DELEGATE.getLogger(name);
    }

    @Override
    public MDC getContext() {
        return DELEGATE.getContext();
    }

    @Override
    public void addListener(LoggerContextListener listener) {
        DELEGATE.addListener(listener);
    }
}
