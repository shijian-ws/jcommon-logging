package com.github.jcommon.logger;

import java.util.EventListener;

/**
 * @author shijian
 * @email shijianws@163.com
 * @date 2021-02-27
 */
public interface LoggerListener extends EventListener {
    /**
     * 日志输出调用之前
     */
    void prepareWriteLog(Logger logger, LoggerLevel level, Object... args);

    /**
     * 日志输出调用之后
     */
    void postWriteLog(Logger logger, LoggerLevel level, Object... args);

    /**
     * 日志级别设置完成
     */
    void onLevelChanged(Logger logger, LoggerLevel level);
}
