package com.unisinsight.demo.question;

import java.util.LinkedList;
import java.util.List;

public class TreeNode {
    private TreeNode l;
    private TreeNode r;
    private int value;

    public List<String> getPath(TreeNode node, int val, String ppath){
        if (val == 0) {
            return new LinkedList<>();
        }

        List<String> result = new LinkedList<>();

        if (node.l != null) {
            List<String> subPath = getPath(node.l, val - node.value, ppath);
            result.addAll(subPath);
        }

        if (node.r != null) {
            List<String> subPath = getPath(node.r, val - node.value, ppath);
            result.addAll(subPath);
        }

        return result;
    }

}
