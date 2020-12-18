package com.unisinsight.demo.service;

public interface ModelCreateSave<T> {
    T create(int index);
    T save(T t);
}
