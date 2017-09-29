package com.zcolin.treeview.demo.demo1.viewbinder;

import android.view.View;
import android.widget.TextView;

import com.zcolin.treeview.TreeNode;
import com.zcolin.treeview.TreeViewBinder;
import com.zcolin.treeview.demo.R;
import com.zcolin.treeview.demo.demo1.bean.File;

public class FileNodeBinder extends TreeViewBinder<FileNodeBinder.ViewHolder> {
    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(ViewHolder holder, int position, TreeNode node) {
        File fileNode = (File) node.content;
        holder.tvName.setText(fileNode.fileName);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_file;
    }

    public class ViewHolder extends TreeViewBinder.ViewHolder {
        public TextView tvName;

        public ViewHolder(View rootView) {
            super(rootView);
            this.tvName = (TextView) rootView.findViewById(R.id.tv_name);
        }

    }
}
