package com.gem1ni.draenor.collections.heap;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * AbstractHeap
 * 堆的定义
 *
 * @author Gem1ni
 */
abstract class AbstractHeap<E extends Comparable<? super E>> extends AbstractCollection<E>
        implements Heap<E> {

    /**
     * 默认的容量：8
     */
    private static final int DEFAULT_CAPACITY = 8;

    /**
     * 最大容量：65536
     */
    private static final int MAX_CAPACITY = 65536;

    /**
     * 当前堆中元素数量
     */
    private int currentSize;

    /**
     * 堆数组
     */
    private E[] array;

    /**
     * 默认构造方法
     */
    public AbstractHeap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * 构造方法
     *
     * @param capacity 容量
     * @apiNote 这里对初始化容量进行修正，确保容量为 2 的 n 次幂
     */
    @SuppressWarnings("unchecked")
    public AbstractHeap(int capacity) {
        if (MAX_CAPACITY < capacity) {
            // 容量太大，不宜使用本数据结构
            throw new IllegalArgumentException("You probably need a mountain, not a heap.");
        }
        int cap = Math.max(DEFAULT_CAPACITY, capacity);
        this.currentSize = 0;
        // 初始化数组，确保容量是 2 的 n 次幂
        this.array = (E[]) new Comparable[fixCapacity(cap + 1)];
    }

    /**
     * 构造方法
     *
     * @param items 堆元素数组
     * @apiNote 通过数组快速构建堆对象
     */
    public AbstractHeap(E[] items) {
        this(items.length);
        int hole = 1;
        for (E item : items) {
            this.array[hole++] = item;
        }
        this.build();
    }

    /**
     * 修正容量值，使其为 2 的 n 次幂
     *
     * @param cap 容量
     * @return 修正后容量
     */
    static int fixCapacity(int cap) {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
        return (n < 0) ? 1 : (n >= MAX_CAPACITY) ? MAX_CAPACITY : n + 1;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayItr<>(this.array);
    }

    @Override
    public int size() {
        return this.currentSize;
    }

    /**
     * 堆构建逻辑
     *
     * @return 构建堆的逻辑
     */
    protected abstract BiFunction<E, E, Boolean> logic();

    /**
     * 扩容
     *
     * @param newCapacity 新数组长度
     */
    @SuppressWarnings("unchecked")
    private void enlarge(int newCapacity) {
        if (MAX_CAPACITY < newCapacity) {
            throw new IllegalArgumentException("Maximum heap size exceeded.");
        }
        E[] old = this.array;
        this.array = (E[]) new Comparable[newCapacity];
        System.arraycopy(old, 0, this.array, 0, old.length);
    }

    @Override
    public boolean add(E e) {
        if (this.size() == this.array.length - 1) {
            this.enlarge(fixCapacity(this.array.length + 1));
        }
        if (this.size() == MAX_CAPACITY) {
            return false;
        }
        int hole = ++this.currentSize;
        this.array[0] = this.array[hole] = e;
        // 向上维护
        this.percolateUp(hole);
        // array[0] 置空
        this.array[0] = null;
        return true;
    }

    @Override
    public E getRoot() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.array[1];
    }

    @Override
    public E removeRoot() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        E root = this.getRoot();
        this.array[1] = this.array[this.currentSize--];
        // 向下维护
        this.percolateDown(1);
        return root;
    }

    /**
     * 向上维护节点
     *
     * @param hole 堆索引，从 1 开始
     * @apiNote 逻辑就是找父节点，比大小，看要不要交换位置
     */
    private void percolateUp(int hole) {
        E tmp = this.array[hole];
        for (; this.logic().apply(tmp, this.array[(hole >> 1)]); hole >>= 1) {
            this.array[hole] = this.array[(hole >> 1)];
        }
        this.array[hole] = tmp;
    }

    /**
     * 向下维护节点
     *
     * @param hole 堆索引，从 1 开始
     * @apiNote 找左子节点，对比左右子节点的大小，取其小，再与当前维护的节点值对比，看要不要交换位置
     */
    private void percolateDown(int hole) {
        E tmp = this.array[hole];
        int child;
        for (; hole << 1 <= this.size(); hole = child) {
            child = hole << 1;
            if (child != this.size() && this.logic().apply(this.array[child + 1], this.array[child])) {
                child++;
            }
            if (this.logic().apply(this.array[child], tmp)) {
                this.array[hole] = this.array[child];
            } else {
                break;
            }
        }
        this.array[hole] = tmp;
    }

    /**
     * ArrayItr
     * 堆迭代器
     *
     * @author Gem1ni
     */
    private static class ArrayItr<E> implements Iterator<E> {
        private int cursor;
        private final E[] a;

        ArrayItr(E[] a) {
            this.a = a;
        }

        @Override
        public boolean hasNext() {
            return this.cursor < this.a.length;
        }

        @Override
        public E next() {
            int i = this.cursor;
            if (i >= this.a.length) {
                throw new NoSuchElementException();
            }
            this.cursor = i + 1;
            return this.a[i];
        }
    }

    /**
     * 构造堆数据结构
     */
    private void build() {
        for (int i = this.size() >> 1; i > 0; i--) {
            this.percolateDown(i);
        }
    }

    @Override
    public String toString() {
        return Stream.of(this.array)
                .filter(Objects::nonNull)
                .map(Objects::toString)
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
