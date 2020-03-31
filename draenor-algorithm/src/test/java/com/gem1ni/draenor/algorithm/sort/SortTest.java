package com.gem1ni.draenor.algorithm.sort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

class SortTest {

    /**
     * 待排序数组
     */
    private Integer[] intArray;

    @BeforeEach
    public void init() {
        this.intArray = new Integer[]{1, 11, 65, 74, 13, 0, 2, 7, 6, 9, 45, 32};
        System.err.println("before sort:");
        this.printArray(this.intArray);
    }

    /**
     * 打印数组
     *
     * @param array
     */
    private void printArray(Integer[] array) {
        String print = Stream.of(array).map(Object::toString).collect(Collectors.joining(", "));
        System.err.println(print);
    }

    @Test
    public void selectionSort() {
        Sort<Integer> sort = new SelectionSort<>(true);
        Integer[] result = sort.sort(this.intArray);
        this.printArray(result);
    }

    @Test
    public void insertionSort() {
        Sort<Integer> sort = new InsertionSort<>();
        Integer[] result = sort.sort(this.intArray);
        this.printArray(result);
    }

    @Test
    public void shellSort() {
        Sort<Integer> sort = new ShellSort<>(ShellSort.Sequence.KNUTH);
        Integer[] result = sort.sort(this.intArray);
        this.printArray(result);
    }
}