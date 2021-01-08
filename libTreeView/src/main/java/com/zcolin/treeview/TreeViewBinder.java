/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-1-9 下午2:34
 * ********************************************************
 */

package com.zcolin.treeview;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;


public abstract class TreeViewBinder<VH extends RecyclerView.ViewHolder> implements LayoutItemType {
    public abstract VH provideViewHolder(View itemView);

    public abstract void bindView(VH holder, int position, TreeNode node);

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View rootView) {
            super(rootView);
        }

        protected <T extends View> T findViewById(@IdRes int id) {
            return (T) itemView.findViewById(id);
        }
    }

}