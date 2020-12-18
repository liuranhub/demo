package com.unisinsight.demo.model;

import com.unisinsight.demo.service.PersonService;

import javax.annotation.Resource;

public class TestAutowireBean {

    @Resource
    private PersonService personService;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
