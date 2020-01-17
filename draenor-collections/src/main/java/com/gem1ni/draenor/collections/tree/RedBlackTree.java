package com.gem1ni.draenor.collections.tree;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

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
public class RedBlackTree<Val> extends AbstractSet<Val> {

    /**
     * 树的根节点
     */
    private RedBlackTreeNode<Val> root;

    /**
     * 树的节点数量
     */
    private int size = 0;

    /**
     * 比较器
     */
    private final Comparator<? super Val> comparator;

    /**
     * 构造方法
     *
     * @param comparator 决定内部排序规则
     */
    public RedBlackTree(Comparator<? super Val> comparator) {
        if (null == comparator) {
            throw new NullPointerException("比较器不能为空");
        }
        this.comparator = comparator;
    }

    @Override
    public Iterator<Val> iterator() {
        return new TreeItr();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends Val> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(Val node) {
        if (null == node) {
            throw new NullPointerException("要新增的节点不能为空");
        }
        RedBlackTreeNode<Val> tmp = this.root;
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
        RedBlackTreeNode<Val> parent;
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
        RedBlackTreeNode<Val> n = new RedBlackTreeNode<>(node, parent);
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
     * @apiNote 注意：这里很多 node 与其他结果的比较用的是比较内存地址，不是比值
     */
    private void fixAfterAdd(RedBlackTreeNode<Val> node) {
        // 新增的节点默认为红色
        setColor(BLACK, node);
        // 当前处理的节点不为空，且当前处理的节点不是根节点，且当前处理节点的父节点是红色
        while (null != node && !node.isRoot() && RED == colorOf(parentOf(node))) {
            // 如果当前处理节点的父节点是祖父节点的左子节点
            if (parentOf(node) == leftOf(parentOf(parentOf(node)))) {
                // 父节点的父节点的右子节点（伯伯节点）
                RedBlackTreeNode<Val> tmp = rightOf(parentOf(parentOf(node)));
                if (colorOf(tmp) == RED) {
                    // 1.伯伯节点为红色【只需要变颜色】
                    // 当前节点的父节点和伯伯节点变为黑色
                    setColor(BLACK, parentOf(node));
                    setColor(BLACK, tmp);
                    // 祖父节点变为红色
                    setColor(BLACK, parentOf(parentOf(node)));
                    // 将当前处理节点变为祖父节点继续处理
                    node = parentOf(parentOf(node));
                } else {
                    // 2.伯伯节点为黑色
                    // 当前节点是父节点的右子节点
                    if (node == rightOf(parentOf(node))) {
                        // 将当前处理节点变为父节点继续处理
                        node = parentOf(node);
                        // 左旋
                        this.rotateLeft(node);
                    }
                    // 将当前处理节点的父节点变为黑色
                    setColor(BLACK, parentOf(node));
                    // 将当前处理节点的祖父节点变为红色
                    setColor(BLACK, parentOf(parentOf(node)));
                }
                // 当前处理节点的父节点是祖父节点的右子节点
            } else {
                // 叔叔节点
                RedBlackTreeNode<Val> tmp = leftOf(parentOf(parentOf(node)));
                // 3.叔叔节点为红色【只需要变颜色】
                if (RED == colorOf(tmp)) {
                    // 当前节点的父节点和伯伯节点变为黑色
                    setColor(BLACK, parentOf(node));
                    setColor(BLACK, tmp);
                    // 祖父节点变为红色
                    setColor(BLACK, parentOf(parentOf(node)));
                    // 将当前处理节点变为祖父节点继续处理
                    node = parentOf(parentOf(node));
                } else {
                    // 4.当前处理的节点是父节点的左子节点
                    if (node == leftOf(parentOf(node))) {
                        // 将当前处理节点变为父节点继续处理
                        node = parentOf(node);
                        // 右旋
                        this.rotateRight(node);
                    }
                    // 将当前处理节点的父节点变为黑色
                    setColor(BLACK, parentOf(node));
                    // 将当前处理节点的祖父节点变为红色
                    setColor(BLACK, parentOf(parentOf(node)));
                    // 左旋
                    this.rotateLeft(parentOf(parentOf(node)));
                }
            }
        }
        // 根节点恒为黑色
        setColor(BLACK, this.root);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(Object o) {
        Val val = (Val) o;
        RedBlackTreeNode<Val> node = this.getNode(val);
        if (null == node) {
            return false;
        }
        this.deleteNode(node);
        return true;
    }

    /**
     * 获取节点
     *
     * @return 红黑树节点
     * @apiNote 利用比较器做二分查找
     */
    private RedBlackTreeNode<Val> getNode(Val value) {
        if (null == value) {
            return null;
        }
        // 比较的结果
        int cmp;
        RedBlackTreeNode<Val> tmp = this.root;
        while (null != tmp) {
            cmp = this.comparator.compare(value, tmp.getValue());
            if (0 > cmp) {
                tmp = tmp.getLeft();
            } else if (0 < cmp) {
                tmp = tmp.getRight();
            } else {
                return tmp;
            }
        }
        return null;
    }

    /**
     * 删除指定节点
     *
     * @param node 树节点
     */
    private void deleteNode(RedBlackTreeNode<Val> node) {
        this.size--;
        // 如果当前处理节点儿女双全
        if (null != node.getLeft() && null != node.getRight()) {
            // 就找它的后继节点
            node = successor(node);
        }
        // 优先取左子节点，否则取右子节点
        RedBlackTreeNode<Val> replacement = null == node.getLeft() ? node.getLeft() : node.getRight();
        if (null != replacement) {
            replacement.setParent(node.getParent());
            if (node.isRoot()) {
                this.root = replacement;
            } else if (node.isLeft()) {
                node.getParent().setLeft(replacement);
            } else {
                node.getParent().setRight(replacement);
            }
            node.setLeft(null).setRight(null).setParent(null);
            if (BLACK == colorOf(node)) {
                this.fixAfterDelete(replacement);
            }
        } else if (node == this.root) {
            // 当前节点或后继节点没有子节点，且 node 是根节点，删除它就是把 root 置空
            this.root = null;
        } else {
            // 当前节点或后继节点没有子节点，且不是根节点
            if (BLACK == colorOf(node)) {
                // 如果节点颜色是黑色，先修正
                this.fixAfterDelete(node);
            }
            // 如果处理后父节点存在，则解除当前节点与父节点的关联
            if (null != node.getParent()) {
                if (node.isLeft()) {
                    node.getParent().setLeft(null);
                } else {
                    node.getParent().setRight(null);
                }
                node.setParent(null);
            }
        }
    }

    /**
     * 删除后自平衡
     *
     * @param node 当前处理节点
     */
    private void fixAfterDelete(RedBlackTreeNode<Val> node) {
        while (this.root != node && BLACK == colorOf(node)) {
            if (node == leftOf(parentOf(node))) {
                // 1. 如果当前处理节点是父节点的左子节点，找哥哥节点
                RedBlackTreeNode<Val> elderBro = rightOf(parentOf(node));
                if (RED == colorOf(elderBro)) {
                    setColor(BLACK, elderBro);
                    setColor(BLACK, parentOf(node));
                    this.rotateLeft(parentOf(node));
                    // 将自旋后的新哥哥节点赋值给变量 elderBro
                    elderBro = rightOf(parentOf(node));
                }
                if (BLACK == colorOf(leftOf(elderBro)) && BLACK == colorOf(rightOf(elderBro))) {
                    // 如果哥哥节点的两个子节点都是黑色，将哥哥节点变为红色
                    setColor(BLACK, elderBro);
                    // 处理当前节点的父节点
                    node = parentOf(node);
                } else {
                    if (BLACK == colorOf(rightOf(elderBro))) {
                        // 如果只有哥哥节点的右节点为黑色
                        setColor(BLACK, rightOf(elderBro));
                        setColor(BLACK, elderBro);
                        this.rotateRight(elderBro);
                        // 继续处理哥哥的哥哥
                        elderBro = rightOf(parentOf(node));
                    }
                    setColor(colorOf(parentOf(node)), elderBro);
                    setColor(BLACK, parentOf(node));
                    setColor(BLACK, rightOf(elderBro));
                    this.rotateLeft(parentOf(node));
                    node = this.root;
                }
            } else {
                // 2. 如果当前处理节点是父节点的右子节点，找弟弟节点
                RedBlackTreeNode<Val> youngerBro = leftOf(parentOf(node));
                if (RED == colorOf(youngerBro)) {
                    setColor(BLACK, youngerBro);
                    setColor(BLACK, parentOf(node));
                    this.rotateRight(parentOf(node));
                    // 将右旋后的新弟弟节点赋值给变量 youngerBro
                    node = leftOf(parentOf(node));
                }
                if (BLACK == colorOf(rightOf(youngerBro)) && BLACK == colorOf(leftOf(youngerBro))) {
                    setColor(BLACK, youngerBro);
                    node = parentOf(node);
                } else {
                    if (BLACK == colorOf(leftOf(youngerBro))) {
                        setColor(BLACK, rightOf(youngerBro));
                        setColor(BLACK, youngerBro);
                        this.rotateLeft(youngerBro);
                        youngerBro = leftOf(parentOf(node));
                    }
                    setColor(colorOf(parentOf(node)), youngerBro);
                    setColor(BLACK, parentOf(node));
                    setColor(BLACK, leftOf(youngerBro));
                    this.rotateRight(parentOf(node));
                    node = this.root;
                }
            }
        }
        setColor(BLACK, node);
    }

    /**
     * 后继节点
     *
     * @param node  指定树节点
     * @param <Val> 节点值泛型
     * @return 后继节点
     * @apiNote 就是找排序后的下一个节点
     */
    private static <Val> RedBlackTreeNode<Val> successor(RedBlackTreeNode<Val> node) {
        if (null == node) {
            return null;
        } else if (null != node.getRight()) {
            // 如果右子节点不为空
            RedBlackTreeNode<Val> tmp = node.getRight();
            while (null != tmp.getLeft()) {
                tmp = tmp.getLeft();
            }
            return tmp;
        } else {
            RedBlackTreeNode<Val> parent = node.getParent();
            RedBlackTreeNode<Val> child = node;
            // 如果当前节点不是根节点且当前点是右节点，则继续往上找
            // 1. 如果当前节点是左子节点，则取父节点
            while (null != parent && child == parent.getRight()) {
                // 2. 如果当前节点是右子节点，则将当前处理节点换为父节点递归处理，直到找到根节点为止
                child = parent;
                parent = parent.getParent();
            }
            return parent;
        }
    }

    /**
     * 获取父节点
     *
     * @param node 树节点
     * @return 父节点
     */
    private static <Val> RedBlackTreeNode<Val> parentOf(RedBlackTreeNode<Val> node) {
        return null == node ? null : node.getParent();
    }

    /**
     * 获取左子节点
     *
     * @param node 树节点
     * @return 左子节点
     */
    private static <Val> RedBlackTreeNode<Val> leftOf(RedBlackTreeNode<Val> node) {
        return null == node ? null : node.getLeft();
    }

    /**
     * 获取右子节点
     *
     * @param node 树节点
     * @return 右子节点
     */
    private static <Val> RedBlackTreeNode<Val> rightOf(RedBlackTreeNode<Val> node) {
        return null == node ? null : node.getRight();
    }

    /**
     * 获取节点颜色
     *
     * @param node  树节点
     * @param <Val> 节点值泛型
     * @return 颜色
     */
    private static <Val> boolean colorOf(RedBlackTreeNode<Val> node) {
        return null == node ? BLACK :
                null == node.getColor() ? BLACK : node.getColor();
    }

    /**
     * 设置节点颜色
     *
     * @param color 颜色
     * @param node  节点
     * @param <Val> 节点值泛型
     */
    private static <Val> void setColor(boolean color, RedBlackTreeNode<Val> node) {
        if (null != node) {
            node.setColor(color);
        }
    }

    /**
     * 【旋转】右旋
     *
     * @param node 被操作节点
     * @apiNote 顺时针旋转
     */
    private void rotateRight(RedBlackTreeNode<Val> node) {
        if (null != node) {
            // 取出当前节点（node）的左子节点
            RedBlackTreeNode<Val> left = node.getLeft();
            // 将左子节点的右子节点置为当前节点（node）的左节点
            RedBlackTreeNode<Val> leftRight = left.getRight();
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
    private void rotateLeft(RedBlackTreeNode<Val> node) {
        if (null != node) {
            // 取出当前节点的右子节点 right
            RedBlackTreeNode<Val> right = node.getRight();
            // 将当前节点（node）的右子节点替换为 rightLeft
            RedBlackTreeNode<Val> rightLeft = right.getLeft();
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

    /**
     * 获取集合中第一个节点
     *
     * @return 节点
     */
    private RedBlackTreeNode<Val> getFirstNode() {
        RedBlackTreeNode<Val> node = this.root;
        for (; null != node && null != node.getLeft(); ) {
            node = node.getLeft();
        }
        return node;
    }

    /**
     * 迭代器
     */
    private class TreeItr implements Iterator<Val> {

        /**
         * 当前遍历的节点
         */
        private RedBlackTreeNode<Val> current;

        /**
         * 下一个节点
         */
        private RedBlackTreeNode<Val> next;

        /**
         * 构造方法
         */
        public TreeItr() {
            this.current = RedBlackTree.this.getFirstNode();
            this.next = successor(this.current);
        }

        @Override
        public boolean hasNext() {
            return null != this.next;
        }

        @Override
        public Val next() {
            this.current = this.next;
            this.next = successor(this.next);
            return this.current.getValue();
        }
    }
}
