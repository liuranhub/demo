package com.unisinsight.demo.fun;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

public class PartialFunctions {
    // y = ( 5*x + 7 * y ) / (m + m)

    public static void main(String[] args) {
        System.out.println(mapSize(2048));
    }

    public static int mapSize(int num) {
        int n = num - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n + 1;
    }

    public static int log2n(int num){
        if (num < 0 ) {
            return 0;
        }
        int value = 0;
        while ((num = num >> 1) != 0){
            value ++;
        }

        return value;
    }

    private static Map<Integer, BigInteger> valueCache = new ConcurrentHashMap<>();
    static {
        valueCache.put(0, new BigInteger("0"));
        valueCache.put(1, new BigInteger("1"));
    }
    public static BigInteger funCache(Integer num) {
        if (valueCache.containsKey(num)) {
            return valueCache.get(num);
        }

        BigInteger result =  funCache(num - 1).add(funCache(  num- 2));
        valueCache.put(num, result);
        return result;
    }

    public static BigInteger fun(int num) {
        if (num == 0) {
            return valueCache.get(0);
        } if (num == 1) {
            return valueCache.get(1);
        }

        return fun(num - 1).add(fun(  num- 2));
    }
}