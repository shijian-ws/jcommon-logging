package com.github.jcommon.logger;

import com.github.jcommon.logger.support.LoggerFactory;

import java.util.Map;

/**
 * 日志参数上下文
 *
 * @author shijian
 * @email shijianws@163.com
 * @date 2020-06-06
 */
public interface MDC {
    /**
     * 设置K/V
     */
    void put(String key, String value);

    /**
     * 设置一组K/V
     */
    void putAll(Map<String, String> map);

    /**
     * 移除K/V
     */
    void remove(String key);

    /**
     * 获取key对应的value
     */
    String get(String key);

    /**
     * 清空上下文
     */
    void clear();

    /**
     * 设置上下文
     */
    void setContext(Map<String, String> contextMap);

    /**
     * 拷贝上下文
     */
    Map<String, String> getCopyOfContext();

    static MDC get() {
        return LoggerFactory.getMDC();
    }
}
