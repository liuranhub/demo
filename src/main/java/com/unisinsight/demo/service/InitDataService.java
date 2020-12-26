package com.unisinsight.demo.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 快速找到数据库中丢失编号
 * */
@Service
public class InitDataService<T> extends AbstractDataService<T> {

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
