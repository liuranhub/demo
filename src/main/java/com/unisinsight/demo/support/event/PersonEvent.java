package com.unisinsight.demo.support.event;

import com.unisinsight.demo.vos.Person;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;

public class PersonEvent extends ApplicationEvent {

    private Person person;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public PersonEvent(Object source) {
        super(source);
    }

    public Person getPerson() {
        return person;
    }
}
