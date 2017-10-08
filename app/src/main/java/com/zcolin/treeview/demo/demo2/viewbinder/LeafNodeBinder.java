package com.zcolin.treeview.demo.demo2.viewbinder;

import android.view.View;
import android.widget.TextView;

import com.zcolin.treeview.TreeNode;
import com.zcolin.treeview.TreeViewBinder;
import com.zcolin.treeview.demo.R;
import com.zcolin.treeview.demo.demo2.bean.LeafNode;

public class LeafNodeBinder extends TreeViewBinder<LeafNodeBinder.ViewHolder> {
    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(ViewHolder holder, int position, TreeNode node) {
        LeafNode fileNode = (LeafNode) node.content;
        holder.tvName.setText(fileNode.nodeName);
        holder.tvName.setSelected(fileNode.isChecked);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_leafnode;
    }

    public class ViewHolder extends TreeViewBinder.ViewHolder {
        public TextView tvName;

        public ViewHolder(View rootView) {
            super(rootView);
            tvName = (TextView) rootView.findViewById(R.id.tv_name);
        }
    }
}
