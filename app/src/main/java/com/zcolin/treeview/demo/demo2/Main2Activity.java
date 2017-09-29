package com.zcolin.treeview.demo.demo2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zcolin.treeview.demo.R;

public class Main2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.frame_layout, MainFragment.newInstance())
                                   .commitAllowingStateLoss();
    }
}
