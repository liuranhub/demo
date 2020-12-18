package com.unisinsight.demo.service;

import com.unisinsight.demo.repository.PersonRepository;
import com.unisinsight.demo.model.Person;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;
import java.util.UUID;

//@Service
public class PersonServiceImpl implements PersonService , ModelCreateSave<Person>{

    @Resource
    private PersonRepository personRepository;


    @Override
    public Person get(Integer id) {
        return personRepository.getOne(id);
    }

    @Override
    public Person create(int index) {
        return null;
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    private static Person createPerson(int i){
        Person person = new Person();
        person.setId(i);
        person.setName(UUID.randomUUID().toString());
        person.setAddress(UUID.randomUUID().toString());
        person.setAge(new Random().nextInt(35));
        person.setSchool(new Random().nextInt(100000) + "");
        person.setNumber(formatNo(i));
        person.setCreateTime(System.currentTimeMillis());

        return person;
    }

    private static String formatNo(Integer no){
        String format = "Order2020-00000000";
        String noStr = no.toString();

        return format.substring(0, format.length() - noStr.length()) + noStr;
    }
}
