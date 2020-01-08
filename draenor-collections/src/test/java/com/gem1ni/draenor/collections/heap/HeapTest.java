package com.gem1ni.draenor.collections.heap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class HeapTest {

    private Heap<Integer> heap;

    @BeforeEach
    void setUp() {
        this.heap = new BinaryMinHeap<>(100);
        int numItems = 32;
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

    @Test
    void testAddAll() {
        List<Integer> toAdd = Stream.of(5, 9, 13, 7, 9, 3)
                .collect(Collectors.toList());
        this.heap.addAll(toAdd);
        System.err.println(this.heap);
    }
}