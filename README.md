# RecyclerTreeView
[![](https://jitpack.io/v/TellH/RecyclerTreeView.svg)](https://jitpack.io/#TellH/RecyclerTreeView)</br>
TreeView implement in Android with RecyclerView..

## Effect
![](https://raw.githubusercontent.com/TellH/RecyclerTreeView/master/raw/effect.gif)

## Usage
### Setup
root build.gradle
```groovy
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```
app build.gradle
```groovy
dependencies {
    implementation 'androidx.appcompat:appcompat:1.0.2'
    implementation 'androidx.recyclerview:recyclerview:1.0.0'
    implementation 'com.github.zcolin:TreeView:latest.release'
}
```



### Quick Start

- Create a Java bean class and  implement the LayoutItemType, to attach item layout id to it.

```java
public class Dir implements LayoutItemType {
    public String dirName;

    public Dir(String dirName) {
        this.dirName = dirName;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_dir;
    }
}
```



- Create a ViewBinder to bind view with the data bean. As you see, `provideViewHolder(View itemView)` corresponds for `onCreateViewHolder` in RecyclerView, and `bindView` corresponds for `onBindViewHolder` in RecyclerView. 

```java
public class FileNodeBinder extends TreeViewBinder<FileNodeBinder.ViewHolder> {
    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(ViewHolder holder, int position, TreeNode node) {
        File fileNode = (File) node.getContent();
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
```



- Add TreeNode to TreeViewAdapter.

```java
        List<TreeNode> nodes = new ArrayList<>();
        TreeNode<Dir> app = new TreeNode<>(new Dir("app"));
        nodes.add(app);
        app.addChild(
                new TreeNode<>(new Dir("manifests"))
                        .addChild(new TreeNode<>(new File("AndroidManifest.xml")))
        );
        app.addChild(
                new TreeNode<>(new Dir("java")).addChild(
                        new TreeNode<>(new Dir("tellh")).addChild(
                                new TreeNode<>(new Dir("com")).addChild(
                                        new TreeNode<>(new Dir("recyclertreeview"))
                                                .addChild(new TreeNode<>(new File("Dir")))
                                                .addChild(new TreeNode<>(new File("DirectoryNodeBinder")))
                                                .addChild(new TreeNode<>(new File("File")))
                                                .addChild(new TreeNode<>(new File("FileNodeBinder")))
                                                .addChild(new TreeNode<>(new File("TreeViewBinder")))
                                )
                        )
                )
        );
        TreeNode<Dir> res = new TreeNode<>(new Dir("res"));
        nodes.add(res);
        res.addChild(
                new TreeNode<>(new Dir("layout"))
                        .addChild(new TreeNode<>(new File("activity_main.xml")))
                        .addChild(new TreeNode<>(new File("item_dir.xml")))
                        .addChild(new TreeNode<>(new File("item_file.xml")))
        );
        res.addChild(
                new TreeNode<>(new Dir("mipmap"))
                        .addChild(new TreeNode<>(new File("ic_launcher.png")))
        );
        TreeViewAdapter adapter = new TreeViewAdapter(nodes, Arrays.asList(new FileNodeBinder(), new DirectoryNodeBinder()));
        rv.setAdapter(adapter);
```

- set TreeNodeListener to TreeViewAdapter

```java
        adapter.setOnTreeNodeListener(new TreeViewAdapter.OnTreeNodeListener() {
            @Override
            public boolean onClick(TreeNode node, RecyclerView.ViewHolder holder) {
                if (!node.isLeaf()) {
                    //Update and toggle the node.
                    onToggle(!node.isExpand(), holder);
                }
                return false;
            }

            @Override
            public void onToggle(boolean isExpand, RecyclerView.ViewHolder holder) {
                DirectoryNodeBinder.ViewHolder dirViewHolder = (DirectoryNodeBinder.ViewHolder) holder;
                final ImageView ivArrow = dirViewHolder.getIvArrow();
                int rotateDegree = isExpand ? 90 : -90;
                ivArrow.animate().rotationBy(rotateDegree)
                        .start();
            }
        });
```



Please check out the Demo and source code for more information. If you have any question, feel free to raise an issue. Thanks a lot!
