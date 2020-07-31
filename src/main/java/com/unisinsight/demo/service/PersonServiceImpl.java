package com.unisinsight.demo.service;

import com.unisinsight.demo.vos.Person;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class PersonServiceImpl extends AbstractPersonService {

    @PostConstruct
    public void test(){
        System.out.println("yyy");
    }

    @Override
    Person saveBefore(Person person) {
        // todo
        return person;
    }

    @Override
    Person saveAfter(Person person) {
        // todo
        return person;
    }

    @Override
    Person updateBefore(Person person) {
        // todo
        return person;
    }

    @Override
    Person updateAfter(Person person) {
        // todo
        return person;
    }

    @Override
    public void doing() {
        System.out.println("dynamic inject");
    }
}
