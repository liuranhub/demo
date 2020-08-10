package com.unisinsight.demo.service;

import javax.annotation.Resource;

public class DynamicService {
    @Resource
    private PersonService personService;

    public void doing(){
        personService.doing();
    }
}
