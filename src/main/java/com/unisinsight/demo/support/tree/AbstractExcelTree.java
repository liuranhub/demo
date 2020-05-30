package com.unisinsight.demo.support.tree;

import jxl.Cell;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Transactional
public abstract class AbstractExcelTree<T> implements ExcelTree<T>{
    abstract ExcelTreeNode<T> create(String value);

    public List<ExcelTreeNode<T>> getTree(InputStream inputStream) {
        List<ExcelTreeNode<T>> trees = new ArrayList<>();
        Excel excel = readExcel(inputStream);
        if (CollectionUtils.isEmpty(excel.getSheets())) {
            return trees;
        }
        List<Sheet> sheets = excel.getSheets();
        List<Line> lines = sheets.get(0).getAllLine();
        trees = getTree(lines);
        return trees;
    }

    public List<ExcelTreeNode<T>> getTree(List<Line> lines) {
        checkExcel(lines);
        List<ExcelTreeNode<T>> trees = new ArrayList<>();
        Stack<ExcelTreeNode<T>> stack = new Stack<>();

        int lastIndex = 0;
        for (Line line : lines) {
            String[] cells = line.getCells();
            int currentIndex = cells.length;
            ExcelTreeNode<T> node = create(cells[cells.length - 1]);
            while (lastIndex >= currentIndex) {
                stack.pop();
                lastIndex--;
            }

            if (currentIndex == 1) {
                trees.add(node);
            } else {
                ExcelTreeNode<T> parent = stack.peek();
                parent.addChild(node);
                node.setParent(parent);
            }
            stack.push(node);
            lastIndex++;
        }
        return trees;
    }

    private Excel readExcel(InputStream fis) {
        Excel excel = new Excel();
        Workbook book = null;
        try {
            book = Workbook.getWorkbook(fis);
            List<Sheet> sheets = new ArrayList<>();

            for (jxl.Sheet sheet : book.getSheets()) {
                List<Line> allLine = new ArrayList<>();
                int rows = sheet.getRows();
                for (int i = 0; i < rows; i++) {
                    Line line = new Line(sheet.getRow(i));
                    allLine.add(line);
                }
                Sheet mySheet = new Sheet();
                mySheet.setAllLine(allLine);
                sheets.add(mySheet);
            }

            excel.setSheets(sheets);
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        } finally {
            if (book != null) {
                book.close();
            }

            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return excel;
    }

    private void checkExcel(List<Line> lines) {
        int lastLineLength = 0;
        int index = 0;
        for (Line line : lines) {
            index++;
            int contentSize = 0;
            for (String cell : line.getCells()) {
                if (!StringUtils.isEmpty(cell)) {
                    contentSize++;
                }
            }
            int currentLineLength = line.getCells().length;
            if (contentSize != 1 || (currentLineLength - lastLineLength) > 1) {
                throw new RuntimeException("excel不合法(" + index + "行)");
            }
            lastLineLength = currentLineLength;
        }
    }

    private class Excel {
        private List<Sheet> sheets;

        List<Sheet> getSheets() {
            return sheets;
        }

        void setSheets(List<Sheet> sheets) {
            this.sheets = sheets;
        }
    }

    private class Sheet {
        private List<Line> allLine;

        List<Line> getAllLine() {
            return allLine;
        }

        void setAllLine(List<Line> allLine) {
            this.allLine = allLine;
        }

    }

    private class Line {
        private String cells[];

        public Line(String[] cells) {
            this.cells = cells;
        }

        Line(Cell[] data) {
            int length = 0;
            for (int i = 0; i < data.length; i++) {
                if (!StringUtils.isEmpty(data[i].getContents())) {
                    length = i + 1;
                }
            }
            cells = new String[length];

            for (int i = 0; i < length; i++) {
                cells[i] = data[i].getContents();
            }
        }

        String[] getCells() {
            return cells;
        }
    }
}
