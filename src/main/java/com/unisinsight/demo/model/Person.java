package com.unisinsight.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "tb_person")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Person extends Base {

    @Id
//    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator="tb_privilege_id_seq")
//    @SequenceGenerator(name="tb_privilege_id_seq",allocationSize = 1)
    private Integer id;
    private Long createTime;
    private String name;
    private Integer age;
    private String school;
    private String address;
    private String number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
