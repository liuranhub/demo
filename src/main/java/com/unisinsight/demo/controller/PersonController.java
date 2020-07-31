package com.unisinsight.demo.controller;

import com.unisinsight.demo.service.DynamicService;
import com.unisinsight.demo.service.PersonService;
import com.unisinsight.demo.support.event.PersonEvent;
import com.unisinsight.demo.vos.Person;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("person")
public class PersonController {
    @Resource
    private PersonService personService;
    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private DefaultListableBeanFactory beanFactory;

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

}
