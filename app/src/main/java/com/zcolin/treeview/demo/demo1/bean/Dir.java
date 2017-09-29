package com.zcolin.treeview.demo.demo1.bean;

import com.zcolin.treeview.LayoutItemType;
import com.zcolin.treeview.demo.R;

public class Dir implements LayoutItemType {
    public String dirName;

    public Dir(String dirName) {
        this.dirName = dirName;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_dir;
    }
}
