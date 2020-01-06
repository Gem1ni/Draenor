package com.gem1ni.draenor.collections.stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinStackTest {

    private MinStack<Integer> minStack;

    @BeforeEach
    void setUp() {
        this.minStack = new MinStack<>();
        Stream.of(5, 5, 6, 2, 4, 7, 9, 3, 1, 1, 2, 8, 4).forEach(this.minStack::push);
    }

    @Test
    void test1() {
        while (!this.minStack.empty()) {
            System.out.println("current Min element: " + this.minStack.getMin());
            System.out.println("current Popped element: " + this.minStack.pop());
        }
        assertEquals(1, 1);

    }
}