package com.unisinsight.demo.support.sort;

public class QuickSort extends AbstractSort{
    public QuickSort(){
        super();
    }

    public QuickSort(boolean invert){
        super(invert);
    }

    @Override
    public Comparable[] sort(Comparable[] array) {
        quickSort(array, 0, array.length -1);
        return array;
    }

    private void quickSort(Comparable[] array, int left, int right){
        if (left >= right){
            return;
        }
        //去第一个为以基准数
        int pivot = left;
        int i = left;
        int j = right;
        while (i != j){
            //从右向左找出第一个比基准数小的
            while (fistGteSecond(array[j], array[pivot]) && i < j){
                j --;
            }

            //从左往右找出第一个比基准数大的
            while (fistLteSecond(array[i], array[pivot]) && i < j){
                i ++;
            }
            swap(array, i, j);
        }

        swap(array, pivot, i );

        if (left < right){
            quickSort(array, left, i - 1);
            quickSort(array, i + 1 , right);
        }
    }
}
