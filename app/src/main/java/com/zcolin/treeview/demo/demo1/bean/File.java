package com.zcolin.treeview.demo.demo1.bean;

import com.zcolin.treeview.LayoutItemType;
import com.zcolin.treeview.Node;
import com.zcolin.treeview.demo.R;

public class File implements Node {
    public String fileName;

    public File(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_file;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }
}
