package com.example.administrator.jkbd.biz;

import com.example.administrator.jkbd.bean.Question;

/**
 * Created by Administrator on 2017/7/3.
 */

public interface IExamBiz {
    void beginExam();
    Question getExam();
    Question getExam(int index);
    Question nextQuestion();
    Question preQuestion();
    int commitExam();
    String getExamIndex();
}
