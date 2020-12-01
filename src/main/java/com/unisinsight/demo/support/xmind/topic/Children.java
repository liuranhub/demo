package com.unisinsight.demo.support.xmind.topic;

import java.util.List;

public class Children {

    private List<Topic> attached;
    private List<Topic> detached;

    public List<Topic> getAttached() {
        return attached;
    }

    public void setAttached(List<Topic> attached) {
        this.attached = attached;
    }

    public List<Topic> getDetached() {
        return detached;
    }

    public void setDetached(List<Topic> detached) {
        this.detached = detached;
    }
}
