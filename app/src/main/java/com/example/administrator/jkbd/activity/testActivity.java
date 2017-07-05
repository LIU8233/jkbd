package com.example.administrator.jkbd.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Gallery;
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
import com.example.administrator.jkbd.view.QuestionAdapter;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/29.
 */

public class testActivity extends AppCompatActivity {
    CheckBox[] cbs = new CheckBox[4];
    QuestionAdapter aAdapter;
    IExamBiz biz;
    boolean isExamInfo = false;
    boolean isQuestion = false;
    LoadExamBrcadcast mLoadExamBrcadcast;
    LoadQuestionBrcadcast mLoadQuestionBrcadcast;
    boolean isExamInfoReceiver = false;
    boolean isQuestionReceiver = false;
    @BindView(R.id.dia_log) ProgressBar dialog;
    @BindView(R.id.tv_load) TextView tvLoad;
    @BindView(R.id.loading) LinearLayout layoutLoading;
    @BindView(R.id.tv_testInfo) TextView tvInfo;
    @BindView(R.id.tv_no) TextView tvExamnNo;
    @BindView(R.id.tv_question_title) TextView tvExamTitle;
    @BindView(R.id.image_question) ImageView mImageView;
    @BindView(R.id.tv_answer_1) TextView tv1;
    @BindView(R.id.tv_answer_2) TextView tv2;
    @BindView(R.id.tv_answer_3) TextView tv3;
    @BindView(R.id.layout_03) LinearLayout layout3;
    @BindView(R.id.tv_answer_4) TextView tv4;
    @BindView(R.id.layout_04) LinearLayout layout4;
    @BindView(R.id.cb_01) CheckBox cb1;
    @BindView(R.id.cb_02) CheckBox cb2;
    @BindView(R.id.cb_03) CheckBox cb3;
    @BindView(R.id.cb_04) CheckBox cb4;
    @BindView(R.id.gallery) Gallery mGallery;
    @BindView(R.id.btn_next) Button btnNext;
    @BindView(R.id.tv_time) TextView tvTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        mLoadExamBrcadcast = new LoadExamBrcadcast();
        mLoadQuestionBrcadcast = new LoadQuestionBrcadcast();
        biz = new ExamBiz();
        setListrener();
        initView();
        loadData();
    }

    private void setListrener() {
        registerReceiver(mLoadExamBrcadcast, new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestionBrcadcast, new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
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
        mGallery = (Gallery) findViewById(R.id.gallery);
        cb1 = (CheckBox) findViewById(R.id.cb_01);
        cb2 = (CheckBox) findViewById(R.id.cb_02);
        cb3 = (CheckBox) findViewById(R.id.cb_03);
        cb4 = (CheckBox) findViewById(R.id.cb_04);
        dialog = (ProgressBar) findViewById(R.id.dia_log);
        mImageView = (ImageView) findViewById(R.id.image_question);
        tvExamnNo = (TextView) findViewById(R.id.tv_no);
        tvInfo = (TextView) findViewById(R.id.tv_testInfo);
        tvExamTitle = (TextView) findViewById(R.id.tv_question_title);
        tv1 = (TextView) findViewById(R.id.tv_answer_1);
        tv2 = (TextView) findViewById(R.id.tv_answer_2);
        tv3 = (TextView) findViewById(R.id.tv_answer_3);
        tv4 = (TextView) findViewById(R.id.tv_answer_4);
        tvLoad = (TextView) findViewById(R.id.tv_load);
        cbs[0] = cb1;
        cbs[1] = cb2;
        cbs[2] = cb3;
        cbs[3] = cb4;
        layout3 = (LinearLayout) findViewById(R.id.layout_03);
        layout4 = (LinearLayout) findViewById(R.id.layout_04);
        layoutLoading = (LinearLayout) findViewById(R.id.loading);
        layoutLoading.setOnClickListener(new View.OnClickListener() {
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

    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
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
        if (mLoadExamBrcadcast != null) {
            unregisterReceiver(mLoadExamBrcadcast);
        }
        if (mLoadQuestionBrcadcast != null) {
            unregisterReceiver(mLoadQuestionBrcadcast);
        }
    }

    private void initData() {
        if (isExamInfoReceiver && isQuestionReceiver) {
            if (isExamInfo && isQuestion) {
                layoutLoading.setVisibility(View.GONE);
                Testtime testtime = ExamApplication.getInstance().getmTesttime();
                if (testtime != null) {
                    showData(testtime);
                    initTime(testtime);
                }
                initGallery();
                showExam(biz.getExam());

            } else {
                layoutLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);
                tvLoad.setText("下载失败，点击重新下载！");

            }
        }

    }

    private void initGallery() {
        aAdapter = new QuestionAdapter(this);
        mGallery.setAdapter(aAdapter);
        mGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("gallery", "gallery item postion=" + position);
                saveUserAnswer();
                showExam(biz.getExam(position));
            }
        });
    }

    private void initTime(final Testtime testtime) {
        int sumTime = testtime.getLimitTime() * 60 * 1000;
        final long overTime = sumTime + System.currentTimeMillis();
        final Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                long l = overTime - System.currentTimeMillis();
                final long min = l / 1000 / 60;
                final long sec = l / 1000 % 60;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTime.setText("剩余时间:" + min + "分" + sec + "秒");
                    }
                });
            }
        }, 0, 1000);
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                time.cancel();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        commit(null);
                    }
                });
            }
        }, sumTime);
    }


    private void showExam(Question exam) {
        Log.e("showExam", "showExam,exam=" + exam);
        if (exam != null) {
            tvExamnNo.setText(biz.getExamIndex());
            tvExamTitle.setText(exam.getQuestion());
            tv1.setText(exam.getItem1());
            tv2.setText(exam.getItem2());
            tv3.setText(exam.getItem3());
            tv4.setText(exam.getItem4());
            tvTime = (TextView) findViewById(R.id.tv_time);
            layout3.setVisibility(exam.getItem3().equals("") ? View.GONE : View.VISIBLE);
            cb3.setVisibility(exam.getItem3().equals("") ? View.GONE : View.VISIBLE);
            layout4.setVisibility(exam.getItem4().equals("") ? View.GONE : View.VISIBLE);
            cb4.setVisibility(exam.getItem4().equals("") ? View.GONE : View.VISIBLE);
            if (exam.getUrl() != null && !exam.getUrl().equals("")) {
                mImageView.setVisibility(View.VISIBLE);
                Picasso.with(testActivity.this)
                        .load(exam.getUrl())
                        .into(mImageView);
            } else {
                mImageView.setVisibility(View.GONE);
            }
            resetOptions();
            String userAnswer = exam.getUserAnswer();
            if (userAnswer != null && !userAnswer.equals("")) {
                int userCB = Integer.parseInt(userAnswer) - 1;
                cbs[userCB].setChecked(true);
                setOptins(true);
            } else {
                setOptins(false);
            }

        }
    }

    private void setOptins(boolean hasAaswer) {
        for (CheckBox cb : cbs) {
            cb.setEnabled(!hasAaswer);
        }
    }

    private void resetOptions() {
        for (CheckBox cb : cbs) {
            cb.setChecked(false);
        }
    }

    public void saveUserAnswer() {

        for (int i = 0; i < cbs.length; i++) {
            if (cbs[i].isChecked()) {
                biz.getExam().setUserAnswer(String.valueOf(i + 1));
                aAdapter.notifyDataSetChanged();
                return;
            }
        }
        biz.getExam().setUserAnswer("");
        aAdapter.notifyDataSetChanged();

    }

    private void showData(Testtime testtime) {
        tvInfo.setText(testtime.toString());
    }

    public void preExam(View view) {
        saveUserAnswer();
        showExam(biz.preQuestion());
    }

    public void nextExam(View view) {
        saveUserAnswer();
        showExam(biz.nextQuestion());
    }

    public void commit(View view) {
        saveUserAnswer();
        int s = biz.commitExam();
        View inflate = View.inflate(this, R.layout.layout_result, null);
        TextView tvResult = (TextView) inflate.findViewById(R.id.tv_result);
        tvResult.setText("你的分数为\n" + s + "分");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.exam_commit32x32)
                .setTitle("考试结果")
                .setView(inflate)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.create().show();
    }

    class LoadExamBrcadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS, false);
            Log.e("LoadExamBrcadcast", "LoadExamBrcadcast,isSuccess=" + isSuccess);
            if (isSuccess) {
                isExamInfo = true;
            }
            isExamInfoReceiver = true;
            initData();

        }
    }

    class LoadQuestionBrcadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS, false);
            Log.e("LoadQuestionBrcadcast", "LoadQuestionBrcadcast,isSuccess=" + isSuccess);
            if (isSuccess) {
                isQuestion = true;
            }
            isQuestionReceiver = true;
            initData();

        }
    }
}
