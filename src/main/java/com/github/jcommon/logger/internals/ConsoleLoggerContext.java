package com.github.jcommon.logger.internals;

import com.github.jcommon.logger.Logger;
import com.github.jcommon.logger.LoggerContext;
import com.github.jcommon.logger.MDC;
import com.github.jcommon.logger.internals.mdc.MapMDCAdapter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 控制台输出日志
 *
 * @author shijian
 * @email shijianws@163.com
 * @date 2019-11-09
 */
public class ConsoleLoggerContext implements LoggerContext {
    private static final Map<String, Logger> LOGGERS = new ConcurrentHashMap<>(32);
    private static final MDC CONTEXT = new MapMDCAdapter();

    @Override
    public Logger getLogger(Class<?> clazz) {
        return this.getLogger(clazz.getName());
    }

    @Override
    public Logger getLogger(String name) {
        return LOGGERS.computeIfAbsent(name, key -> (Logger) Proxy.newProxyInstance(ConsoleLoggerContext.class.getClassLoader(), new Class[]{Logger.class}, (proxy, method, args) -> invoke(name, method, args)));
    }

    @Override
    public MDC getContext() {
        return CONTEXT;
    }

    private static Object invoke(String name, Method method, Object[] args) throws IOException {
        if (boolean.class == method.getReturnType()) {
            return true;
        }

        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length < 1) {
            return null;
        }

        String format;
        Object[] arguments = null;
        Throwable ex = null;
        Class<?> parameterType = parameterTypes[0];
        if (Throwable.class.isAssignableFrom(parameterType)) {
            ex = (Throwable) args[0];
            format = ex.getMessage();
        } else {
            format = (String) args[0];
            Object arg = args[1];
            if (arg instanceof Throwable) {
                ex = (Throwable) arg;
            } else if (arg instanceof Object[]) {
                arguments = (Object[]) arg;
                if (arguments.length > 0) {
                    Object lastArg = arguments[arguments.length - 1];
                    if (lastArg instanceof Throwable) {
                        ex = (Throwable) lastArg;
                        arguments = Arrays.copyOf(arguments, arguments.length - 1);
                    }
                }
            }
        }

        String methodName = method.getName();

        try (StringWriter writer = new StringWriter()) {
            // 当前时间
            writer.write(LocalDateTime.now().toString());
            // 日志级别
            writer.write(String.format(" [%s] ", methodName));
            writer.write(String.format("%s: ", name));
            // 日志
            writer.write(formartString(format, arguments));
            if (ex != null) {
                PrintWriter printWriter = new PrintWriter(writer);
                printWriter.println();
                ex.printStackTrace(printWriter);
            }

            if (Objects.equals("error", methodName)) {
                System.err.println(writer);
            } else {
                System.out.println(writer);
            }
        }
        return null;
    }

    /**
     * 使用占位符{}格式化
     */
    private static String formartString(String formart, Object... args) {
        if (formart == null || args == null || args.length == 0 || !formart.contains("{}")) {
            return formart;
        }

        StringBuilder buf = new StringBuilder();
        int offset = 0;
        for (Object arg : args) {
            // 检索
            int pos = formart.indexOf("{}", offset);
            if (pos < 0) {
                // 未找到
                break;
            }
            if (pos > 0) {
                // 占位符不在开始, 填充非占位符
                buf.append(formart.substring(offset, pos));
            }
            // 替换占位符
            buf.append(arg);
            // 偏移到当前占位符索引之后
            offset = pos + 2;
        }

        if (offset < formart.length()) {
            // 还有剩余
            buf.append(formart.substring(offset));
        }

        return buf.toString();
    }
}
