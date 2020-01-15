package com.gem1ni.draenor.collections.tree;

import java.util.*;
import java.util.stream.Stream;

import static com.gem1ni.draenor.collections.tree.RedBlackTreeNode.BLACK;
import static com.gem1ni.draenor.collections.tree.RedBlackTreeNode.RED;

/**
 * RedBlackTree
 * 红黑树
 * - 继承 {@link java.util.AbstractSet} 是因为我觉得树节点中不能让重复节点出现
 * - 暂且称本类为 TreeMap 注释加人话版
 *
 * @author Gem1ni
 */
public class RedBlackTree<Node> extends AbstractSet<Node> {

    /**
     * 树的根节点
     */
    private RedBlackTreeNode<Node> root;

    /**
     * 树的节点数量
     */
    private int size = 0;

    /**
     * 比较器
     */
    private final Comparator<? super Node> comparator;

    /**
     * 构造方法
     *
     * @param comparator 决定内部排序规则
     */
    public RedBlackTree(Comparator<? super Node> comparator) {
        if (null == comparator) {
            throw new NullPointerException("比较器不能为空");
        }
        this.comparator = comparator;
    }

    @Override
    public Iterator<Node> iterator() {
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(Node node) {
        if (null == node) {
            throw new NullPointerException("要新增的节点不能为空");
        }
        RedBlackTreeNode<Node> tmp = this.root;
        // 如果根节点是空
        if (null == tmp) {
            this.root = new RedBlackTreeNode<>(node, null)
                    // 根节点永远为黑色
                    .setColor(BLACK);
            this.size = 1;
            return true;
        }
        // 比较的结果
        int cmp;
        // 父节点
        RedBlackTreeNode<Node> parent;
        // do-while 至少执行一次
        do {
            // 将临时节点赋值为父节点
            parent = tmp;
            // 要插入的节点与临时节点进行比较
            cmp = this.comparator.compare(node, tmp.getValue());
            if (0 > cmp) {
                // 将临时节点赋值为其左子节点
                tmp = tmp.getLeft();
            } else if (0 < cmp) {
                // 将临时节点赋值为其右子节点
                tmp = tmp.getRight();
            } else {
                // 插入的值等于当前节点的值，不作变更
                return false;
            }
        } while (tmp != null);
        RedBlackTreeNode<Node> n = new RedBlackTreeNode<>(node, parent);
        if (0 > cmp) {
            // 作为父节点的左子节点
            parent.setLeft(n);
        } else {
            // 反之作为右子节点
            parent.setRight(n);
        }
        this.fixAfterAdd(n);
        this.size++;
        return true;
    }

    /**
     * 新增后自平衡
     *
     * @param node 新增的节点
     */
    private void fixAfterAdd(RedBlackTreeNode<Node> node) {
        // 新增的节点默认为红色
        setColorRed(node);
        // 当前处理的节点不为空，且当前处理的节点不是根节点，且当前处理节点的父节点是红色
        while (null != node && !node.isRoot() && RED == colorOf(parentOf(node))) {
            // 如果当前处理节点的父节点是祖父节点的左子节点
            if (parentOf(node) == leftOf(parentOf(parentOf(node)))) {
                // 父节点的父节点的右子节点（伯伯节点）
                RedBlackTreeNode<Node> tmp = rightOf(parentOf(parentOf(node)));
                if (colorOf(tmp) == RED) {
                    // 1.伯伯节点为红色【只需要变颜色】
                    // 当前节点的父节点和伯伯节点变为黑色
                    setColorBlack(parentOf(node), tmp);
                    // 祖父节点变为红色
                    setColorRed(parentOf(parentOf(node)));
                    // 将当前处理节点变为祖父节点继续处理
                    node = parentOf(parentOf(node));
                } else {
                    // 2.伯伯节点为黑色
                    // 当前节点是父节点的右子节点
                    if (node.equals(rightOf(parentOf(node)))) {
                        // 将当前处理节点变为父节点继续处理
                        node = parentOf(node);
                        // 左旋
                        this.rotateLeft(node);
                    }
                    // 将当前处理节点的父节点变为黑色
                    setColorBlack(parentOf(node));
                    // 将当前处理节点的祖父节点变为红色
                    setColorRed(parentOf(parentOf(node)));
                }
                // 当前处理节点的父节点是祖父节点的右子节点
            } else {
                // 叔叔节点
                RedBlackTreeNode<Node> tmp = leftOf(parentOf(parentOf(node)));
                // 3.叔叔节点为红色【只需要变颜色】
                if (RED == colorOf(tmp)) {
                    // 当前节点的父节点和伯伯节点变为黑色
                    setColorBlack(parentOf(node), tmp);
                    // 祖父节点变为红色
                    setColorRed(parentOf(parentOf(node)));
                    // 将当前处理节点变为祖父节点继续处理
                    node = parentOf(parentOf(node));
                } else {
                    // 当前处理的节点是父节点的左子节点
                    if (node.equals(leftOf(parentOf(node)))) {
                        // 将当前处理节点变为父节点继续处理
                        node = parentOf(node);
                        // 右旋
                        this.rotateRight(node);
                    }
                    // 将当前处理节点的父节点变为黑色
                    setColorBlack(parentOf(node));
                    // 将当前处理节点的祖父节点变为红色
                    setColorRed(parentOf(parentOf(node)));
                    // 左旋
                    this.rotateLeft(parentOf(parentOf(node)));
                }
            }
        }
        // 根节点恒为黑色
        setColorBlack(this.root);
    }

    /**
     * 获取父节点
     *
     * @param node 树节点
     * @return 父节点
     */
    private static <Node> RedBlackTreeNode<Node> parentOf(RedBlackTreeNode<Node> node) {
        return null == node ? null : node.getParent();
    }

    /**
     * 获取左子节点
     *
     * @param node 树节点
     * @return 左子节点
     */
    private static <Node> RedBlackTreeNode<Node> leftOf(RedBlackTreeNode<Node> node) {
        return null == node ? null : node.getLeft();
    }

    /**
     * 获取右子节点
     *
     * @param node 树节点
     * @return 右子节点
     */
    private static <Node> RedBlackTreeNode<Node> rightOf(RedBlackTreeNode<Node> node) {
        return null == node ? null : node.getRight();
    }

    /**
     * 获取节点颜色
     *
     * @param node   树节点
     * @param <Node> 节点值泛型
     * @return 颜色
     */
    private static <Node> boolean colorOf(RedBlackTreeNode<Node> node) {
        return null == node ? BLACK :
                null == node.getColor() ? BLACK : node.getColor();
    }

    /**
     * 【变色】将节点置为黑色
     *
     * @param node 树节点
     */
    @SafeVarargs
    private static <Node> void setColorBlack(RedBlackTreeNode<Node>... node) {
        if (null != node) {
            Stream.of(node)
                    .filter(Objects::nonNull)
                    .forEach(n -> n.setColor(BLACK));
        }
    }

    /**
     * 【变色】将节点置为红色
     *
     * @param node   树节点
     * @param <Node> 节点值泛型
     */
    @SafeVarargs
    private static <Node> void setColorRed(RedBlackTreeNode<Node>... node) {
        if (null != node) {
            Stream.of(node)
                    .filter(Objects::nonNull)
                    .forEach(n -> n.setColor(RED));
        }
    }

    /**
     * 【旋转】右旋
     *
     * @param node 被操作节点
     * @apiNote 顺时针旋转
     */
    private void rotateRight(RedBlackTreeNode<Node> node) {
        if (null != node) {
            // 取出当前节点（node）的左子节点
            RedBlackTreeNode<Node> left = node.getLeft();
            // 将左子节点的右子节点置为当前节点（node）的左节点
            RedBlackTreeNode<Node> leftRight = left.getRight();
            node.setLeft(leftRight);
            // 如果左子节点的右子节点不为空
            if (null != leftRight) {
                // 将这个节点的父节点变更为当前节点（node）
                leftRight.setParent(node);
            }
            // 将当前节点（node）的父节点置为 left 的父节点
            left.setParent(node.getParent());
            if (node.isRoot()) {
                // 如果当前节点是根节点，则 left 成为新的根节点
                this.root = left;
            } else if (node.isRight()) {
                // 如果当前节点是父节点的右子节点，则 left 取代 node 成为 nodeParent 的右子节点
                node.getParent().setRight(left);
            } else {
                // 反之 left 取代 node 成为 nodeParent 的左子节点
                node.getParent().setLeft(left);
            }
            // 当前节点（node）成为原左子节点的右子节点
            left.setRight(node);
            node.setParent(left);
        }
    }

    /**
     * 【旋转】左旋
     *
     * @param node 被操作节点
     */
    private void rotateLeft(RedBlackTreeNode<Node> node) {
        if (null != node) {
            // 取出当前节点的右子节点 right
            RedBlackTreeNode<Node> right = node.getRight();
            // 将当前节点（node）的右子节点替换为 rightLeft
            RedBlackTreeNode<Node> rightLeft = right.getLeft();
            node.setRight(rightLeft);
            if (null != rightLeft) {
                // 如果 rightLeft 不为空，则将其父节点设置为 node
                rightLeft.setParent(node);
            }
            // 将 right 的父节点变更为 node 的父节点
            right.setParent(node.getParent());
            if (node.isRoot()) {
                // 如果当前节点是根节点，则 right 成为新的根节点
                this.root = right;
            } else if (node.isLeft()) {
                // 如果当前节点是父节点的左子节点，则 right 取代 node 成为 nodeParent 的左子节点
                node.getParent().setLeft(right);
            } else {
                // 反之 right 取代 node 成为 nodeParent 的右子节点
                node.getParent().setRight(right);
            }
            // 当前节点（node）成为原右子节点的左子节点
            right.setLeft(node);
            node.setParent(right);
        }
    }
}
