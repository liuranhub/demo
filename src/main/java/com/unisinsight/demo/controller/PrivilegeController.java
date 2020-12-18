package com.unisinsight.demo.controller;

import com.unisinsight.demo.model.Privilege;
import com.unisinsight.demo.service.InitDataService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("privilege")
public class PrivilegeController {

    @Resource
    private InitDataService<Privilege> initDataService;

    @PostMapping(value = "init/{start}/{end}")
    public void init(@PathVariable Integer start, @PathVariable Integer end){
        initDataService.initData(start, end);
    }
}
