package com.unisinsight.demo.support.tree;

import java.io.InputStream;
import java.util.List;

public interface ExcelTree<T> {
    public List<ExcelTreeNode<T>> getTree(InputStream inputStream);
    public List<ExcelTreeNode<T>> getTree(List<AbstractExcelTree.Line> lines);
}
