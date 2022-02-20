package com.github.jcommon.logger;

import com.github.jcommon.logger.support.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 可组合的Logger
 *
 * @author shijian
 * @email shijianws@163.com
 * @date 2021-02-06
 */
public interface CompositeLogger extends Logger {
    /**
     * 添加一个Logger
     */
    boolean addDelegate(Logger logger);

    /**
     * 获取代理的日志对象
     */
    List<Logger> getDelegates();

    /**
     * 默认实现
     */
    class Impl implements CompositeLogger {
        private final List<Logger> delegates = new ArrayList<>(3);

        private Impl(List<Logger> delegates) {
            if (delegates != null) {
                this.delegates.addAll(delegates);
            }
        }

        @Override
        public boolean addDelegate(Logger logger) {
            return logger != null && delegates.add(logger);
        }

        @Override
        public List<Logger> getDelegates() {
            return Collections.unmodifiableList(delegates);
        }

        @Override
        public void debug(String format, Object... arguments) {
            delegates.stream().filter(Logger::isDebugEnabled).forEach(logger -> logger.debug(format, arguments));
        }

        @Override
        public void debug(String msg, Throwable ex) {
            delegates.stream().filter(Logger::isDebugEnabled).forEach(logger -> logger.debug(msg, ex));
        }

        @Override
        public void debug(Throwable ex) {
            delegates.stream().filter(Logger::isDebugEnabled).forEach(logger -> logger.debug(ex));
        }

        @Override
        public void info(String format, Object... arguments) {
            delegates.stream().filter(Logger::isInfoEnabled).forEach(logger -> logger.info(format, arguments));
        }

        @Override
        public void info(String msg, Throwable ex) {
            delegates.stream().filter(Logger::isInfoEnabled).forEach(logger -> logger.info(msg, ex));
        }

        @Override
        public void info(Throwable ex) {
            delegates.stream().filter(Logger::isInfoEnabled).forEach(logger -> logger.info(ex));
        }

        @Override
        public void warn(String format, Object... arguments) {
            delegates.stream().filter(Logger::isWarnEnabled).forEach(logger -> logger.warn(format, arguments));
        }

        @Override
        public void warn(String msg, Throwable ex) {
            delegates.stream().filter(Logger::isWarnEnabled).forEach(logger -> logger.warn(msg, ex));
        }

        @Override
        public void warn(Throwable ex) {
            delegates.stream().filter(Logger::isWarnEnabled).forEach(logger -> logger.warn(ex));
        }

        @Override
        public void error(String format, Object... arguments) {
            delegates.stream().filter(Logger::isErrorEnabled).forEach(logger -> logger.error(format, arguments));
        }

        @Override
        public void error(String msg, Throwable ex) {
            delegates.stream().filter(Logger::isErrorEnabled).forEach(logger -> logger.error(msg, ex));
        }

        @Override
        public void error(Throwable ex) {
            delegates.stream().filter(Logger::isErrorEnabled).forEach(logger -> logger.error(ex));
        }

        @Override
        public boolean isDebugEnabled() {
            return delegates.stream().anyMatch(Logger::isDebugEnabled);
        }

        @Override
        public boolean isInfoEnabled() {
            return delegates.stream().anyMatch(Logger::isInfoEnabled);
        }

        @Override
        public boolean isWarnEnabled() {
            return delegates.stream().anyMatch(Logger::isWarnEnabled);
        }

        @Override
        public boolean isErrorEnabled() {
            return delegates.stream().anyMatch(Logger::isErrorEnabled);
        }
    }

    static Logger of(Logger... loggers) {
        if (loggers == null || loggers.length == 0) {
            return LoggerFactory.EMPTY_LOGGER;
        }
        List<Logger> loggerList = Stream.of(loggers).filter(Objects::nonNull).collect(Collectors.toList());
        if (loggerList.isEmpty()) {
            return LoggerFactory.EMPTY_LOGGER;
        }
        return new Impl(loggerList);
    }
}
