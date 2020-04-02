package com.gem1ni.draenor.algorithm.sort;

/**
 * ShellSort
 *
 * @author Gem1ni
 * @date 2020/3/31 9:14
 * @apiNote 希尔排序（非稳定）
 */
public class ShellSort<T extends Comparable<T>> implements Sort<T> {

    /**
     * 步长序列枚举
     */
    enum Sequence {
        SHELL {
            @Override
            public int initStep(int length) {
                return length;
            }

            @Override
            public int nextStep(int currentStep) {
                return currentStep / 2;
            }
        },
        HIBBARD {
            @Override
            public int initStep(int length) {
                int step = 1;
                while (step < length / 2) {
                    step = step * 2 + 1;
                }
                return step;
            }

            @Override
            public int nextStep(int currentStep) {
                return (currentStep - 1) / 2;
            }
        },
        KNUTH {
            @Override
            public int initStep(int length) {
                int step = 1;
                while (step < length / 3) {
                    step = step * 3 + 1;
                }
                return step;
            }

            @Override
            public int nextStep(int currentStep) {
                return (currentStep - 1) / 3;
            }
        },
        SEDGEWICK {
            // 该序列由下面两个表达式交互获得:
            // 1, 19, 109, 505, 2161,….., 9(4k – 2k) + 1, k = 0, 1, 2, 3,…
            // 5, 41, 209, 929, 3905,…..2k+2 (2k+2 – 3 ) + 1, k = 0, 1, 2, 3, …

            @Override
            public int initStep(int length) {
                // ... 好像没想出来怎么实现
                return 0;
            }

            @Override
            public int nextStep(int currentStep) {
                // ... 好像没想出来怎么实现
                return 0;
            }
        },
        ;

        /**
         * 初始化步长
         *
         * @param length 数组长度
         * @return 初始化递减步长
         */
        public abstract int initStep(int length);

        /**
         * 下一个步长
         *
         * @param currentStep 当前步长
         * @return 下一个步长
         */
        public abstract int nextStep(int currentStep);
    }

    /**
     * 步长序列
     */
    private final Sequence sequence;

    /**
     * 构造方法
     *
     * @param sequence 步长序列
     */
    public ShellSort(Sequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public void sort(T[] array) {
        int length = array.length;
        int step = this.sequence.initStep(length);
        while (1 <= step) {
            // 从第二个元素开始
            for (int i = 1; i < length; i++) {
                // 从第 i 个元素开始，依次和前面 i - step 个元素比较，如果小于，就交换位置
                for (int j = i; j >= step; j -= step) {
                    if (0 > array[j].compareTo(array[j - step])) {
                        this.swap(array, j, j - step);
                    } else {
                        break;
                    }
                }
            }
            step = this.sequence.nextStep(step);
        }
    }
}
