package com.gem1ni.draenor.algorithm.sort;

import java.util.Arrays;

/**
 * MergeSort
 *
 * @author Gem1ni
 * @date 2020/3/31 9:40
 * @apiNote 合并排序（稳定）
 * 1. 申请空间，使其大小为两个已经排序序列之和，然后将待排序数组复制到该数组中。
 * 2. 设定两个指针，最初位置分别为两个已经排序序列的起始位置
 * 3. 比较复制数组中两个指针所指向的元素，选择相对小的元素放入到原始待排序数组中，并移动指针到下一位置
 * 4. 重复步骤 3 直到某一指针达到序列尾
 * 5. 将另一序列剩下的所有元素直接复制到原始数组末尾
 */
public class MergeSort<T extends Comparable<T>> implements Sort<T> {

    /**
     * 辅助数组
     */
    private T[] helpArray;

    /**
     * 插入排序
     */
    private InsertionSort<T> insertionSort;

    /**
     * 数组长度为 7 时替换为插入排序
     */
    private static final int CUTOFF = 7;

    @SuppressWarnings("unchecked")
    @Override
    public void sort(T[] array) {
        // 把待排序数组的元素拷贝到辅助数组中
        this.helpArray = (T[]) Arrays.copyOf(array, array.length, array.getClass());
        this.insertionSort = new InsertionSort<>();
        this.sort(array, 0, array.length - 1);
    }

    /**
     * 排序
     *
     * @param array     数组
     * @param fromIndex 开始下标
     * @param toIndex   结束下标
     */
    private void sort(T[] array, int fromIndex, int toIndex) {
        if (fromIndex >= toIndex) {
            return;
        }
        if (toIndex <= fromIndex + CUTOFF - 1) {
            this.insertionSort.sort(array, fromIndex, toIndex);
        }
        // 平分数组
        int splitIndex = fromIndex + (toIndex - fromIndex) / 2;
        // 递归左边数组排序
        this.sort(array, fromIndex, splitIndex);
        // 递归右边数组排序
        this.sort(array, splitIndex + 1, toIndex);
        // 如果左边数组的尾部小于或等于右边数组的头部，则不需要合并
        if (0 >= array[splitIndex].compareTo(array[splitIndex + 1])) {
            return;
        }
        // 合并左右两个数组
        this.merge(array, fromIndex, splitIndex, toIndex);
    }

    /**
     * 合并左右两个数组
     *
     * @param array      数组
     * @param fromIndex  开始下标
     * @param splitIndex 拆分下标（即左边数组结束下标）
     * @param toIndex    结束下标
     */
    private void merge(T[] array, int fromIndex, int splitIndex, int toIndex) {
        // left 是左边数组的指针，right 是右边数组的指针
        int left = fromIndex, right = splitIndex + 1;
        // 将左右两个数组拷贝到辅助数组中
        if (toIndex + 1 - fromIndex >= 0)
            System.arraycopy(array, fromIndex, this.helpArray, fromIndex, toIndex + 1 - fromIndex);
        // 按照规则将数据从辅助数组中拷贝回原数组
        for (int k = fromIndex; k <= toIndex; k++) {
            if (left > splitIndex) {
                // 左边数组元素没了，直接将右边的剩余元素合并到原数组
                array[k] = this.helpArray[right++];
            } else if (right > toIndex) {
                // 如果右边元素没了，则将左边的剩余元素合并到原数组
                array[k] = this.helpArray[left++];
            } else if (0 > this.helpArray[left].compareTo(this.helpArray[right])) {
                // 如果左边指针的元素小，则拷贝左边指针的元素到原数组
                array[k] = this.helpArray[left++];
            } else {
                // 反之拷贝右边指针的元素到原数组
                array[k] = this.helpArray[right++];
            }
        }
    }
}
