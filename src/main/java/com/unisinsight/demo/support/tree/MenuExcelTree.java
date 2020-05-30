package com.unisinsight.demo.support.tree;

import com.unisinsight.demo.support.tree.vo.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class MenuExcelTree extends AbstractExcelTree<Menu> {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuExcelTree.class);

    @Override
    ExcelTreeNode<Menu> create(String value) {
        ExcelTreeNode<Menu> node = new ExcelTreeNode<Menu>();
        Menu menu = new Menu();
        menu.setMenuCode(value);
        node.setValue(menu);
        return node;
    }

    public static void main(String[] args) {
        AbstractExcelTree<Menu> excelTree = new MenuExcelTree();
        try {
            String path = "C:\\test.xls";
            InputStream inputStream = new FileInputStream(new File(path));
            List<ExcelTreeNode<Menu>> trees = excelTree.getTree(inputStream);

            System.out.println(trees.size());
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
        }

    }


}
