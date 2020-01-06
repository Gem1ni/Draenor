package com.gem1ni.draenor.collections.stack;

import java.util.Stack;

/**
 * MinStack 支持获取栈中最小值的栈
 *
 * @author Gem1ni
 * @date 2020/1/6 21:03
 */
public class MinStack<E extends Comparable<E>> extends Stack<E> {

    /**
     * 辅助栈：存放最小值的索引
     */
    private Stack<Integer> minIndexes;

    /**
     * 构造方法
     */
    public MinStack() {
        super();
        this.minIndexes = new Stack<>();
    }

    @Override
    public synchronized E push(E item) {
        // 入栈
        super.push(item);
        // 如果是空栈
        if (this.minIndexes.empty()) {
            // 初始化一个 0 索引
            this.minIndexes.push(0);
        } else {
            // 不是空栈
            E currentMin = this.getMin();
            // 判断是否比当前最小元素还小
            if (0 < currentMin.compareTo(item)) {
                // 当前push的是最小元素，压入辅助栈顶
                this.minIndexes.push(this.size() - 1);
            }
        }
        return item;
    }

    @Override
    public synchronized E pop() {
        // 出栈
        E item = super.pop();
        // 出栈后 this.size() 如果等于辅助栈的栈顶元素值，则表明刚出栈的是最小元素
        if (this.size() == this.minIndexes.peek()) {
            // 这里this.size() 其实就是出栈前 item 的索引值
            this.minIndexes.pop();
        }
        return item;
    }

    /**
     * 获取栈中当前最小值
     *
     * @return 当前最小值
     */
    public E getMin() {
        // 获取辅助栈栈顶元素值，即数据栈的最小元素索引
        Integer minIndex = this.minIndexes.peek();
        // 通过 Stack 自带的 elementAt(int index) 方法获取数据栈中最小元素的值
        return super.elementAt(minIndex);
    }
}
