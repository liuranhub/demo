package com.unisinsight.demo.support.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSort implements Sort{
    static final Logger LOG = LoggerFactory
            .getLogger(AbstractSort.class);

    protected boolean invert = false;

    public AbstractSort(){

    }

    public AbstractSort(boolean invert){
        this.invert = invert;
    }

    protected static void swap(Comparable[] array, int fist, int second){
        Comparable temp = array[fist];
        array[fist] = array[second];
        array[second] = temp;
    }

    @SuppressWarnings("unchecked")
    protected static boolean fistGtSecond(Comparable fist, Comparable second){
        if (fist.compareTo(second) > 0){
            return true;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    protected static boolean fistGteSecond(Comparable fist, Comparable second){
        if (fist.compareTo(second) >= 0){
            return true;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    protected static boolean fistLteSecond(Comparable fist, Comparable second){
        if (fist.compareTo(second) <= 0){
            return true;
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    public <T extends Comparable<? super T>> List<T> sort(List<T> list) {
        if (list == null || list.size() <= 1){
            return list;
        }

        Comparable[] array = toArray(list);
        long start = System.currentTimeMillis();

        Comparable[] result = sort(array);

        String className = this.getClass().getName();
        LOG.info(className.substring(className.lastIndexOf(".") + 1) + "耗时:" +
                (System.currentTimeMillis() - start));

        List<T> resultList = new ArrayList<>(result.length);
        if (invert){
            for (int i = result.length - 1 ; i >= 0; i --){
                resultList.add((T) result[i]);
            }
        } else {
            for (Comparable comparable : result){
                resultList.add((T) comparable);
            }
        }

        return resultList;
    }

    @Override
    public <T extends Comparable<? super T>> long sortTime(List<T> list) {
        if (list == null || list.size() <= 1){
            return 0;
        }

        Comparable[] array = toArray(list);
        long start = System.currentTimeMillis();

        sort(array);

        return System.currentTimeMillis() - start;
    }

    public abstract Comparable[] sort(Comparable[] array);

    private static <T extends Comparable> Comparable[] toArray(List<T> list){
        Comparable[] array = new Comparable[list.size()];
        for (int i = 0; i < list.size() ; i ++){
            array[i] = list.get(i);
        }

        return array;
    }
}
