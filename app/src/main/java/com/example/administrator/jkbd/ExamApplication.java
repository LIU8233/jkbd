package com.example.administrator.jkbd;

import android.app.Application;
import android.service.media.MediaBrowserService;
import android.util.Log;

import com.example.administrator.jkbd.bean.Question;
import com.example.administrator.jkbd.bean.Results;
import com.example.administrator.jkbd.bean.Testtime;
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
        new Thread(new Runnable() {
            @Override
            public void run() {
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
                OkHttpUtils<String> utils1=new OkHttpUtils<String>(instane);
                String url2="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
                utils1.url(url2)
                        .targetClass(String.class)
                        .execute(new OkHttpUtils.OnCompleteListener<String>() {
                            @Override
                            public void onSuccess(String jsonStr) {
                                Results results = ResultUtils.getListResultFromJson(jsonStr);
                                if (results!=null&&results.getError_code()==0){
                                    List<Question> list=results.getResult();
                                    if (list!=null&&list.size()>0){
                                        mquestion=list;
                                    }
                                }
                            }

                            @Override
                            public void onError(String error) {
                                Log.e("main","error"+error);

                            }
                        });
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
