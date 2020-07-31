package com.unisinsight.demo.support.tree.vo;

public class Menu implements Cloneable{
    private String menuCode;
    private Integer length;
    private Integer[] values = new Integer[]{1,2,3,4};

    private int testInt = 7;

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer lenth) {
        this.length = lenth;
    }

    public void updateValue(int index, Integer value) {
        values[index] = value;
    }

    public Integer[] getValues() {
        return values;
    }

    @Override
    public Menu clone() throws CloneNotSupportedException {
        Menu menu = (Menu) super.clone();
        return menu;
    }
}
