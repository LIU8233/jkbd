package com.example.administrator.jkbd;

import android.app.Application;
import android.util.Log;

import com.example.administrator.jkbd.bean.Question;
import com.example.administrator.jkbd.bean.Testtime;
import com.example.administrator.jkbd.utils.OkHttpUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication extends Application{
    Testtime mTesttime;
    List<Question> mquestion;
    private static ExamApplication instane;
    @Override
    public void onCreate() {
        super.onCreate();
        instane=this;
        inData();
    }
    public static ExamApplication getInstance(){
        return  instane;
    }

    private void inData() {
        OkHttpUtils<Testtime> utils=new OkHttpUtils<>(instane);
        String uri="http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(uri).targetClass(Testtime.class)
                .execute(new OkHttpUtils.OnCompleteListener<Testtime>(){
                    @Override
                    public void onSuccess(Testtime result) {
                        Log.e("main","result"+result);
                        mTesttime=result;
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error"+error);

                    }
                });
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
