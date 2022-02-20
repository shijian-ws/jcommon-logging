package com.github.jcommon.logger.internals.mdc;

import com.github.jcommon.logger.MDC;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于内存日志参数上下文实现
 *
 * @author shijian
 * @email shijianws@163.com
 * @date 2021-01-16
 */
public class MapMDCAdapter implements MDC {
    private static final ThreadLocal<Map<String, String>> THREAD_LOCAL = new ThreadLocal<>();

    private static Map<String, String> get() {
        Map<String, String> map = THREAD_LOCAL.get();
        if (map == null) {
            synchronized (THREAD_LOCAL) {
                if ((map = THREAD_LOCAL.get()) == null) {
                    THREAD_LOCAL.set(map = new ConcurrentHashMap<>(16));
                }
            }
        }
        return map;
    }

    @Override
    public void put(String key, String value) {
        get().put(key, value);
    }

    @Override
    public void putAll(Map<String, String> map) {
        if (map != null && !map.isEmpty()) {
            get().putAll(map);
        }
    }

    @Override
    public void remove(String key) {
        Map<String, String> map = THREAD_LOCAL.get();
        if (map == null || map.isEmpty()) {
            return;
        }
        map.remove(key);
    }

    @Override
    public String get(String key) {
        return get().get(key);
    }

    @Override
    public void clear() {
        Map<String, String> map = THREAD_LOCAL.get();
        if (map == null) {
            return;
        }
        THREAD_LOCAL.remove();
        if (!map.isEmpty()) {
            map.clear();
        }
    }

    @Override
    public void setContext(Map<String, String> contextMap) {
        Map<String, String> map = get();
        if (!map.isEmpty()) {
            map.clear();
        }
        map.putAll(contextMap);
    }

    @Override
    public Map<String, String> getCopyOfContext() {
        return new HashMap<>(get());
    }
}
