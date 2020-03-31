package com.gem1ni.draenor.algorithm.sort;

/**
 * Sort
 *
 * @author Gem1ni
 * @date 2020/3/31 8:17
 * @apiNote 排序算法
 */
public interface Sort<T extends Comparable<T>> {

    /**
     * 排序
     *
     * @param array 待排序数组
     * @return 排序后数组
     */
    T[] sort(T[] array);

    /**
     * 交换数组中两个元素的位置
     *
     * @param array   数组
     * @param one     一个元素
     * @param another 另一个元素
     */
    default void swap(T[] array, int one, int another) {
        T tmp = array[one];
        array[one] = array[another];
        array[another] = tmp;
    }
}
