package com.unisinsight.demo.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestServerOne {
    @Resource
    private TestServerTwo testServerTwo;

    public void test(){
        testServerTwo.doing();
    }

    public void doing(){

    }
}
