package com.unisinsight.demo.service;

import com.unisinsight.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


/**
 * 快速找到数据库中丢失编号
 * */
@Component
public class PersonSequenceInitDataService extends AbstractSequenceDataService<Person> {
    @Autowired
    private ModelCreateSave<Person> modelCreateSave;

    public Person create(int index) {
        return modelCreateSave.create(index);
    }

    public void doing(Person o) {
        modelCreateSave.save(o);
    }
}
