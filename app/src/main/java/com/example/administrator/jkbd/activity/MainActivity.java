package com.example.administrator.jkbd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.administrator.jkbd.R;
import com.example.administrator.jkbd.bean.Testtime;
import com.example.administrator.jkbd.utils.OkHttpUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void test(View view) {
        Intent intent=new Intent(MainActivity.this,testActivity.class);
        startActivity(intent);
        //startActivity(new Intent(MainActivity.this,testActivity.class));
    }

    public void exit(View view) {
        finish();
    }

    public void set(View view) {
        Intent intent=new Intent(MainActivity.this,SetActivity.class);
        startActivity(intent);
    }
}
