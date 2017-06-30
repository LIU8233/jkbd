package com.example.administrator.jkbd.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.administrator.jkbd.ExamApplication;
import com.example.administrator.jkbd.R;
import com.example.administrator.jkbd.bean.Question;
import com.example.administrator.jkbd.bean.Testtime;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class testActivity extends AppCompatActivity {
    TextView tvInfo,tvExamTitle,tv1,tv2,tv3,tv4;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        initData();
    }

    private void initView() {
        tvInfo= (TextView) findViewById(R.id.tv_testInfo);
        tvExamTitle= (TextView) findViewById(R.id.tv_question_title);
        tvInfo= (TextView) findViewById(R.id.tv_answer_1);
        tvInfo= (TextView) findViewById(R.id.tv_answer_2);
        tvInfo= (TextView) findViewById(R.id.tv_answer_3);
        tvInfo= (TextView) findViewById(R.id.tv_answer_4);
    }

    private void initData() {
        Testtime testtime = ExamApplication.getInstance().getmTesttime();
        if (testtime!=null){
            showData(testtime);
        }
        List<Question> examList=ExamApplication.getInstance().getMquestion();
        if (examList!=null){
            showExam(examList);
        }
    }

    private void showExam(List<Question> examList) {
        Question exam=examList.get(0);
        if (exam!=null){
            tvExamTitle.setText(exam.getQuestion());
        }
    }

    private void showData(Testtime testtime) {
        tvInfo.setText(testtime.toString());
    }
}
