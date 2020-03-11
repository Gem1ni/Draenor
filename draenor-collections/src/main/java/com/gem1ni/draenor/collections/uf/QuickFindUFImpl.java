package com.gem1ni.draenor.collections.uf;

/**
 * QuickFindUFImpl
 *
 * @author Gem1ni
 * @date 2020/3/11 7:55
 * @apiNote 快速查找并查集实现
 */
public class QuickFindUFImpl extends UnionFind {

    /**
     * 构造一个大小为 size 的并查集
     *
     * @param size 并查集大小
     */
    public QuickFindUFImpl(int size) {
        super(size);
    }

    @Override
    public int find(int p) {
        return super.parent[p];
    }

    @Override
    public void union(int p, int q) {
        int pId = this.find(p);
        int qId = this.find(q);
        if (pId == qId) {
            return;
        }
        for (int i = 0; i < parent.length; i++) {
            if (parent[i] == pId) {
                parent[i] = qId;
            }
        }
    }
}
