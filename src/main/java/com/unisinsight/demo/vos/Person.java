package com.unisinsight.demo.vos;

import com.unisinsight.demo.service.PersonService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

public class Person {
    private String id;
    private String name;
    private Date birthday;
    private String identify;
    private String type;


    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", identify='" + identify + '\'' +
                ", type='" + type + '\'' +
                '}' + '\n';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
