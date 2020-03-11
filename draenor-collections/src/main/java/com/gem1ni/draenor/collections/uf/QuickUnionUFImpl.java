package com.gem1ni.draenor.collections.uf;

/**
 * QuickUnionUFImpl
 *
 * @author Gem1ni
 * @date 2020/3/11 8:03
 * @apiNote 快速连接并查集实现
 */
public class QuickUnionUFImpl extends UnionFind {

    /**
     * 构造一个大小为 size 的并查集
     *
     * @param size 并查集大小
     */
    public QuickUnionUFImpl(int size) {
        super(size);
    }

    @Override
    public int find(int p) {
        while (p != this.parent[p]) {
            p = this.parent[p];
        }
        return p;
    }

    @Override
    public void union(int p, int q) {
        int pRoot = this.find(p);
        int qRoot = this.find(q);

        if (pRoot != qRoot) {
            this.parent[pRoot] = qRoot;
        }
    }
}
