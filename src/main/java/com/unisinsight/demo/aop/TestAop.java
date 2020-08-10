package com.unisinsight.demo.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestAop {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void BrokerAspect(){

    }

    @Before("BrokerAspect()")
    public void doBeforeGame(){
        System.out.println("test aop");
    }

}
