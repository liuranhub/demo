package com.unisinsight.demo.service;

import com.unisinsight.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PersonRandomUpdateDataService extends AbstractRandomDataService<Person>{
    @Autowired
    private ModelCreateSave<Person> modelCreateSave;

    public Person create(int index) {
        return modelCreateSave.create(index);
    }

    public void doing(Person o) {
        modelCreateSave.save(o);
    }
}
