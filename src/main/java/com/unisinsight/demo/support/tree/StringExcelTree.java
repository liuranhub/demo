/*
 * www.unisinsight.com Inc.
 * Copyright (c) 2018 All Rights Reserved
 */
package com.unisinsight.demo.support.tree;

/**
 * description
 *
 * @author longjiang [KF.longjiang@h3c.com]
 * @date 2018/11/08 17:25
 * @since 1.0
 */
public class StringExcelTree extends AbstractExcelTree<String> {
    @Override
    ExcelTreeNode<String> create(String value) {
        ExcelTreeNode<String> node = new ExcelTreeNode<String>();
        node.setValue(value);
        return node;
    }
}
