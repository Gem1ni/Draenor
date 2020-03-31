package com.gem1ni.draenor.algorithm.sort;

/**
 * SelectionSort
 *
 * @author Gem1ni
 * @date 2020/3/31 8:20
 * @apiNote 选择排序（非稳定）
 * 1. 从左至右遍历，找到最小(大)的元素，然后与第一个元素交换。
 * 2. 从剩余未排序元素中继续寻找最小（大）元素，然后与第二个元素进行交换。
 * 3. 以此类推，直到所有元素均排序完毕。
 */
public class SelectionSort<T extends Comparable<T>> implements Sort<T> {

    /**
     * 降序
     */
    private final boolean desc;

    /**
     * 构造方法
     */
    public SelectionSort() {
        this(false);
    }

    /**
     * 构造方法
     *
     * @param desc 降序排序
     */
    public SelectionSort(boolean desc) {
        this.desc = desc;
    }

    @Override
    public T[] sort(T[] array) {
        int length = array.length;
        for (int i = 0; i < length; i++) {
            int current = i;
            for (int j = i + 1; j < length; j++) {
                if (this.desc && 0 > array[current].compareTo(array[j])) {
                    current = j;
                } else if (!this.desc && 0 < array[current].compareTo(array[j])) {
                    current = j;
                }
            }
            this.swap(array, current, i);
        }
        return array;
    }
}
