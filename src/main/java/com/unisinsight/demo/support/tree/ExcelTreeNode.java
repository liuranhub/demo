package com.unisinsight.demo.support.tree;

import java.util.ArrayList;
import java.util.List;

public class ExcelTreeNode<T> {
    private T value;

    private ExcelTreeNode<T> parent;

    private List<ExcelTreeNode<T>> child = new ArrayList<>();

    public List<ExcelTreeNode<T>> getChild() {
        return child;
    }

    void addChild(ExcelTreeNode<T> node) {
        child.add(node);
    }

    void setParent(ExcelTreeNode<T> parent) {
        this.parent = parent;
    }

    public ExcelTreeNode<T> getParent() {
        return parent;
    }

    public T getValue() {
        return value;
    }

    void setValue(T value) {
        this.value = value;
    }
}
