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
