package com.unisinsight.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Application {
    public static void main(String[] args) {
        System.setProperty("proxyHost", "10.208.10.253");
        System.setProperty("proxyPort",  "8080");
        System.setProperty("proxyUser", "liuran");
        System.setProperty("proxyPassword", "UNIs@172");

        SpringApplication.run(Application.class);
    }

    public static int ip2Int(String ip) {
        int intIp = 0;
        if (ip == null || ip.trim().length() == 0) {
            return intIp;
        }
        String[] split = ip.trim().split("\\.");
        for (int i = 0; i < split.length; i++) {
            int i1 = Integer.parseInt(split[i]);
            intIp = intIp | (i1 << (i * 8));
        }
        return intIp;
    }

    public static long ipToInt(long[] ip){
        return (ip[0] << 24) | (ip[1] << 16) | (ip[2] << 8) | ip[3];
    }

    public static long[] intToIp(long ip) {
        long [] result = new long[4];
        result[0] = ((ip >> 24) & 0Xff);
        result[1] = ((ip >> 16) & 0Xff);
        result[2] = ((ip >> 8) & 0Xff);
        result[3] = (ip & 0Xff);
        return result;
    }


    public static <T> Supplier<T> memoize(Supplier<T> delegate) {
        AtomicReference<T> value = new AtomicReference<>();
        return () -> {
            T val = value.get();
            if (val == null) {
                synchronized(value) {
                    val = value.get();
                    if (val == null) {
                        val = Objects.requireNonNull(delegate.get());
                        value.set(val);
                    }
                }
            }
            return val;
        };
    }

    public static Map<Boolean, List<InvokeResult>>  invokeAllGetMethod(final Object obj){
        Stream<Field> fieldStream = Stream.<Field>builder().build();
        Class c = obj.getClass();
        while (!c.equals(Object.class)) {
            fieldStream = Stream.concat(fieldStream, Stream.of(c.getDeclaredFields()));
            c = c.getSuperclass();
        }
        Map<Boolean, List<InvokeResult>> invokeResultMap = fieldStream
                .map(Application::getGetMethodNameByField)
                .map((String methodName) -> {
                    InvokeResult result = new InvokeResult();
                    result.methodName = methodName;
                    try {
                        result.value = obj.getClass().getMethod(methodName).invoke(obj);
                    } catch (NoSuchMethodException | IllegalAccessException
                            | InvocationTargetException e) {
                        result.value = null;
                    }
                    return result;
                })
                .collect(Collectors.groupingBy(invokeResult -> invokeResult.value != null));
        return invokeResultMap;
    }
    private static String getGetMethodNameByField(Field field) {
        String firstChar = field.getName().substring(0, 1).toUpperCase();
        return "get" + firstChar + field.getName().substring(1);
    }
    private static class InvokeResult{
        private String methodName;
        private Object value;
    }

    public static BigInteger feb(int n) {
        BigInteger[] febs = new BigInteger[n + 1];
        febs[0] = new BigInteger("0");
        febs[1] = new BigInteger("1");
        febs[2] = new BigInteger("1");

        for (int i = 3; i <= n; i++) {
            febs[i] = febs[i - 1].add(febs[i - 2]);
        }
        return febs[n];
    }


    private static Map<Integer, BigInteger> valueCache = new HashMap<>();

    static {
        valueCache.put(1, new BigInteger("1"));
        valueCache.put(2, new BigInteger("1"));
    }

    public static BigInteger fibo(Integer num) {
        if (valueCache.containsKey(num)) {
            return valueCache.get(num);
        }
        BigInteger result = fibo(num - 1).add(fibo(num - 2));
        valueCache.put(num, result);
        return result;
    }

}
