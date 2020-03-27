package com.gem1ni.draenor.algorithm.med;

/**
 * LevenshteinDistanceImpl
 *
 * @author Gem1ni
 * @date 2020/3/26 5:46
 * @apiNote Levenshtein Distance 算法实现
 */
public class LevenshteinDistanceImpl implements MinimumEditDistance {

    @Override
    public int distanceBetween(String from, String to) {
        // 边界值判断
        if (MinimumEditDistance.isEmpty(from) && MinimumEditDistance.isEmpty(to)) {
            return 0;
        }
        int fl = from.length();
        int tl = to.length();
        if (MinimumEditDistance.isEmpty(from)) {
            return tl;
        }
        if (MinimumEditDistance.isEmpty(to)) {
            return fl;
        }
        // 初始化矩阵
        int[][] matrix = new int[fl + 1][tl + 1];
        for (int i = 0; i <= fl; i++) {
            matrix[i][0] = i;
        }
        for (int j = 0; j <= tl; j++) {
            matrix[0][j] = j;
        }
        // 计算距离
        for (int i = 1; i <= fl; i++) {
            for (int j = 1; j <= tl; j++) {
                matrix[i][j] = from.charAt(i - 1) == to.charAt(j - 1)
                        ? matrix[i - 1][j - 1] : matrix[i - 1][j - 1] + 1;
                matrix[i][j] = Math.min(matrix[i][j],
                        Math.min(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1));
            }
        }
        return matrix[fl][tl];
    }
}
