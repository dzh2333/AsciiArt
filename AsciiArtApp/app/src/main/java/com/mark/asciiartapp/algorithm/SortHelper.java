package com.mark.asciiartapp.algorithm;

import android.content.Intent;

import java.util.List;

/**
 * 排序帮助类
 */
public class SortHelper {

    //归并排序
    public static int[] sortMinToMax(int[] inputs){
        int[] res = new int[inputs.length];
        sort(res, 0, res.length - 1);
        return res;
    }

    public static void sort(int[] array, int start, int end){
        if (start >= end){
            return;
        }
        int mid = (end + start) / 2;
        sort(array, start, mid);
        sort(array, mid, end);
        merge(array, start, mid +1, end);
    }

    public static void merge(int[] array, int start, int mid, int end){
        int[] copyArray = new int[end - start + 1];
        for (int i = 0; i < copyArray.length;i++) {
            copyArray[i] = array[i + start];
        }
        int leftIndex = start, rightIndex = mid;
        for (int i = 0; i <= end; i++){
            if (leftIndex >= mid) {
                // 如果左边搞完
                array[i] = copyArray[rightIndex - start];
                rightIndex++;
            } else if (rightIndex > end) {
                // 如果右边边搞完
                array[i] = copyArray[leftIndex - start];
                leftIndex++;
            } else if (copyArray[leftIndex - start] <= copyArray[rightIndex - start]) {
                // 如果左边比右边小
                array[i] = copyArray[leftIndex - start];
                leftIndex++;
            } else if (copyArray[leftIndex - start] > copyArray[rightIndex - start]) {
                // 如果右边比左边小
                array[i] = copyArray[rightIndex - start];
                rightIndex++;
            }
        }
    }

    //快速排序
    public static void quickSort(int[] arr,int low,int high){
        int i,j,temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        //temp就是基准位
        temp = arr[low];

        while (i<j) {
            //先看右边，依次往左递减
            while (temp<=arr[j]&&i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp>=arr[i]&&i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }

        }
        //最后将基准为与i和j相等位置的数字交换
        arr[low] = arr[i];
        arr[i] = temp;
        //递归调用左半数组
        quickSort(arr, low, j-1);
        //递归调用右半数组
        quickSort(arr, j+1, high);
    }

}
