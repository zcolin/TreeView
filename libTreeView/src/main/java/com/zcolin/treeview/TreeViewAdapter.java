/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-1-9 下午2:34
 * ********************************************************
 */

package com.zcolin.treeview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TreeViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String KEY_IS_EXPAND = "IS_EXPAND";

    private final List<? extends TreeViewBinder> viewBinders;
    private       int                            padding;
    private       List<TreeNode>                 displayNodes;
    private       OnTreeNodeListener             onTreeNodeListener;
    private       boolean                        toCollapseChild;

    public TreeViewAdapter(List<? extends TreeViewBinder> viewBinders) {
        this(null, viewBinders);
    }

    public TreeViewAdapter(List<TreeNode> nodes, List<? extends TreeViewBinder> viewBinders) {
        displayNodes = new ArrayList<>();
        if (nodes != null)
            findDisplayNodes(nodes);
        this.viewBinders = viewBinders;
    }

    /**
     * 从nodes的结点中寻找展开了的非叶结点，添加到displayNodes中。
     *
     * @param nodes 基准点
     */
    private void findDisplayNodes(List<TreeNode> nodes) {
        for (TreeNode node : nodes) {
            displayNodes.add(node);
            if (!node.isLeaf() && node.isExpand)
                findDisplayNodes(node.childList);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return displayNodes.get(position).content.getLayoutId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        if (viewBinders.size() == 1)
            return viewBinders.get(0).provideViewHolder(v);
        for (TreeViewBinder viewBinder : viewBinders) {
            if (viewBinder.getLayoutId() == viewType)
                return viewBinder.provideViewHolder(v);
        }
        return viewBinders.get(0).provideViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (payloads != null && !payloads.isEmpty()) {
            Bundle b = (Bundle) payloads.get(0);
            for (String key : b.keySet()) {
                switch (key) {
                    case KEY_IS_EXPAND:
                        if (onTreeNodeListener != null)
                            onTreeNodeListener.onToggle(b.getBoolean(key), holder);
                        break;
                }
            }
        }
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (padding == 0) {
            padding = dip2px(holder.itemView.getContext(), 20);
        }
        holder.itemView.setPadding(displayNodes.get(position).getDepth() * padding, 3, 3, 3);
        holder.itemView.setOnClickListener(v -> {
            TreeNode selectedNode = displayNodes.get(holder.getLayoutPosition());
            // Prevent multi-click during the short interval.
            try {
                long lastClickTime = (long) holder.itemView.getTag();
                if (System.currentTimeMillis() - lastClickTime < 500)
                    return;
            } catch (Exception e) {
                holder.itemView.setTag(System.currentTimeMillis());
            }
            holder.itemView.setTag(System.currentTimeMillis());

            if (onTreeNodeListener != null && onTreeNodeListener.onClick(selectedNode, holder))
                return;
            if (selectedNode.isLeaf())
                return;
            toggleTreeNode(selectedNode);
        });
        for (TreeViewBinder viewBinder : viewBinders) {
            if (viewBinder.getLayoutId() == displayNodes.get(position).content.getLayoutId())
                viewBinder.bindView(holder, position, displayNodes.get(position));
        }
    }

    public void toggleTreeNode(TreeNode selectedNode) {
        boolean isExpand = selectedNode.isExpand;
        int positionStart = displayNodes.indexOf(selectedNode) + 1;
        if (!isExpand) {
            notifyItemRangeInserted(positionStart, addChildNodes(selectedNode, positionStart));
        } else {
            notifyItemRangeRemoved(positionStart, removeChildNodes(selectedNode, true));
        }
    }

    private int addChildNodes(TreeNode pNode, int startIndex) {
        List<TreeNode> childList = pNode.childList;
        int addChildCount = 0;
        for (TreeNode treeNode : childList) {
            displayNodes.add(startIndex + addChildCount++, treeNode);
            if (treeNode.isExpand) {
                addChildCount += addChildNodes(treeNode, startIndex + addChildCount);
            }
        }
        if (!pNode.isExpand)
            pNode.toggle();
        return addChildCount;
    }

    private int removeChildNodes(TreeNode pNode) {
        return removeChildNodes(pNode, true);
    }

    private int removeChildNodes(TreeNode pNode, boolean shouldToggle) {
        if (pNode.isLeaf())
            return 0;
        List<TreeNode> childList = pNode.childList;
        int removeChildCount = childList.size();
        displayNodes.removeAll(childList);
        for (TreeNode child : childList) {
            if (child.isExpand) {
                if (toCollapseChild)
                    child.toggle();
                removeChildCount += removeChildNodes(child, false);
            }
        }
        if (shouldToggle)
            pNode.toggle();
        return removeChildCount;
    }

    @Override
    public int getItemCount() {
        return displayNodes == null ? 0 : displayNodes.size();
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public void ifCollapseChildWhileCollapseParent(boolean toCollapseChild) {
        this.toCollapseChild = toCollapseChild;
    }

    public void setOnTreeNodeListener(OnTreeNodeListener onTreeNodeListener) {
        this.onTreeNodeListener = onTreeNodeListener;
    }

    public interface OnTreeNodeListener {
        /**
         * called when TreeNodes were clicked.
         *
         * @return weather consume the click event.
         */
        boolean onClick(TreeNode node, RecyclerView.ViewHolder holder);

        /**
         * called when TreeNodes were toggle.
         *
         * @param isExpand the status of TreeNodes after being toggled.
         */
        void onToggle(boolean isExpand, RecyclerView.ViewHolder holder);
    }

    /**
     * 增加子节点数据
     */
    public void addNodes(TreeNode pNode, int startIndex) {
        notifyItemRangeInserted(startIndex, addChildNodes(pNode, startIndex));
    }

    /**
     * 更新数据源
     */
    public void refresh(List<TreeNode> treeNodes) {
        displayNodes.clear();
        findDisplayNodes(treeNodes);
        notifyDataSetChanged();
    }

    public Iterator<TreeNode> getDisplayNodesIterator() {
        return displayNodes.iterator();
    }

    private void notifyDiff(final List<TreeNode> temp) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return temp.size();
            }

            @Override
            public int getNewListSize() {
                return displayNodes.size();
            }

            // judge if the same items
            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return TreeViewAdapter.this.areItemsTheSame(temp.get(oldItemPosition), displayNodes.get(newItemPosition));
            }

            // if they are the same items, whether the contents has bean changed.
            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return TreeViewAdapter.this.areContentsTheSame(temp.get(oldItemPosition), displayNodes.get(newItemPosition));
            }

            @Nullable
            @Override
            public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                return TreeViewAdapter.this.getChangePayload(temp.get(oldItemPosition), displayNodes.get(newItemPosition));
            }
        });
        diffResult.dispatchUpdatesTo(this);
    }

    private Object getChangePayload(TreeNode oldNode, TreeNode newNode) {
        Bundle diffBundle = new Bundle();
        if (newNode.isExpand != oldNode.isExpand) {
            diffBundle.putBoolean(KEY_IS_EXPAND, newNode.isExpand);
        }
        if (diffBundle.size() == 0)
            return null;
        return diffBundle;
    }

    // For DiffUtil, if they are the same items, whether the contents has bean changed.
    private boolean areContentsTheSame(TreeNode oldNode, TreeNode newNode) {
        return oldNode.content != null && oldNode.content.equals(newNode.content) && oldNode.isExpand == newNode.isExpand;
    }

    // judge if the same item for DiffUtil
    private boolean areItemsTheSame(TreeNode oldNode, TreeNode newNode) {
        return oldNode.content != null && oldNode.content.equals(newNode.content);
    }

    /**
     * collapse all root nodes.
     */
    public void collapseAll() {
        // Back up the nodes are displaying.
        List<TreeNode> temp = backupDisplayNodes();
        //find all root nodes.
        List<TreeNode> roots = new ArrayList<>();
        for (TreeNode displayNode : displayNodes) {
            if (displayNode.isRoot())
                roots.add(displayNode);
        }
        //Close all root nodes.
        for (TreeNode root : roots) {
            if (root.isExpand)
                removeChildNodes(root);
        }
        notifyDiff(temp);
    }

    @NonNull
    private List<TreeNode> backupDisplayNodes() {
        List<TreeNode> temp = new ArrayList<>();
        for (TreeNode displayNode : displayNodes) {
            try {
                temp.add(displayNode.clone());
            } catch (CloneNotSupportedException e) {
                temp.add(displayNode);
            }
        }
        return temp;
    }

    public void collapseNode(TreeNode pNode) {
        List<TreeNode> temp = backupDisplayNodes();
        removeChildNodes(pNode);
        notifyDiff(temp);
    }

    public void collapseBrotherNode(TreeNode pNode) {
        List<TreeNode> temp = backupDisplayNodes();
        if (pNode.isRoot()) {
            List<TreeNode> roots = new ArrayList<>();
            for (TreeNode displayNode : displayNodes) {
                if (displayNode.isRoot())
                    roots.add(displayNode);
            }
            //Close all root nodes.
            for (TreeNode root : roots) {
                if (root.isExpand && !root.equals(pNode))
                    removeChildNodes(root);
            }
        } else {
            TreeNode parent = pNode.parent;
            if (parent == null)
                return;
            List<TreeNode> childList = parent.childList;
            for (TreeNode node : childList) {
                if (node.equals(pNode) || !node.isExpand)
                    continue;
                removeChildNodes(node);
            }
        }
        notifyDiff(temp);
    }


    private static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}