package com.unisinsight.demo.support.sort;

public class BubbleSort extends AbstractSort{
    public BubbleSort(){
        super();
    }
    public BubbleSort(boolean invert){
        super(invert);
    }

    @Override
    public Comparable[] sort(Comparable[] list) {
        for (int i = 0; i < list.length ;i ++){
            for (int j = 0; j < list.length - i -1 ;j ++){
                Comparable fist = list[j];
                Comparable second = list[j + 1];
                if (fistGtSecond(fist, second)){
                    swap(list, j, j + 1);
                }
            }
        }

        return list;
    }
}
