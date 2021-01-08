/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-1-9 下午2:34
 * ********************************************************
 */

package com.zcolin.treeview;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T extends Node> implements Cloneable {
    public static final int UNDEFINE = -1;

    /** 内容数据 */
    public  T              content;
    /** 父节点 */
    public  TreeNode       parent;
    /** 子节点 */
    public  List<TreeNode> childList;
    /** 是否展开 */
    public  boolean        isExpand;
    /** 树深度 */
    private int            depth = UNDEFINE;

    public TreeNode(@NonNull T content) {
        this.content = content;
    }

    public int getDepth() {
        if (isRoot()) {
            depth = 0;
        } else if (depth == UNDEFINE) {
            depth = parent.getDepth() + 1;
        }
        return depth;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return content.isLeaf();
    }

    public TreeNode addChild(TreeNode node) {
        if (childList == null) {
            childList = new ArrayList<>();
        }
        childList.add(node);
        node.parent = this;
        return this;
    }

    public boolean toggle() {
        isExpand = !isExpand;
        return isExpand;
    }

    public void collapse() {
        if (!isExpand) {
            isExpand = false;
        }
    }

    public void expand() {
        if (isExpand) {
            isExpand = true;
        }
    }

    @Override
    protected TreeNode<T> clone() throws CloneNotSupportedException {
        TreeNode<T> clone = new TreeNode<>(this.content);
        clone.isExpand = this.isExpand;
        return clone;
    }
}
