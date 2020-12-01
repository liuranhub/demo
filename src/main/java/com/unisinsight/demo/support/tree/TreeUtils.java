package com.unisinsight.demo.support.tree;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TreeUtils {

    /**
     * 带层级的文本树转内存树
     * */
    public static List<TreeNode> linesToTree(List<Line> lines, TreeNodeFactory factory){
        if (CollectionUtils.isEmpty(lines)){
            return null;
        }
        LinkedList<TreeNode> parentStack = new LinkedList<>();
        List<TreeNode> result = new ArrayList<>();
        TreeNode root ;
        for (Line line : lines) {
            while (parentStack.size() >= line.getLevel()){
                parentStack.pop();
            }
            TreeNode parent = parentStack.peek();
            TreeNode node ;
            if (parent == null) {
                root = factory.create(line.getContent());
                result.add(root);
                node = root;
            } else {
                node = factory.create(parent, line.getContent());
                parent.addChild(node);
            }
            parentStack.push(node);
        }
        return result;
    }
}
