package com.unisinsight.demo.support;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private String name;
    private List<TreeNode> nodes = new ArrayList<>();
    private TreeNode parent;
    private String category;

    public String getCategory() {
        return category;
    }

    public List<TreeNode> getNodes() {
        return nodes;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        String id = ChineseToFirstCharUtil.getFirstSpell(name).toUpperCase();

        if (parent != null) {
            id = parent.getId() + "_" + id;
        }

        return id;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public String getParent() {
        if (parent != null) {
            return parent.getId();
        }
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addNode(TreeNode node) {
        nodes.add(node);
    }
}
