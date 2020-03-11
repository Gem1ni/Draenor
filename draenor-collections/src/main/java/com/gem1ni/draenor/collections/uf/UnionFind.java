package com.gem1ni.draenor.collections.uf;

/**
 * UnionFind
 *
 * @author Gem1ni
 * @date 2020/3/11 7:38
 * @apiNote 并查集
 */
public abstract class UnionFind {

    /**
     * 集合中的节点所连接的节点
     */
    protected int[] parent;

    /**
     * 构造一个大小为 size 的并查集
     *
     * @param size 并查集大小
     */
    public UnionFind(int size) {
        this.parent = new int[size];
        for (int i = 0; i < size; i++) {
            this.parent[i] = i;
        }
    }

    /**
     * 判断 p 和 q 节点是否连通
     *
     * @param p 点 p
     * @param q 点 q
     * @return 是否连通
     */
    public boolean connected(int p, int q) {
        return this.find(p) == this.find(q);
    }

    /**
     * 查找 p 点的连通分量编号
     *
     * @param p 点 p
     * @return 连通分量编号，即 p 点跟连接到的那个点
     */
    public abstract int find(int p);

    /**
     * 连接 p 点和 q 点
     *
     * @param p 集合中的一个点
     * @param q 另一个点
     */
    public abstract void union(int p, int q);
}
