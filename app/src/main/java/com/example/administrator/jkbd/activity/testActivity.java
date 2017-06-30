package com.example.administrator.jkbd.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.administrator.jkbd.ExamApplication;
import com.example.administrator.jkbd.R;
import com.example.administrator.jkbd.bean.Testtime;

/**
 * Created by Administrator on 2017/6/29.
 */

public class testActivity extends AppCompatActivity {
    TextView tvInfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        initData();
    }

    private void initView() {
        tvInfo= (TextView) findViewById(R.id.tv_testInfo);
    }

    private void initData() {
        Testtime testtime = ExamApplication.getInstance().getmTesttime();
        if (testtime!=null){
            showData(testtime);
        }
    }

    private void showData(Testtime testtime) {
        tvInfo.setText(testtime.toString());
    }
}
