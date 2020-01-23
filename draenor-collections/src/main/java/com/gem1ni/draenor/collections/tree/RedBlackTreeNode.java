package com.gem1ni.draenor.collections.tree;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * RedBlackTreeNode
 * 红黑树节点
 * 定义了红黑树节点的特有属性
 * - 节点颜色，红和黑
 *
 * @author Gem1ni
 */
@Getter
@Setter
@Accessors(chain = true)
class RedBlackTreeNode<Val> {

    /**
     * 红色
     */
    public static final boolean RED = false;

    /**
     * 黑色
     */
    public static final boolean BLACK = true;

    /**
     * 默认构造方法
     */
    private RedBlackTreeNode() {
        this.color = RED;
    }

    /**
     * 构造方法
     */
    public RedBlackTreeNode(Val value, RedBlackTreeNode<Val> parent) {
        this();
        this.value = value;
        this.parent = parent;
    }

    /**
     * 当前节点颜色
     */
    private Boolean color;

    /**
     * 当前节点值
     */
    private Val value;

    /**
     * 父节点
     */
    private RedBlackTreeNode<Val> parent;

    /**
     * 左子节点
     */
    private RedBlackTreeNode<Val> left;

    /**
     * 右子节点
     */
    private RedBlackTreeNode<Val> right;

    /**
     * 是否是根节点
     *
     * @return 是否是根节点
     */
    public boolean isRoot() {
        return null == this.parent;
    }

    /**
     * 是否是父节点在左子节点
     *
     * @return 是否是父节点的左子节点
     */
    public boolean isLeft() {
        RedBlackTreeNode<Val> leftOfParent = this.getParent().getLeft();
        return !this.isRoot()
                && null != leftOfParent
                && leftOfParent.equals(this);
    }

    /**
     * 是否是父节点的右子节点
     *
     * @return 是否是父节点的右子节点
     */
    public boolean isRight() {
        RedBlackTreeNode<Val> rightOfParent = this.getParent().getRight();
        return !this.isRoot()
                && null != rightOfParent
                && rightOfParent.equals(this);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.value);
    }

    @Override
    public boolean equals(Object obj) {
        // 强调：节点的对比比的是内存地址，不是比值
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "[value]: " + this.value +
                " [color]: " + ((null == this.color || !this.color) ? "black" : "red") +
                " [parent]: " + (null == this.parent ? null : this.parent.getValue()) +
                " [left]: " + (null == this.left ? null : this.left.getValue()) +
                " [right]: " + (null == this.right ? null : this.right.getValue());
    }
}
