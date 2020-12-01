package com.unisinsight.demo.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unisinsight.demo.support.tree.Line;
import com.unisinsight.demo.support.tree.TreeNode;
import com.unisinsight.demo.support.tree.TreeNodeFactory;
import com.unisinsight.demo.support.tree.TreeUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileToJson {

    public static void main(String[] args) {
        TreeNode root = new FileToJson()
                .readFile("/Users/liuran/File/Temp/作战室.txt", "作战室");
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(root);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(json);
    }

    public TreeNode readFile(String fileName, String category){
        File file = new File(fileName);
        List<Line> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String str;
            try {
                while ((str = reader.readLine()) != null) {
                    String name = str.trim();
                    String prefix = str.substring(0, str.indexOf(name));
                    int level = prefix.length() / 4 + 1;
                    Line line = new Line(name, level);
                    lines.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<TreeNode> result = TreeUtils.linesToTree(lines, new DefaultFactory(category));

        return result.get(0);
    }

    private class DefaultFactory implements TreeNodeFactory{
        private String category;
        public DefaultFactory(String category) {
            this.category = category;
        }

        @Override
        public TreeNode create(TreeNode parent, String content) {

            DefaultTreeNode treeNode = new DefaultTreeNode(parent, content);
            treeNode.setCategory(category);
            return treeNode;
        }

        @Override
        public TreeNode create(String context) {
            DefaultTreeNode treeNode =  new DefaultTreeNode(null, context);
            treeNode.setCategory(category);
            return treeNode;
        }
    }


    private class DefaultTreeNode extends TreeNode {
        private String name;
        private List<TreeNode> nodes = new ArrayList<>();
        private String category;

        public DefaultTreeNode(TreeNode parent, String content) {
            super(parent, content);
            this.name = content;
        }

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

            if (getParentNode() != null && getParentNode() instanceof DefaultTreeNode ) {
                id = ((DefaultTreeNode) getParentNode()).getId() + "_" + id;
            }

            return id;
        }

        public String getParent(){
            if (getParentNode() != null && getParentNode() instanceof DefaultTreeNode) {
                return ((DefaultTreeNode) getParentNode()).getId();
            }
            return null;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
