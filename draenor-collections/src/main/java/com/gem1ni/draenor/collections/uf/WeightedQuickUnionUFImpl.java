package com.gem1ni.draenor.collections.uf;

/**
 * WeightedQuickUnionUFImpl
 *
 * @author Gem1ni
 * @date 2020/3/11 8:05
 * @apiNote 加权快速连接并查集实现
 */
public class WeightedQuickUnionUFImpl extends QuickUnionUFImpl {

    /**
     * 权
     */
    protected int[] weight;

    /**
     * 构造一个大小为 size 的并查集
     *
     * @param size 并查集大小
     */
    public WeightedQuickUnionUFImpl(int size) {
        super(size);
        this.weight = new int[size];
        for (int i = 0; i < size; i++) {
            this.weight[i] = 1;
        }
    }

    @Override
    public void union(int p, int q) {
        int i = this.find(p);
        int j = this.find(q);
        if (i == j) {
            return;
        }
        if (this.weight[i] < this.weight[j]) {
            super.parent[i] = j;
            this.weight[j] += this.weight[i];
        } else {
            super.parent[j] = i;
            this.weight[i] += this.weight[j];
        }
    }
}
