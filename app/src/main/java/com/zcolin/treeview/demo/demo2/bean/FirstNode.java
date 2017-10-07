package com.zcolin.treeview.demo.demo2.bean;

import com.zcolin.treeview.LayoutItemType;
import com.zcolin.treeview.Node;
import com.zcolin.treeview.demo.R;

public class FirstNode implements Node {
    public String id;
    public String nodeName;
    public int validCount;
    public int totalCount;

    public FirstNode(String nodeName, String id) {
        this.nodeName = nodeName;
        this.id = id;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_firstnode;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}
