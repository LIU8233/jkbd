package com.example.administrator.jkbd;

import android.app.Application;
import android.service.media.MediaBrowserService;
import android.util.Log;

import com.example.administrator.jkbd.bean.Question;
import com.example.administrator.jkbd.bean.Results;
import com.example.administrator.jkbd.bean.Testtime;
import com.example.administrator.jkbd.biz.ExamBiz;
import com.example.administrator.jkbd.biz.IExamBiz;
import com.example.administrator.jkbd.utils.OkHttpUtils;
import com.example.administrator.jkbd.utils.ResultUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication extends Application{
    Testtime mTesttime;
    List<Question> mquestion;
    private static ExamApplication instane;
    IExamBiz biz;
    @Override
    public void onCreate() {
        super.onCreate();
        instane=this;
        biz=new ExamBiz();
        inData();
    }
    public static ExamApplication getInstance(){
        return  instane;
    }

    private void inData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();

            }
        }).start();


    }

    public Testtime getmTesttime() {
        return mTesttime;
    }

    public void setmTesttime(Testtime mTesttime) {
        this.mTesttime = mTesttime;
    }

    public List<Question> getMquestion() {
        return mquestion;
    }

    public void setMquestion(List<Question> mquestion) {
        this.mquestion = mquestion;
    }
}
