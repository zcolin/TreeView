/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-1-9 下午2:34
 * ********************************************************
 */

package com.zcolin.treeview.demo.demo2.bean;

import com.zcolin.treeview.Node;
import com.zcolin.treeview.demo.R;

public class LeafNode implements Node {
    public String  id;
    public String  nodeName;
    public boolean isChecked;

    public LeafNode(String nodeName, String id) {
        this.nodeName = nodeName;
        this.id = id;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_leafnode;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }
}
