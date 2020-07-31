package com.unisinsight.demo.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestServerTwo {
    @Resource
    private TestServerOne testServerOne;

    public void test(){
        testServerOne.doing();
    }

    public void doing(){

    }
}
