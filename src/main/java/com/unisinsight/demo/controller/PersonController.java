package com.unisinsight.demo.controller;

import com.unisinsight.demo.service.PersonService;
import com.unisinsight.demo.support.question.SearchEmptyNoService;
import com.unisinsight.demo.vos.Person;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("person")
public class PersonController {

    @Resource
    private SearchEmptyNoService searchService;

    @Resource
    private PersonService personService;

    @GetMapping("{id}")
    public Person get(@PathVariable String id){
       return personService.get(id);
    }


    @PostMapping
    public Person save(){
        Person person = new Person();
        person.setId(UUID.randomUUID().toString());
        person.setName(UUID.randomUUID().toString());
        person.setAddress(UUID.randomUUID().toString());
        person.setAge(UUID.randomUUID().toString());
        person.setPassword(UUID.randomUUID().toString());
        person.setSchool(UUID.randomUUID().toString());

        return personService.save(person);
    }

    @GetMapping("search/{slices}")
    public void search(@PathVariable Integer slices){
        long start = System.currentTimeMillis();
        List<String> orders = searchService.search(0, 5000000, slices);
        for (String val : orders) {
            System.out.println(val);
        }

        System.out.println(System.currentTimeMillis() - start);
    }


    @PostMapping("init")
    public void initData(){
        searchService.initData(2000001, 5000000);
    }
}
