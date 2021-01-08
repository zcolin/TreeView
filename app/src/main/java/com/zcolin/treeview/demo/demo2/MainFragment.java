/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-1-9 下午2:34
 * ********************************************************
 */

package com.zcolin.treeview.demo.demo2;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zcolin.treeview.TreeNode;
import com.zcolin.treeview.TreeViewAdapter;
import com.zcolin.treeview.demo.R;
import com.zcolin.treeview.demo.demo2.bean.FirstNode;
import com.zcolin.treeview.demo.demo2.bean.LeafNode;
import com.zcolin.treeview.demo.demo2.bean.SecondNode;
import com.zcolin.treeview.demo.demo2.viewbinder.FirstNodeBinder;
import com.zcolin.treeview.demo.demo2.viewbinder.LeafNodeBinder;
import com.zcolin.treeview.demo.demo2.viewbinder.SecondNodeBinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */

public class MainFragment extends BaseFrameFrag {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView       recyclerView;
    private TreeViewAdapter    adapter;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getRootViewLayId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void createView(@Nullable Bundle savedInstanceState) {
        recyclerView = rootView.findViewById(R.id.recyclerView);
        swipeRefreshLayout = rootView.findViewById(R.id.srl_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout.setOnRefreshListener(() -> {
            initData();
            swipeRefreshLayout.setRefreshing(false);
        });

        initData();
    }

    @Override
    protected void lazyLoad(@Nullable Bundle savedInstanceState) {
    }

    private void initData() {
        adapter = new TreeViewAdapter(requestData(),
                                      Arrays.asList(new LeafNodeBinder(),
                                                    new FirstNodeBinder(),
                                                    new SecondNodeBinder()));
        adapter.setOnTreeNodeListener(new TreeViewAdapter.OnTreeNodeListener() {
            @Override
            public boolean onClick(TreeNode node, RecyclerView.ViewHolder holder) {
                if (!node.isLeaf()) {
                    onToggle(!node.isExpand, holder);
                } else if (node.content instanceof LeafNode) {
                    LeafNodeBinder.ViewHolder leafViewHolder = (LeafNodeBinder.ViewHolder) holder;
                    boolean isChecked = !leafViewHolder.tvName.isSelected();
                    leafViewHolder.tvName.setSelected(isChecked);
                    ((LeafNode) node.content).isChecked = isChecked;
                }
                return false;
            }

            @Override
            public void onToggle(boolean isExpand, RecyclerView.ViewHolder holder) {

            }
        });
        recyclerView.setAdapter(adapter);
    }

    private List<TreeNode> requestData() {
        List<TreeNode> nodes = new ArrayList<>();
        TreeNode<FirstNode> app = new TreeNode<>(new FirstNode("一级节点1", "1"));
        nodes.add(app);
        app.addChild(new TreeNode<>(new SecondNode("二级节点", "21")).addChild(new TreeNode<>(new LeafNode("叶子节点", "91"))));
        app.addChild(new TreeNode<>(new SecondNode("二级节点", "22")).addChild(new TreeNode<>(new SecondNode("三级节点",
                                                                                                         "31")).addChild(
                new TreeNode<>(new SecondNode("四级节点", "41")).addChild(new TreeNode<>(new SecondNode("五级节点",
                                                                                                    "51")).addChild(new TreeNode<>(
                        new LeafNode("叶子节点", "92")))
                                                                                                          .addChild(new TreeNode<>(
                                                                                                                  new LeafNode(
                                                                                                                          "叶子节点",
                                                                                                                          "97")))
                                                                                                          .addChild(new TreeNode<>(
                                                                                                                  new LeafNode(
                                                                                                                          "叶子节点",
                                                                                                                          "98")))
                                                                                                          .addChild(new TreeNode<>(
                                                                                                                  new LeafNode(
                                                                                                                          "叶子节点",
                                                                                                                          "99")))
                                                                                                          .addChild(new TreeNode<>(
                                                                                                                  new LeafNode(
                                                                                                                          "叶子节点",
                                                                                                                          "9n")))))));
        TreeNode<FirstNode> res = new TreeNode<>(new FirstNode("一级节点2", "12"));
        nodes.add(res);
        res.addChild(new TreeNode<>(new SecondNode("二级节点", "23")).addChild(new TreeNode<>(new LeafNode("叶子节点", "93")))
                                                                 .addChild(new TreeNode<>(new LeafNode("叶子节点", "94")))
                                                                 .addChild(new TreeNode<>(new LeafNode("叶子节点", "95"))));
        res.addChild(new TreeNode<>(new SecondNode("二级节点", "24")).addChild(new TreeNode<>(new LeafNode("叶子节点", "96"))));
        return nodes;
    }

    private void getCheckedList(List<TreeNode> nodes, List<TreeNode> listCheckedNode) {
        for (TreeNode node : nodes) {
            if (node.content instanceof LeafNode) {
                if (((LeafNode) node.content).isChecked) {
                    listCheckedNode.add(node);
                }
            } else {
                getCheckedList(node.childList, listCheckedNode);
            }
        }
    }
}
