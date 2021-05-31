package com.unisinsight.demo.support.trie;

import java.util.ArrayList;
import java.util.List;

public class TrieNode {
    private final char value;
    private final List<TrieNode> children = new ArrayList<>();
    private final TrieNode parent;
    private boolean isEnd;

    public TrieNode(char value, TrieNode parent){
        this.value = value;
        this.parent = parent;
        this.isEnd = false;
    }

    public void addWord(String word){
        if (word == null || word.length() < 1) {
            return;
        }
        boolean isEnd = word.length() == 1;
        char firstWord = word.charAt(0);
        TrieNode child = getChildByValue(firstWord, isEnd);

        String wordSubStr = word.substring(1);
        if (wordSubStr.length() > 0){
            child.addWord(wordSubStr);
        }
    }

    private TrieNode getChildByValue(char value, boolean isEnd){
        TrieNode node = null;
        for (TrieNode ctn : children) {
            if (ctn.value == value) {
                node = ctn;
            }
        }
        if(node == null) {
            node = new TrieNode(value, this);
            children.add(node);
        }
        if (isEnd) {
            node.isEnd = true;
        }
        return node;
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
