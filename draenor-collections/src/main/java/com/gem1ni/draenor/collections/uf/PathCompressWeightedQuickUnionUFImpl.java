package com.gem1ni.draenor.collections.uf;

/**
 * PathCompressWeightedQuickUnionUFImpl
 *
 * @author Gem1ni
 * @date 2020/3/11 8:23
 * @apiNote 路径压缩加权快速连接并查集实现
 */
public class PathCompressWeightedQuickUnionUFImpl extends WeightedQuickUnionUFImpl {

    /**
     * 构造一个大小为 size 的并查集
     *
     * @param size 并查集大小
     */
    public PathCompressWeightedQuickUnionUFImpl(int size) {
        super(size);
    }

    @Override
    public int find(int p) {
        // 找根节点
        int root = super.find(p);
        int tmp;
        while (p != root) {
            // tmp 暂存 p 的父节点
            tmp = this.parent[p];
            // p 的父节点指向根节点
            this.parent[p] = root;
            // 将原 p 的父节点值赋值给 p 进行下一次循环
            p = tmp;
        }
        return root;
    }
}
