package com.gem1ni.draenor.collections.heap;

import java.util.function.BiFunction;

/**
 * BinaryMinHeap
 * 小根堆
 *
 * @author Gem1ni
 */
public class BinaryMinHeap<E extends Comparable<? super E>> extends AbstractHeap<E> {

    /**
     * 默认构造方法
     */
    public BinaryMinHeap() {
        super();
    }

    /**
     * 指定容量构造方法
     *
     * @param capacity 容量
     */
    public BinaryMinHeap(int capacity) {
        super(capacity);
    }

    /**
     * 通过数组快速构造堆对象
     *
     * @param items 堆元素数组
     */
    public BinaryMinHeap(E[] items) {
        super(items);
    }

    @Override
    protected BiFunction<E, E, Boolean> logic() {
        return (hole, target) -> 0 > hole.compareTo(target);
    }
}
