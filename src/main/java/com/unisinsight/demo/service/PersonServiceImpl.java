package com.unisinsight.demo.service;

import com.unisinsight.demo.repository.PersonRepository;
import com.unisinsight.demo.vos.Person;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PersonServiceImpl implements PersonService {

    @Resource
    private PersonRepository personRepository;


    @Override
    public Person get(String id) {
        return personRepository.getOne(id);
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }
}
