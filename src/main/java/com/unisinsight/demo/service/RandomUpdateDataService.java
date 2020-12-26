package com.unisinsight.demo.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RandomUpdateDataService<T> extends AbstractRandomDataService<T>{
    @Resource
    private ModelCreateSave<T> modelCreateSave;

    @Override
    public T create(int index) {
        return modelCreateSave.create(index);
    }

    @Override
    public void doing(T o) {
        modelCreateSave.save(o);
    }
}
