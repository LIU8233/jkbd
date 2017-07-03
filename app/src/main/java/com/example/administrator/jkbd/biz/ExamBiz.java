package com.example.administrator.jkbd.biz;

import com.example.administrator.jkbd.ExamApplication;
import com.example.administrator.jkbd.bean.Question;
import com.example.administrator.jkbd.dao.ExamDao;
import com.example.administrator.jkbd.dao.IExamDao;

import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */

public class ExamBiz implements IExamBiz {
    IExamDao dao;
    int examInde=0;
    List<Question> examList=null;
    public  ExamBiz(){
        this.dao=new ExamDao();
    }
    @Override
    public void beginExam() {
        examInde=0;
        dao.loadExamInfo();
        dao.loadExamList();


    }

    @Override
    public Question getExam() {
        examList=ExamApplication.getInstance().getMquestion();
        if (examList!=null) {
            return examList.get(examInde);
        }else {
            return null;
        }
    }


    @Override
    public Question nextQuestion() {
        if (examList!=null&&examInde<examList.size()-1) {
            examInde++;
            return examList.get(examInde);
        }else {
            return null;
        }
    }

    @Override
    public Question preQuestion() {
        if (examList!=null&&examInde>0) {
            examInde--;
            return examList.get(examInde);
        }else {
            return null;
        }
    }

    @Override
    public void commitExam() {

    }

    @Override
    public String getExamIndex() {
        return (examInde+1)+".";
    }
}
