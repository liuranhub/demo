package com.unisinsight.demo.controller;

import com.unisinsight.demo.service.DynamicService;
import com.unisinsight.demo.support.question.SearchEmptyNoService;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("person")
public class PersonController {
    @Resource
    private DefaultListableBeanFactory beanFactory;

    @Resource
    private SearchEmptyNoService searchService;

    @GetMapping("create")
    public void createDynamicService(){
        beanFactory.registerSingleton(DynamicService.class.getName(),
                beanFactory.createBean(DynamicService.class, 2, true) );
    }

    @GetMapping("publish")
    public void test() {
        DynamicService dynamicService = beanFactory
                .getBean(DynamicService.class.getName(), DynamicService.class);
        dynamicService.doing();
    }


    @GetMapping("search/{slices}")
    public void search(@PathVariable Integer slices){
        long start = System.currentTimeMillis();
        int base = 1000000;
        List<String> orders = searchService.search(base, base * 2, slices);
        for (String val : orders) {
            System.out.println(val);
        }

        System.out.println(System.currentTimeMillis() - start);
    }


    @PostMapping("init")
    public void initData(){
        searchService.initData(0, 2000000);
    }
}
