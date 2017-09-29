package com.zcolin.treeview.demo.demo2.bean;

import com.zcolin.treeview.LayoutItemType;
import com.zcolin.treeview.demo.R;

public class LeafNode implements LayoutItemType {
    public String id;
    public String nodeName;
    public boolean isChecked;

    public LeafNode(String nodeName, String id) {
        this.nodeName = nodeName;
        this.id = id;
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.item_leafnode;
    }
}
