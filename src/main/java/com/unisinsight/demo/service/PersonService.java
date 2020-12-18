package com.unisinsight.demo.service;

import com.unisinsight.demo.model.Person;

public interface PersonService {
    Person get(Integer id);

    Person save(Person person);
}
