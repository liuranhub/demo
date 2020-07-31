package com.unisinsight.demo.service;

import com.unisinsight.demo.vos.Person;

public interface PersonService {
    public Person save(Person person);
    public Person update(Person person);
    public void doing();
}
