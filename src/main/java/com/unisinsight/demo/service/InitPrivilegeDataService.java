package com.unisinsight.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unisinsight.demo.model.Privilege;
import com.unisinsight.demo.repository.PrivilegeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

//@Service
public class InitPrivilegeDataService implements ModelCreateSave<Privilege>{

    ObjectMapper mapper = new ObjectMapper();

    @Resource
    private PrivilegeRepository repository;


    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Privilege save(Privilege privilege) {
        if (privilege.getId() != null && repository.existsById(privilege.getId())) {
            return null;
        }

        jdbcTemplate.execute(privilege.toSql());
        return null;
    }

    public Privilege create(int i){
        Privilege privilege = new Privilege();
        privilege.setId(i);

        PrivilegeVo vo = new PrivilegeVo();
        vo.role = random();
        vo.usercode = random();
        vo.orgpath = random();

        privilege.setOrgpath(toString(vo.orgpath));
        privilege.setRole(toString(vo.usercode));
        privilege.setUsercode(toString(vo.orgpath));
        privilege.setHashcode(UUID.randomUUID().toString());
        try {
            privilege.setExt(mapper.writeValueAsString(vo));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return privilege;
    }

    public class PrivilegeVo{
        private List<String> usercode;
        private List<String> orgpath;
        private List<String> role;
        private List<String> hashcode;

        public List<String> getUsercode() {
            return usercode;
        }

        public void setUsercode(List<String> usercode) {
            this.usercode = usercode;
        }

        public List<String> getOrgpath() {
            return orgpath;
        }

        public void setOrgpath(List<String> orgpath) {
            this.orgpath = orgpath;
        }

        public List<String> getRole() {
            return role;
        }

        public void setRole(List<String> role) {
            this.role = role;
        }

        public List<String> getHashcode() {
            return hashcode;
        }

        public void setHashcode(List<String> hashcode) {
            this.hashcode = hashcode;
        }
    }

    private static String toString(List<String> list){
        StringBuilder builder = new StringBuilder("{\"");
        builder.append(list.get(0));
        builder.append("\", \"");
        builder.append(list.get(1));
        builder.append("\"}");
        return builder.toString();
    }

    private static List<String> defaultList = new ArrayList<>();
    static {
        for (int i = 0; i < 100; i ++) {
            defaultList.add(UUID.randomUUID().toString());
        }
    }

    private static List<String> random(){
        List<String> result = new ArrayList<>();
        result.add(defaultList.get(new Random().nextInt(defaultList.size())));
        result.add(UUID.randomUUID().toString());
        return result;
    }
}
