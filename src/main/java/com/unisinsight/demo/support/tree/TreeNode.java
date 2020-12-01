package com.unisinsight.demo.support.tree;

import java.util.ArrayList;
import java.util.List;

public abstract class TreeNode {
    private TreeNode parent;
    private String content;
    private List<TreeNode> children;

    public TreeNode(TreeNode parent, String content){
        this.parent = parent;
        this.content = content;
    }

    protected TreeNode getParent() {
        return parent;
    }

    protected String getContent() {
        return content;
    }

    public TreeNode addChild(TreeNode child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
        return this;
    }

    public List<TreeNode> getChildren() {
        return children;
    }
}
