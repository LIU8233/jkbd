package com.example.administrator.jkbd.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jkbd.ExamApplication;
import com.example.administrator.jkbd.R;
import com.example.administrator.jkbd.bean.Question;
import com.example.administrator.jkbd.bean.Testtime;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class testActivity extends AppCompatActivity {
    TextView tvInfo,tvExamTitle,tv1,tv2,tv3,tv4;
    ImageView mImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        initData();
    }

    private void initView() {
        mImageView= (ImageView) findViewById(R.id.image_question);
        tvInfo= (TextView) findViewById(R.id.tv_testInfo);
        tvExamTitle= (TextView) findViewById(R.id.tv_question_title);
        tv1= (TextView) findViewById(R.id.tv_answer_1);
        tv2= (TextView) findViewById(R.id.tv_answer_2);
        tv3= (TextView) findViewById(R.id.tv_answer_3);
        tv4= (TextView) findViewById(R.id.tv_answer_4);
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
            tv1.setText(exam.getItem1());
            tv2.setText(exam.getItem2());
            tv3.setText(exam.getItem3());
            tv4.setText(exam.getItem4());
            Picasso.with(testActivity.this)
                    .load(exam.getUrl())
                    .into(mImageView);
        }
    }

    private void showData(Testtime testtime) {
        tvInfo.setText(testtime.toString());
    }
}
