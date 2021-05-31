package com.unisinsight.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

@RestController
@RequestMapping(value = "test")
public class TestController {
    private static Logger LOG = LoggerFactory.getLogger(TestController.class);

    @GetMapping(value = "sqrt")
    public Object test(){
        double d = 0 ;
        for (int i = 0;i < 1000000 ;i ++) {
            d += Math.sqrt(1);
        }
        LOG.info("success");

        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);

        return "success";

    }

    @GetMapping(value = "hello")
    public String get(){
        return "hello world";
    }

}
