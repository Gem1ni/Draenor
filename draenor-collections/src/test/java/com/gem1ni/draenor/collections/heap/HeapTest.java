package com.gem1ni.draenor.collections.heap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HeapTest {

    private Heap<Integer> heap;

    @BeforeEach
    void setUp() {
        this.heap = new BinaryMinHeap<>(100);
        int numItems = 100;
        for (int i = 7; i != 0; i = (i + 7) % numItems) {
            this.heap.add(i * 2);
        }
    }

    @Test
    void test1() {

        for (int j = this.heap.size(); j > 1; j--) {
            System.err.println("removed root: " + this.heap.removeRoot() + ", current root: " + this.heap.getRoot());
        }
    }
}