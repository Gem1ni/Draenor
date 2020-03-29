package com.gem1ni.draenor.algorithm.node;

/**
 * IntListNode
 *
 * @author Gem1ni
 * @date 2020/3/29 23:59
 * @apiNote 整型链表节点
 */
public class IntListNode implements LinkedNode<Integer> {

    /**
     * 整型值
     */
    private final int value;

    /**
     * 下一个节点
     */
    private LinkedNode<Integer> next;

    /**
     * 构造方法
     *
     * @param value
     */
    public IntListNode(int value) {
        this.value = value;
    }

    @Override
    public LinkedNode<Integer> getNext() {
        return this.next;
    }

    @Override
    public LinkedNode<Integer> setNext(LinkedNode<Integer> next) {
        this.next = next;
        return this;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
