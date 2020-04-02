package com.gem1ni.draenor.algorithm.sort;

/**
 * InsertionSort
 *
 * @author Gem1ni
 * @date 2020/3/31 8:55
 * @apiNote 插入排序（稳定）
 * 1. 从第一个元素开始，该元素可以认为已经被排序
 * 2. 取出下一个元素，在已经排序的元素序列中从后向前扫描
 * 3. 如果该元素小于前面的元素（已排序），则依次与前面元素进行比较如果小于则交换，直到找到大于该元素的就则停止；
 * 4. 如果该元素大于前面的元素（已排序），则重复步骤2
 * 5. 重复步骤 2~4 直到所有元素都排好序 。
 */
public class InsertionSort<T extends Comparable<T>> implements Sort<T> {

    /**
     * 默认构造方法
     */
    public InsertionSort() {
    }

    @Override
    public void sort(T[] array) {
        this.sort(array, 0, array.length);
    }

    /**
     * 排序
     *
     * @param array 数组
     * @param from  起始下标
     * @param to    结束下标
     */
    public void sort(T[] array, int from, int to) {
        // 从第二个元素开始
        for (int i = from + 1; i < to; i++) {
            // 从第 i 个元素开始，依次和前面 i - 1 个元素比较，如果小于，就交换位置
            for (int j = i; j > 0; j--) {
                if (0 > array[j].compareTo(array[j - 1])) {
                    this.swap(array, j, j - 1);
                } else {
                    // 如果大于，则不用继续往前比较了，因为前面的元素已经排好序，比较大的大就是较大的了。
                    break;
                }
            }
        }
    }
}
