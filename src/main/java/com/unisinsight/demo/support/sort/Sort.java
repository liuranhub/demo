package com.unisinsight.demo.support.sort;

import java.util.List;

public interface Sort {
    public <T extends Comparable<? super T>> List<T> sort(List<T> list);
    public <T extends Comparable<? super T>> long sortTime(List<T> list);
}
