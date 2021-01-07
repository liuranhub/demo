package com.unisinsight.demo.service;

import com.unisinsight.demo.model.Person;
import com.unisinsight.demo.model.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeSequenceInitDataService extends AbstractRandomDataService<Privilege> {
    @Autowired
    private ModelCreateSave<Privilege> modelCreateSave;

    public Privilege create(int index) {
        return modelCreateSave.create(index);
    }

    public void doing(Privilege o) {
        modelCreateSave.save(o);
    }
}
