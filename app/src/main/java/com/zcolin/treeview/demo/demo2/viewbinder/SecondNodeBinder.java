package com.zcolin.treeview.demo.demo2.viewbinder;

import android.view.View;
import android.widget.TextView;

import com.zcolin.treeview.TreeNode;
import com.zcolin.treeview.TreeViewBinder;
import com.zcolin.treeview.demo.R;
import com.zcolin.treeview.demo.demo2.bean.SecondNode;

public class SecondNodeBinder extends TreeViewBinder<SecondNodeBinder.ViewHolder> {
    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(ViewHolder holder, int position, TreeNode node) {
        SecondNode dirNode = (SecondNode) node.content;
        holder.tvName.setText(dirNode.nodeName);
        holder.tvCount.setText(dirNode.validCount + "/" + dirNode.totalCount);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_secondnode;
    }

    public static class ViewHolder extends TreeViewBinder.ViewHolder {
        public TextView tvName;
        public TextView tvCount;

        public ViewHolder(View rootView) {
            super(rootView);
            tvName = (TextView) rootView.findViewById(R.id.tv_name);
            tvCount = (TextView) rootView.findViewById(R.id.tv_count);

        }
    }
}
