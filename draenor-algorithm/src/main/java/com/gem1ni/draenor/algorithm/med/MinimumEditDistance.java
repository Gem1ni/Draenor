package com.gem1ni.draenor.algorithm.med;

/**
 * MinimumEditDistance
 *
 * @author Gem1ni
 * @date 2020/3/26 5:42
 * @apiNote 最短编辑距离
 */
public interface MinimumEditDistance {

    /**
     * 求两个字符串的最短编辑距离
     *
     * @param from 一个字符串
     * @param to   另一个字符串
     * @return 最短编辑距离
     * @apiNote 只允许三种操作（插入一个字符，删除一个字符，替换一个字符）
     */
    int distanceBetween(String from, String to);

    /**
     * 字符串是否为空
     *
     * @param str 字符串
     * @return 是否为空
     */
    static boolean isEmpty(String str) {
        return null == str || str.isEmpty();
    }
}
