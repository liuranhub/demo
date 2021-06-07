package com.unisinsight.demo.support.question.aili;

import java.util.*;

public class SolutionOne {
    public static Integer[] method1(Integer[] arrFirst, Integer[] arrSecond) {
        if (arrFirst == null || arrFirst.length == 0 || arrSecond == null || arrSecond.length == 0){
            return null;
        }
        Set<Integer> set = new HashSet<>(Arrays.asList(arrFirst));

        List<Integer> result = new LinkedList<>();
        for (Integer val : arrSecond) {
            if (set.contains(val)) {
                result.add(val);
            }
        }

        return result.toArray(new Integer[]{});
    }

    public static Integer[] method2(Integer[] firstArr, Integer[] secondArr) {
        if (firstArr == null || firstArr.length == 0 || secondArr == null || secondArr.length == 0){
            return new Integer[]{};
        }

        int firstIndex = 0;
        int secondIndex = 0;
        List<Integer> result = new LinkedList<>();
        while (firstIndex < firstArr.length && secondIndex < secondArr.length) {
            if (firstArr[firstIndex].equals(secondArr[secondIndex])) {
                result.add(firstArr[firstIndex]);
                firstIndex ++;
                secondIndex ++;
            } else if(firstArr[firstIndex] < secondArr[secondIndex]) {
                // 移动第一个数组
                firstIndex ++;
            } else if (firstArr[firstIndex] > secondArr[secondIndex]){
                secondIndex ++;
            }
        }

        return result.toArray(new Integer[]{});
    }

    public static void main(String[] args) {
        Integer[] arr1 = new Integer[]{1,2,3,4,5,6};
        Integer[] arr2 = new Integer[]{2,3,4, 7};
        System.out.println(Arrays.toString(method1(arr1, arr2)));
    }
}
