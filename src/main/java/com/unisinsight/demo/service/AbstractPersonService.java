package com.unisinsight.demo.service;

import com.unisinsight.demo.vos.Person;

public abstract class AbstractPersonService implements PersonService{
    @Override
    public Person save(Person person) {
        saveBefore(person);

        // todo ,checkParam(Person), insert(person)

        saveAfter(person);
        return person;
    }

    @Override
    public Person update(Person person) {
        updateBefore(person);

        // todo

        updateAfter(person);
        return person;
    }

    abstract Person saveBefore(Person person);
    abstract Person saveAfter(Person person);
    abstract Person updateBefore(Person person);
    abstract Person updateAfter(Person person);
}
