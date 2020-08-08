package com.unisinsight.demo.vos;

import java.util.UUID;

public class Base {
    private String id = "11";
    private String identify = UUID.randomUUID().toString();

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
