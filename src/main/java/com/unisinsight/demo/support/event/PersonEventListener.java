package com.unisinsight.demo.support.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PersonEventListener {

    @EventListener
    public void personEvent(PersonEvent personEvent){
        System.out.println(personEvent);
    }
}
