package com.unisinsight.demo.support.tree;

public class Line {
    private Integer level;
    private String content;

    public Line(String content, Integer level) {
        this.content = content;
        this.level = level;
    }

    public Integer getLevel() {
        return level;
    }

    public String getContent() {
        return content;
    }
}
