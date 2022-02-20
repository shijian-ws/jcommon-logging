package com.github.jcommon.logger.internals;

import com.github.jcommon.logger.Logger;
import com.github.jcommon.logger.LoggerContext;
import com.github.jcommon.logger.MDC;
import com.github.jcommon.logger.internals.mdc.Slf4JMDCAdapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * slf4j日志提供者
 *
 * @author shijian
 * @email shijianws@163.com
 * @date 2019-11-09
 */
public class Slf4JLoggerContext implements LoggerContext {
    private static final Map<String, Slf4jLogger> LOGGERS = new ConcurrentHashMap<>(32);
    private static final MDC CONTEXT = new Slf4JMDCAdapter();

    @Override
    public Logger getLogger(Class<?> clazz) {
        return LOGGERS.computeIfAbsent(clazz.getName(), key -> new Slf4jLogger(org.slf4j.LoggerFactory.getLogger(clazz)));
    }

    @Override
    public Logger getLogger(String name) {
        return LOGGERS.computeIfAbsent(name, key -> new Slf4jLogger(org.slf4j.LoggerFactory.getLogger(name)));
    }

    @Override
    public MDC getContext() {
        return CONTEXT;
    }

    private static class Slf4jLogger implements Logger {
        private final org.slf4j.Logger logger;

        private Slf4jLogger(org.slf4j.Logger logger) {
            this.logger = logger;
        }

        @Override
        public void debug(String format, Object... arguments) {
            logger.debug(format, arguments);
        }

        @Override
        public void debug(String msg, Throwable t) {
            logger.debug(msg, t);
        }

        @Override
        public void debug(Throwable t) {
            logger.debug(t == null ? null : t.getMessage(), t);
        }

        @Override
        public void info(String format, Object... arguments) {
            logger.info(format, arguments);
        }

        @Override
        public void info(String msg, Throwable t) {
            logger.info(msg, t);
        }

        @Override
        public void info(Throwable t) {
            logger.info(t == null ? null : t.getMessage(), t);
        }

        @Override
        public void warn(String format, Object... arguments) {
            logger.warn(format, arguments);
        }

        @Override
        public void warn(String msg, Throwable t) {
            logger.warn(msg, t);
        }

        @Override
        public void warn(Throwable t) {
            logger.warn(t == null ? null : t.getMessage(), t);
        }

        @Override
        public void error(String format, Object... arguments) {
            logger.error(format, arguments);
        }

        @Override
        public void error(String msg, Throwable t) {
            logger.error(msg, t);
        }

        @Override
        public void error(Throwable t) {
            logger.error(t == null ? null : t.getMessage(), t);
        }

        @Override
        public boolean isDebugEnabled() {
            return logger.isDebugEnabled();
        }

        @Override
        public boolean isInfoEnabled() {
            return logger.isInfoEnabled();
        }

        @Override
        public boolean isWarnEnabled() {
            return logger.isWarnEnabled();
        }

        @Override
        public boolean isErrorEnabled() {
            return logger.isErrorEnabled();
        }
    }
}
