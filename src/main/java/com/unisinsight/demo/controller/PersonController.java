package com.unisinsight.demo.controller;

import com.unisinsight.demo.service.PersonService;
import com.unisinsight.demo.vos.Person;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PersonController {
    @Resource
    private PersonService personService;

    public void save(Person person) {
        personService.save(person);
    }

}
