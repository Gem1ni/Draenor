package com.gem1ni.draenor.algorithm.node;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReverseByGroup {

    /**
     * 链表的头节点
     */
    private LinkedNode<Integer> head;

    @BeforeEach
    public void init() {
        this.head = new IntListNode(0);
        LinkedNode<Integer> tmp = this.head;
        for (int i = 1; i < 10; i++) {
            LinkedNode<Integer> next = new IntListNode(i);
            tmp.setNext(next);
            tmp = next;
        }
        this.head.print();
    }

    @Test
    public void reverseLinkedListByGroup() {
        LinkedNode<Integer> result = this.reverseByGroup(this.head, 4);
        result.print();
    }

    /**
     * N个一组翻转链表
     *
     * @param head   表头
     * @param byStep N个一组
     * @return 新表头节点
     */
    private LinkedNode<Integer> reverseByGroup(LinkedNode<Integer> head, int byStep) {
        if (null == head || null == head.getNext()) {
            return head;
        }
        LinkedNode<Integer> tail = head;
        for (int i = 0; i < byStep; i++) {
            if (null == tail) {
                return head;
            }
            tail = tail.getNext();
        }
        LinkedNode<Integer> newHead = this.reverse(head, tail);
        head.setNext(this.reverseByGroup(tail, byStep));
        return newHead;
    }

    /**
     * 从头至尾翻转链表
     *
     * @param head 表头
     * @param tail 尾巴
     * @return 反转后的表头
     */
    private LinkedNode<Integer> reverse(LinkedNode<Integer> head, LinkedNode<Integer> tail) {
        LinkedNode<Integer> previous = null;
        LinkedNode<Integer> next = null;
        while (head != tail) {
            next = head.getNext();
            head.setNext(previous);
            previous = head;
            head = next;
        }
        return previous;
    }
}
