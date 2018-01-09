/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-1-9 下午2:34
 * ********************************************************
 */

package com.zcolin.treeview.demo.demo2.viewbinder;

import android.view.View;
import android.widget.TextView;

import com.zcolin.treeview.TreeNode;
import com.zcolin.treeview.TreeViewBinder;
import com.zcolin.treeview.demo.R;
import com.zcolin.treeview.demo.demo2.bean.FirstNode;

public class FirstNodeBinder extends TreeViewBinder<FirstNodeBinder.ViewHolder> {
    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(ViewHolder holder, int position, TreeNode node) {
        FirstNode dirNode = (FirstNode) node.content;
        holder.tvName.setText(dirNode.nodeName);
        holder.tvCount.setText(dirNode.validCount + "/" + dirNode.totalCount);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_firstnode;
    }

    public static class ViewHolder extends TreeViewBinder.ViewHolder {
        public TextView tvName;
        public TextView tvCount;

        public ViewHolder(View rootView) {
            super(rootView);
            tvName = rootView.findViewById(R.id.tv_name);
            tvCount = rootView.findViewById(R.id.tv_count);

        }
    }
}
