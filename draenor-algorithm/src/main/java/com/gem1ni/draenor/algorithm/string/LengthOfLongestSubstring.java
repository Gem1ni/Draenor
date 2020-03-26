package com.gem1ni.draenor.algorithm.string;

import java.util.HashMap;
import java.util.Map;

/**
 * LengthOfLongestSubstring
 *
 * @author Gem1ni
 * @date 2020/3/26 11:37
 * @apiNote 最长子串长度
 */
public class LengthOfLongestSubstring {

    /**
     * 获取不重复子串最大长度
     *
     * @param str 一个字符串
     * @return 不重复子串最大长度
     */
    public static int getLength(String str) {
        if (null == str || 0 == str.length()) {
            return 0;
        }
        int maxLength = 0;
        int length = 0;
        Map<Character, Integer> map = new HashMap<>(16);
        for (int i = 0; i < str.length(); i++) {
            Character c = str.charAt(i);
            if (map.containsKey(c)) {
                length = Math.max(length, (map.get(c) + 1));
            }
            map.put(c, i);
            maxLength = Math.max(maxLength, i - length + 1);
        }
        return maxLength;
    }
}
