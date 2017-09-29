package com.zcolin.treeview.demo.demo2.bean;

import com.zcolin.treeview.LayoutItemType;
import com.zcolin.treeview.demo.R;

public class SecondNode implements LayoutItemType {
    public String id;
    public String nodeName;
    public int    validCount;
    public int    totalCount;

    public SecondNode(String nodeName, String id) {
        this.nodeName = nodeName;
        this.id = id;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_secondnode;
    }
}
