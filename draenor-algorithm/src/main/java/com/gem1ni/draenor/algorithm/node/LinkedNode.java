package com.gem1ni.draenor.algorithm.node;

/**
 * LinkedNode
 *
 * @author Gem1ni
 * @date 2020/3/29 23:56
 * @apiNote 链表节点接口定义
 */
public interface LinkedNode<Val> extends Node<Val> {

    /**
     * 获取下一个节点
     *
     * @return 下一个节点
     */
    LinkedNode<Val> getNext();

    /**
     * 设置下一个节点
     *
     * @param next 下一个节点
     * @return 当前节点
     */
    LinkedNode<Val> setNext(LinkedNode<Val> next);

    /**
     * 打印链表
     */
    default void print() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getValue());
        LinkedNode<Val> next = this.getNext();
        while (null != next) {
            builder.append(" -> ").append(next.getValue());
            next = next.getNext();
        }
        System.err.println(builder.toString());
    }
}
