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
    public static  String LOAD_EXAM_INFO="load_exam_info";
    public static  String LOAD_EXAM_QUESTION="load_exam_question";
    public static  String LOAD_DATA_SUCCESS="load_data_success";
    Testtime mTesttime;
    List<Question> mquestion;
    private static ExamApplication instane;
    @Override
    public void onCreate() {
        super.onCreate();
        instane=this;


    }
    public static ExamApplication getInstance(){
        return  instane;
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
