package com.unisinsight.demo.service;

import com.unisinsight.demo.vos.Person;

public interface PersonService {
    Person get(Integer id);

    Person save(Person person);
}
