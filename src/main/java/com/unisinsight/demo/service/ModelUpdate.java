package com.unisinsight.demo.service;

public interface ModelUpdate<T> {
    T create(int index);
    T update(T t);
}
