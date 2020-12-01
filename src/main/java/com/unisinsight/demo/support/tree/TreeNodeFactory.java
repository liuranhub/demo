package com.unisinsight.demo.support.tree;

public interface TreeNodeFactory {
    TreeNode create(TreeNode parent, String content);
    TreeNode create(String context);
}
