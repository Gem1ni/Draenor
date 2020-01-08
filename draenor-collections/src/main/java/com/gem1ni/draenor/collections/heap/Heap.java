package com.gem1ni.draenor.collections.heap;

import java.util.Collection;

/**
 * Heap
 * 堆接口定义
 *
 * @author Gem1ni
 */
public interface Heap<E extends Comparable<? super E>> extends Collection<E> {

    /**
     * 移除堆顶元素
     *
     * @return 堆顶元素
     */
    E removeRoot();

    /**
     * 获取堆顶元素
     *
     * @return 堆顶元素
     */
    E getRoot();
}
