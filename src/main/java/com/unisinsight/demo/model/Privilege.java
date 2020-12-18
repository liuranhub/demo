package com.unisinsight.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_privilege")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Privilege {
    @Id
    private Integer id;

    private String usercode;
    private String orgpath;
    private String role;
    private String hashcode;
    private String ext;

    public String toSql(){
        return  "INSERT INTO public.tb_privilege " +
                "(usercode, orgpath, \"role\", hashcode, id, ext)\n" +
                "VALUES('"+ usercode +"', '"+ orgpath +"', '"+ role +"', '"+ hashcode +"', '" + id  +"','"+ ext + "');";
    }


    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getOrgpath() {
        return orgpath;
    }

    public void setOrgpath(String orgpath) {
        this.orgpath = orgpath;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }
}
