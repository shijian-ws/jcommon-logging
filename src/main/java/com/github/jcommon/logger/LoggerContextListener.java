package com.github.jcommon.logger;

import java.util.EventListener;
import java.util.List;

/**
 * @author shijian
 * @email shijianws@163.com
 * @date 2021-02-27
 */
public interface LoggerContextListener extends EventListener {
    List<LoggerListener> getLoggerListener();
}
