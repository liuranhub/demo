package com.unisinsight.demo.service;

import com.unisinsight.demo.vos.Person;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl extends AbstractPersonService {
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
}
