package com.example.administrator.jkbd.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.jkbd.ExamApplication;
import com.example.administrator.jkbd.R;
import com.example.administrator.jkbd.bean.Question;
import com.example.administrator.jkbd.bean.Testtime;
import com.example.administrator.jkbd.biz.ExamBiz;
import com.example.administrator.jkbd.biz.IExamBiz;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class testActivity extends AppCompatActivity {
    TextView tvInfo,tvExamTitle,tv1,tv2,tv3,tv4,tvLoad,tvExamnNo;
    CheckBox cb1,cb2,cb3,cb4;
    CheckBox[] cbs=new CheckBox[4];
    ImageView mImageView;
    ProgressBar dialog;
    IExamBiz biz;
    boolean isExamInfo=false;
    boolean isQuestion=false;
    LoadExamBrcadcast mLoadExamBrcadcast;
    LoadQuestionBrcadcast mLoadQuestionBrcadcast;
    LinearLayout layoutLoading,layout3,layout4;
    boolean isExamInfoReceiver=false;
    boolean isQuestionReceiver=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mLoadExamBrcadcast=new LoadExamBrcadcast();
        mLoadQuestionBrcadcast=new LoadQuestionBrcadcast();
        biz=new ExamBiz();
        setListrener();
        initView();
        loadData();
    }

    private void setListrener() {
        registerReceiver(mLoadExamBrcadcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestionBrcadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void loadData() {
        layoutLoading.setEnabled(false);
        dialog.setVisibility(View.VISIBLE);
        tvLoad.setText("下载数据！");
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();

            }
        }).start();
    }

    private void initView() {
        cb1= (CheckBox) findViewById(R.id.cb_01);
        cb2= (CheckBox) findViewById(R.id.cb_02);
        cb3= (CheckBox) findViewById(R.id.cb_03);
        cb4= (CheckBox) findViewById(R.id.cb_04);
        dialog= (ProgressBar) findViewById(R.id.dia_log);
        mImageView= (ImageView) findViewById(R.id.image_question);
        tvExamnNo= (TextView) findViewById(R.id.tv_no);
        tvInfo= (TextView) findViewById(R.id.tv_testInfo);
        tvExamTitle= (TextView) findViewById(R.id.tv_question_title);
        tv1= (TextView) findViewById(R.id.tv_answer_1);
        tv2= (TextView) findViewById(R.id.tv_answer_2);
        tv3= (TextView) findViewById(R.id.tv_answer_3);
        tv4= (TextView) findViewById(R.id.tv_answer_4);
        tvLoad= (TextView) findViewById(R.id.tv_load);
        cbs[0]=cb1;
        cbs[1]=cb2;
        cbs[2]=cb3;
        cbs[3]=cb4;
        layout3= (LinearLayout) findViewById(R.id.layout_03);
        layout4= (LinearLayout) findViewById(R.id.layout_04);
        layoutLoading= (LinearLayout) findViewById(R.id.loading);
        layoutLoading.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadData();

            }
        });
        cb1.setOnCheckedChangeListener(listener);
        cb2.setOnCheckedChangeListener(listener);
        cb3.setOnCheckedChangeListener(listener);
        cb4.setOnCheckedChangeListener(listener);
    }
    CompoundButton.OnCheckedChangeListener listener=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                int userAnswer = 0;
                switch (buttonView.getId()) {
                    case R.id.cb_01:
                        userAnswer = 1;
                        break;
                    case R.id.cb_02:
                        userAnswer = 2;
                        break;
                    case R.id.cb_03:
                        userAnswer = 3;
                        break;
                    case R.id.cb_04:
                        userAnswer = 4;
                        break;
                }
                if (userAnswer > 0) {
                    for (CheckBox cb : cbs) {
                        cb.setChecked(false);
                    }
                    cbs[userAnswer - 1].setChecked(true);
                }
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadExamBrcadcast!=null){
            unregisterReceiver(mLoadExamBrcadcast);
        }
        if (mLoadQuestionBrcadcast!=null){
            unregisterReceiver(mLoadQuestionBrcadcast);
        }
    }

    private void initData() {
        if(isExamInfoReceiver&&isQuestionReceiver){
            if (isExamInfo&&isQuestion) {
                layoutLoading.setVisibility(View.GONE);
                Testtime testtime = ExamApplication.getInstance().getmTesttime();
                if (testtime != null) {
                    showData(testtime);
                }
                    showExam(biz.getExam());
            }
            else {
                layoutLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);
                tvLoad.setText("下载失败，点击重新下载！");

            }
        }

    }

    private void showExam(Question exam) {
        Log.e("showExam","showExam,exam="+exam);
        if (exam!=null){
            tvExamnNo.setText(biz.getExamIndex());
            tvExamTitle.setText(exam.getQuestion());
            tv1.setText(exam.getItem1());
            tv2.setText(exam.getItem2());
            tv3.setText(exam.getItem3());
            tv4.setText(exam.getItem4());
            layout3.setVisibility(exam.getItem3().equals("")?View.GONE:View.VISIBLE);
            cb3.setVisibility(exam.getItem3().equals("")?View.GONE:View.VISIBLE);
            layout4.setVisibility(exam.getItem4().equals("")?View.GONE:View.VISIBLE);
            cb4.setVisibility(exam.getItem4().equals("")?View.GONE:View.VISIBLE);
            if (exam.getUrl()!=null&&!exam.getUrl().equals("")) {
                mImageView.setVisibility(View.VISIBLE);
                Picasso.with(testActivity.this)
                        .load(exam.getUrl())
                        .into(mImageView);
            }else {
                mImageView.setVisibility(View.GONE);
            }
            resetOptions();

        }
    }

    private void resetOptions() {
        for (CheckBox cb:cbs){
            cb.setChecked(false);
        }
    }

    private void showData(Testtime testtime) {
        tvInfo.setText(testtime.toString());
    }

    public void preExam(View view) {
        showExam(biz.preQuestion());
    }

    public void nextExam(View view) {
        showExam(biz.nextQuestion());
    }

    class LoadExamBrcadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("LoadExamBrcadcast","LoadExamBrcadcast,isSuccess="+isSuccess);
            if(isSuccess){
                isExamInfo=true;
            }
            isExamInfoReceiver=true;
            initData();

        }
    }

    class LoadQuestionBrcadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("LoadQuestionBrcadcast","LoadQuestionBrcadcast,isSuccess="+isSuccess);
            if(isSuccess){
                isQuestion=true;
            }
            isQuestionReceiver=true;
            initData();

        }
    }
}
