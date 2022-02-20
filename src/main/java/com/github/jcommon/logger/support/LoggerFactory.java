package com.github.jcommon.logger.support;

import com.github.jcommon.logger.CompositeLogger;
import com.github.jcommon.logger.Logger;
import com.github.jcommon.logger.LoggerContext;
import com.github.jcommon.logger.MDC;

import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 日志工厂
 *
 * @author shijian
 * @email shijianws@163.com
 * @date 2019-11-09
 */
public class LoggerFactory {
    /**
     * 获取日志输出对象
     */
    public static Logger getLogger(Class<?> clazz) {
        LoggerContext provider = getContext();
        return provider == null ? EMPTY_LOGGER : provider.getLogger(clazz);
    }

    /**
     * 获取日志输出对象
     */
    public static Logger getLogger(String name) {
        LoggerContext provider = getContext();
        return provider == null ? EMPTY_LOGGER : provider.getLogger(name);
    }

    private static class Inner {
        private static final LoggerContext CONTEXT;

        static {
            LoggerContext context = null;
            // 使用Java SPI
            ServiceLoader<LoggerContext> loader = ServiceLoader.load(LoggerContext.class, LoggerContext.class.getClassLoader());
            for (Iterator<LoggerContext> iter = loader.iterator(); iter.hasNext(); ) {
                if ((context = iter.next()) != null) {
                    break;
                }
            }
            CONTEXT = context;
        }
    }

    private static LoggerContext getContext() {
        return Inner.CONTEXT;
    }

    /**
     * 获取MDC
     */
    public static MDC getMDC() {
        LoggerContext context = getContext();
        return context == null ? null : context.getContext();
    }

    /**
     * 空实现
     */
    public static final Logger EMPTY_LOGGER = (Logger) Proxy.newProxyInstance(
            LoggerFactory.class.getClassLoader(),
            new Class[]{Logger.class},
            (proxy, method, args) -> boolean.class == method.getReturnType() ? false : null
    );

    /**
     * 多个日志输出对象合并为一个日志对象
     */
    public static Logger concat(Logger... loggers) {
        if (loggers == null || loggers.length == 0) {
            return EMPTY_LOGGER;
        }
        List<Logger> loggerList = Stream.of(loggers).filter(Objects::nonNull).collect(Collectors.toList());
        if (loggerList.isEmpty()) {
            return EMPTY_LOGGER;
        }
        return CompositeLogger.of(loggers);
    }
}
