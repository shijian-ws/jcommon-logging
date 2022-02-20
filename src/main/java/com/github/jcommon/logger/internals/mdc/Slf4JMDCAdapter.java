package com.github.jcommon.logger.internals.mdc;


import com.github.jcommon.logger.MDC;

import java.util.Map;

/**
 * Slf4j日志参数上下文实现
 *
 * @author shijian
 * @email shijianws@163.com
 * @date 2020-06-06
 */
public class Slf4JMDCAdapter implements MDC {
    @Override
    public void put(String key, String value) {
        org.slf4j.MDC.put(key, value);
    }

    @Override
    public void putAll(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        map.forEach(org.slf4j.MDC::put);
    }

    @Override
    public void remove(String key) {
        org.slf4j.MDC.remove(key);
    }

    @Override
    public String get(String key) {
        return org.slf4j.MDC.get(key);
    }

    @Override
    public void clear() {
        org.slf4j.MDC.clear();
    }

    @Override
    public void setContext(Map<String, String> contextMap) {
        org.slf4j.MDC.setContextMap(contextMap);
    }

    @Override
    public Map<String, String> getCopyOfContext() {
        return org.slf4j.MDC.getCopyOfContextMap();
    }
}
