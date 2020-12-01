package com.unisinsight.demo.support;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileToJson {

    public static void main(String[] args) {
        TreeNode root = readFile("/Users/liuran/File/Temp/作战室.txt");
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(root);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(json);
    }

    public static TreeNode readFile(String fileName){
        File file = new File(fileName);
        List<Line> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String str;
            try {
                while ((str = reader.readLine()) != null) {
                    String name = str.trim();
                    String perfix = str.substring(0, str.indexOf(name));
                    int level = perfix.length() + 1;
                    Line line = new Line(name, level);
                    lines.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String category = "作战室";


        TreeNode root = new TreeNode();
        root.setCategory(category);
        root.setName(category);

        LinkedList<TreeNode> parentStack = new LinkedList<TreeNode>();
        parentStack.push(root);

        for (Line line : lines) {
            TreeNode node = new TreeNode();
            while (parentStack.size() > line.level){
                parentStack.pop();
            }
            TreeNode parent = parentStack.peek();
            parent.addNode(node);
            node.setParent(parent);
            node.setName(line.name);
            node.setCategory(category);
            parentStack.push(node);
        }

        return root;
    }

    private static class Line{
        private String name;
        private int level;



        public Line(String name, int level){
            this.name = name;
            this.level = level;
        }
    }
}
