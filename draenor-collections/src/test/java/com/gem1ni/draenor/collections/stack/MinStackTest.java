package com.gem1ni.draenor.collections.stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinStackTest {

    private MinStack<Integer> minStack;

    private int MAX_CAPACITY = 127;

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

    @Test
    void test2() {
        IntStream.rangeClosed(1, 129)
                .peek(cap -> System.out.print("cap = " + cap))
                .map(cap -> cap - 1)
                .map(Integer::numberOfLeadingZeros)
                .peek(lz -> System.out.print("; lz = " + lz))
                .map(lz -> -1 >>> lz)
                .peek(n -> System.out.print("; n = " + n))
                .peek(n -> {
                    int result = (n < 0) ? 1 : (n >= MAX_CAPACITY) ? MAX_CAPACITY : n + 1;
                    System.out.print("; r = " + result);
                    System.out.println();
                })
                .forEach(n -> {
                });
    }
}