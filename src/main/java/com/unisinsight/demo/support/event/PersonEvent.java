package com.unisinsight.demo.support.event;

import com.unisinsight.demo.model.Person;
import org.springframework.context.ApplicationEvent;

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
