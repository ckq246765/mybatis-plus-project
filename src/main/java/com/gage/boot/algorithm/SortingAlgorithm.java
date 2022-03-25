package com.gage.boot.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.BinaryOperator;

/**
 * 十大今典排序算法
 */
public class SortingAlgorithm {

    /**
     * 冒泡排序算法：
     * <1>.比较相邻的元素。如果第一个比第二个大，就交换它们两个；
     * <2>.对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，这样在最后的元素应该会是最大的数；
     * <3>.针对所有的元素重复以上的步骤，除了最后一个；
     * <4>.重复步骤1~3，直到排序完成。
     * @param arr
     */
    public void bubbleSort1(int[] arr){
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len - 1 - i; j++) {
                if (arr[j] > arr[j+1]){
                    int temp = arr[j];arr[j] = arr[j+1];arr[j+1] = temp;
                }
            }
        }
    }

    /**
     * 冒泡排序算法：
     * 设置一标志性变量pos,用于记录每趟排序中最后一次进行交换的位置。由于pos位置之后的记录均已交换到位,故在进行下一趟排序时只要扫描到pos位置即可
     * @param arr
     */
    public void bubbleSort2(int[] arr){
        int len = arr.length - 1;
        while (len > 0){
            int pos = 0;
            for (int i = 0; i < len; i++) {
                if (arr[i] > arr[i+1]){
                    pos = i;
                    int temp = arr[i];arr[i] = arr[i+1];arr[i+1] = temp;
                }
            }
            len = pos;
        }
    }

    /**
     * 冒泡排序算法：
     * 传统冒泡排序中每一趟排序操作只能找到一个最大值或最小值,我们考虑利用在每趟排序中进行正向和反向两遍冒泡的方法一次可以得到两个最终值(最大者和最小者) , 从而使排序趟数几乎减少了一半。
     * @param arr
     */
    public void bubbleSort3(int[] arr){
        int low = 0;
        int high = arr.length - 1;
        int temp , j;
        while (low < high){
            for (j = low; j < high; ++j) {
                if (arr[j] > arr[j+1]){
                    temp = arr[j];arr[j] = arr[j+1]; arr[j+1]=temp;
                }
            }
            --high;
            for (j = high; j > low; --j) {
                if (arr[j] < arr[j-1]){
                    temp = arr[j];arr[j] = arr[j-1]; arr[j-1]=temp;
                }
            }
            ++low;
        }
    }

    /**
     * 选择排序：
     * @param arr
     */
    public void selectionSort(int[] arr){
        int len = arr.length;
        int minIndex, temp;
        for (int i = 0; i < len-1; i++) {
            minIndex = i;
            for (int j = i+1; j < len; j++) {
                if (arr[j] < arr[minIndex]){
                    minIndex = j;
                }
            }
            if (i != minIndex){
                temp = arr[i];arr[i] = arr[minIndex]; arr[minIndex]=temp;
            }
        }
    }

    /**
     * 插入排序：
     * 它的工作原理：首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。以此类推，直到所有元素均排序完毕。
     * @param arr
     */
    public void insertSort(int[] arr){
        for (int i = 1; i < arr.length; i++) {
            int value = arr[i];
            int position = i;
            while (position > 0 && arr[position-1] > value){
                arr[position] = arr[position-1];
                --position;
            }
            arr[position] = value;
        }
    }

    /**
     * 改进插入排序： 查找插入位置时使用二分查找的方式
     * @param arr
     */
    public void binaryInsertionSort(int[] arr){
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int left = 0;
            int right = i - 1;
            while (left <= right) {
                int middle = (left + right) / 2;
                if (key < arr[middle]) {
                    right = middle - 1;
                } else {
                    left = middle + 1;
                }
            }
            for (int j = i - 1; j >= left; j--) {
                arr[j + 1] = arr[j];
            }
            arr[left] = key;
        }
    }

    /**
     * 希尔排序：
     * @param arr
     */
    public void shellSort(int[] arr){
        int temp;
        for (int delta = arr.length/2; delta >= 1; delta /= 2){                              //对每个增量进行一次排序
            for (int i = delta; i < arr.length; i++){
                for (int j = i; j >= delta && arr[j] < arr[j-delta]; j -= delta){ //注意每个地方增量和差值都是delta
                    temp = arr[j-delta];
                    arr[j-delta] = arr[j];
                    arr[j] = temp;
                }
            }//loop i
        }//loop delta
    }
    public void shellSort2(@org.jetbrains.annotations.NotNull int[] arr){
        int delta = 1;
        while (delta < arr.length/3){//generate delta
            delta=delta*3+1;    // <O(n^(3/2)) by Knuth,1973>: 1, 4, 13, 40, 121, ...
        }
        int temp;
        for (; delta>=1; delta/=3){
            for (int i=delta; i<arr.length; i++){
                for (int j=i; j>=delta && arr[j]<arr[j-delta]; j-=delta){
                    temp = arr[j-delta];
                    arr[j-delta] = arr[j];
                    arr[j] = temp;
                }
            }//loop i
        }//loop delta
    }

    /**
     * 归并排序：
     * @param arr
     */
    public void mergeSort(int[] arr){
        int[] temp = new int[arr.length];
        internalMergeSort(arr, temp, 0, arr.length-1);
    }
    public void internalMergeSort(int[] arr, int[] temp, int left, int right){
        if (left < right){
            int middle = (left + right) / 2;
            internalMergeSort(arr, temp, left, middle);
            internalMergeSort(arr, temp, middle + 1, right);
            mergeSortArray(arr, temp, left, middle, right);
        }
    }
    public void mergeSortArray(int[] arr, int[] temp, int left, int middle, int right){
        int i = left;
        int j = middle + 1;
        int k = 0;
        while (i <= middle && j <= right){
            temp[k++] = (arr[i] <= arr[j]) ? arr[i++] : arr[j++];
        }
        while (i <= middle){
            temp[k++] = arr[i++];
        }
        while (j <= right){
            temp[k++] = arr[j++];
        }
        for (i = 0; i < k; i++) {
            arr[left + i] = temp[i];
        }
    }

    /**
     * 快速排序：
     * @param arr
     */
    public void quickSort(int[] arr){
        qsort(arr, 0, arr.length - 1);
    }
    public void qsort(int[] arr, int low, int hight){
        if (low >= hight){
            return;
        }
        int pivot = partition(arr, low, hight);
        qsort(arr, low, pivot - 1);
        qsort(arr, pivot + 1, hight);
    }
    public int partition(int[] arr, int low, int high){
        int pivot = arr[low];
        while (low < high){
            while (low < high && arr[high] >= pivot){
                --high;
            }
            arr[low] = arr[high];
            while (low < high && arr[low] <= pivot){
                ++low;
            }
            arr[high] = arr[low];
        }
        arr[low] = pivot;
        return low;
    }


    private int getParentIndex(int child) {
        return (child - 1) / 2;
    }
    private int getLeftChildIndex(int parent) {
        return 2 * parent + 1;
    }
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    /**
     * 调整堆。
     */
    private void adjustHeap(int[] arr, int i, int len) {
        int left, right, j;
        left = getLeftChildIndex(i);
        while (left <= len) {
            right = left + 1;
            j = left;
            if (j < len && arr[left] < arr[right]) {
                j++;
            }
            if (arr[i] < arr[j]) {
                swap(arr, i, j);
                i = j;
                left = getLeftChildIndex(i);
            } else {
                break; // 停止筛选
            }
        }
    }
    /**
     * 堆排序。
     * */
    public void sort(int[] arr) {
        int last = arr.length - 1;
        // 初始化最大堆
        for (int i = getParentIndex(last); i >= 0; --i) {
            adjustHeap(arr, i, last);
        }
        // 堆调整
        while (last >= 0) {
            swap(arr, 0, last--);
            adjustHeap(arr, 0, last);
        }
    }


    /**
     * 计数排序：
     * @param a
     * @param max
     * @param min
     */
    public void countSort(int[] a, int max, int min) {
        int[] b = new int[a.length];//存储数组
        int[] count = new int[max - min + 1];//计数数组
        for (int num = min; num <= max; num++) {
            //初始化各元素值为0，数组下标从0开始因此减min
            count[num - min] = 0;
        }
        for (int i = 0; i < a.length; i++) {
            int num = a[i];
            count[num - min]++;//每出现一个值，计数数组对应元素的值+1
        }
        for (int num = min + 1; num <= max; num++) {
            //加总数组元素的值为计数数组对应元素及左边所有元素的值的总和
            count[num - min] += count[num - min - 1];
        }
        for (int i = 0; i < a.length; i++) {
            int num = a[i];//源数组第i位的值
            int index = count[num - min] - 1;//加总数组中对应元素的下标
            b[index] = num;//将该值存入存储数组对应下标中
            count[num - min]--;//加总数组中，该值的总和减少1。
        }
        //将存储数组的值一一替换给源数组
        for(int i=0;i<a.length;i++){
            a[i] = b[i];
        }
    }

    /**
     * 桶排序：
     * @param arr
     */
    public static void bucketSort(int[] arr){
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < arr.length; i++){
            max = Math.max(max, arr[i]);
            min = Math.min(min, arr[i]);
        }
        //桶数
        int bucketNum = (max - min) / arr.length + 1;
        ArrayList<ArrayList<Integer>> bucketArr = new ArrayList<>(bucketNum);
        for(int i = 0; i < bucketNum; i++){
            bucketArr.add(new ArrayList<Integer>());
        }
        //将每个元素放入桶
        for(int i = 0; i < arr.length; i++){
            int num = (arr[i] - min) / (arr.length);
            bucketArr.get(num).add(arr[i]);
        }
        //对每个桶进行排序
        for(int i = 0; i < bucketArr.size(); i++){
            Collections.sort(bucketArr.get(i));
        }
        System.out.println(bucketArr.toString());
    }

    /**
     * 基数排序：
     * @param array
     * @param radix
     */
    public void sort(int[] array, int radix) {
        // 数组的第一维表示可能的余数0-radix，第二维表示array中的等于该余数的元素
        // 如：十进制123的个位为3，则bucket[3][] = {123}
        int[][] bucket = new int[radix][array.length];
        int distance = getDistance(array, radix); // 表示最大的数有多少位
        int temp = 1;
        int round = 1; // 控制键值排序依据在哪一位
        while (round <= distance) {
            // 用来计数：数组counter[i]用来表示该位是i的数的个数
            int[] counter = new int[radix];
            // 将array中元素分布填充到bucket中，并进行计数
            for (int i = 0; i < array.length; i++) {
                int which = (array[i] / temp) % radix;
                bucket[which][counter[which]] = array[i];
                counter[which]++;
            }
            int index = 0;
            // 根据bucket中收集到的array中的元素，根据统计计数，在array中重新排列
            for (int i = 0; i < radix; i++) {
                if (counter[i] != 0)
                    for (int j = 0; j < counter[i]; j++) {
                        array[index] = bucket[i][j];
                        index++;
                    }
                counter[i] = 0;
            }
            temp *= radix;
            round++;
        }
    }
    private int getDistance(int[] array, int radix) {
        int max = computeMax(array);
        int digits = 0;
        int temp = max / radix;
        while(temp != 0) {
            digits++;
            temp = temp / radix;
        }
        return digits + 1;
    }
    private int computeMax(int[] array) {
        int max = array[0];
        for(int i=1; i<array.length; i++) {
            if(array[i]>max) {
                max = array[i];
            }
        }
        return max;
    }



    public static void main(String[] args) {
        int[] arr = {3,44,38,5,47,15,36,26,27,2,46,4,19,50,48};
        new SortingAlgorithm().sort(arr,10);
        System.out.println(Arrays.toString(arr));
    }

}
