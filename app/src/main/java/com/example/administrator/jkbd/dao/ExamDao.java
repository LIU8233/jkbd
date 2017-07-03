package com.example.administrator.jkbd.dao;

import android.util.Log;

import com.example.administrator.jkbd.ExamApplication;
import com.example.administrator.jkbd.bean.Question;
import com.example.administrator.jkbd.bean.Results;
import com.example.administrator.jkbd.bean.Testtime;
import com.example.administrator.jkbd.utils.OkHttpUtils;
import com.example.administrator.jkbd.utils.ResultUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */

public class ExamDao implements  IExamDao {
    @Override
    public void loadExamInfo() {
        OkHttpUtils<Testtime> utils=new OkHttpUtils<>(ExamApplication.getInstance());
        String uri="http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(uri).targetClass(Testtime.class)
                .execute(new OkHttpUtils.OnCompleteListener<Testtime>(){
                    @Override
                    public void onSuccess(Testtime result) {
                        Log.e("main","result"+result);
                        ExamApplication.getInstance().setmTesttime(result);

                    }
                    @Override
                    public void onError(String error) {
                        Log.e("main","error"+error);

                    }
                });

    }

    @Override
    public void loadExamList() {
        OkHttpUtils<String> utils1=new OkHttpUtils<>(ExamApplication.getInstance());
        String url2="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
        utils1.url(url2)
                .targetClass(String.class)
                .execute(new OkHttpUtils.OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String jsonStr) {
                        Results result = ResultUtils.getListResultFromJson(jsonStr);
                        if (result!=null&&result.getError_code()==0){
                            List<Question> list=result.getResult();
                            if (list!=null&&list.size()>0){
                                ExamApplication.getInstance().setMquestion(list);
                            }
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error"+error);

                    }
                });

    }
}
