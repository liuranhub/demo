package com.unisinsight.demo.support.trie;

import java.util.ArrayList;
import java.util.List;

public class TrieNode {
    private final char value;
    private List<TrieNode> children;
    private final TrieNode parent;
    private boolean isEnd;

    public TrieNode(char value, boolean isEnd, TrieNode parent){
        this.value = value;
        this.isEnd = isEnd;
        this.parent = parent;
    }


    public void addWord(String word){

    }

    public void addChild(char value, boolean isEnd) {
        if (children == null) {
            children = new ArrayList<>();
        }

        children.add(new TrieNode(value, isEnd, this));
    }

    public String getFullWord(){
        String word = "" + value;
        if (parent != null){
            word = parent.getFullWord() + word;
        }
        return word;
    }

    public TrieNode match(String target) {
        if (target == null || target.length() < 1) {
            return null;
        }
        char firstChar = target.charAt(0);

        if (value == firstChar) {
            // 最长单词匹配模式，匹配上之后继续匹配子字符串
            TrieNode childEndNode = matchChildren(target.substring(1));
            if(childEndNode != null) {
                return childEndNode;
            }
            // 匹配上当前节点，切当前节点是词的末尾
            if (isEnd) {
                return this;
            }
        }
        // 没有匹配上
        return null;
    }

    private TrieNode matchChildren(String target) {
        if (target.length() < 1 || children == null) {
            return null;
        }

        for (TrieNode child : children) {
            TrieNode result = child.match(target);
            if(result != null) {
                return result;
            }
        }
        return null;
    }
}
