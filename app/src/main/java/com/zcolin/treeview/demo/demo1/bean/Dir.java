/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-1-9 下午2:34
 * ********************************************************
 */

package com.zcolin.treeview.demo.demo1.bean;

import com.zcolin.treeview.Node;
import com.zcolin.treeview.demo.R;

public class Dir implements Node {
    public String dirName;

    public Dir(String dirName) {
        this.dirName = dirName;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_dir;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}
