/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-1-9 下午2:34
 * ********************************************************
 */

package com.zcolin.treeview.demo.demo1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.zcolin.treeview.TreeNode;
import com.zcolin.treeview.TreeViewAdapter;
import com.zcolin.treeview.demo.R;
import com.zcolin.treeview.demo.demo1.bean.Dir;
import com.zcolin.treeview.demo.demo1.bean.File;
import com.zcolin.treeview.demo.demo1.viewbinder.DirectoryNodeBinder;
import com.zcolin.treeview.demo.demo1.viewbinder.FileNodeBinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView    rv;
    private TreeViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        List<TreeNode> nodes = new ArrayList<>();
        TreeNode<Dir> app = new TreeNode<>(new Dir("app"));
        nodes.add(app);
        app.addChild(new TreeNode<>(new Dir("manifests")).addChild(new TreeNode<>(new File("AndroidManifest.xml"))));
        app.addChild(new TreeNode<>(new Dir("java")).addChild(new TreeNode<>(new Dir("tellh")).addChild(new TreeNode<>(new Dir("com")).addChild(new 
                TreeNode<>(new Dir("recyclertreeview"))
                .addChild(new TreeNode<>(new File("Dir")))
                .addChild(new TreeNode<>(new File("DirectoryNodeBinder")))
                .addChild(new TreeNode<>(new File("File")))
                .addChild(new TreeNode<>(new File("FileNodeBinder")))
                .addChild(new TreeNode<>(new File("TreeViewBinder")))))));
        TreeNode<Dir> res = new TreeNode<>(new Dir("res"));
        nodes.add(res);
        res.addChild(new TreeNode<>(new Dir("layout")).addChild(new TreeNode<>(new File("activity_main.xml")))
                                                      .addChild(new TreeNode<>(new File("item_dir.xml")))
                                                      .addChild(new TreeNode<>(new File("item_file.xml"))));
        res.addChild(new TreeNode<>(new Dir("mipmap")).addChild(new TreeNode<>(new File("ic_launcher.png"))));
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TreeViewAdapter(nodes, Arrays.asList(new FileNodeBinder(), new DirectoryNodeBinder()));
        adapter.setOnTreeNodeListener(new TreeViewAdapter.OnTreeNodeListener() {
            @Override
            public boolean onClick(TreeNode node, RecyclerView.ViewHolder holder) {
                if (!node.isLeaf()) {
                    //Update and toggle the node.
                    onToggle(!node.isExpand, holder);
                    //                    if (!node.isExpand())
                    //                        adapter.collapseBrotherNode(node);
                }
                return false;
            }

            @Override
            public void onToggle(boolean isExpand, RecyclerView.ViewHolder holder) {
                DirectoryNodeBinder.ViewHolder dirViewHolder = (DirectoryNodeBinder.ViewHolder) holder;
                final ImageView ivArrow = dirViewHolder.getIvArrow();
                int rotateDegree = isExpand ? 90 : -90;
                ivArrow.animate().rotationBy(rotateDegree).start();
            }
        });
        rv.setAdapter(adapter);
    }

    private void initView() {
        rv = findViewById(R.id.rv);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.id_action_close_all:
                adapter.collapseAll();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
