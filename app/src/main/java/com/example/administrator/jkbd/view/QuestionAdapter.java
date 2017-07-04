package com.example.administrator.jkbd.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jkbd.ExamApplication;
import com.example.administrator.jkbd.R;
import com.example.administrator.jkbd.bean.Question;

import java.util.List;

/**
 * Created by Administrator on 2017/7/4/004.
 */

public class QuestionAdapter extends BaseAdapter {
    Context mContext;
    List<Question> mquestion;

    public QuestionAdapter(Context mContext) {
        this.mContext = mContext;
        mquestion = ExamApplication.getInstance().getMquestion();
    }

    @Override
    public int getCount() {
        return mquestion==null?0:mquestion.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=View.inflate(mContext, R.layout.item_question,null);
        TextView tvNum= (TextView) view.findViewById(R.id.tv_num);
        ImageView ivQuestion= (ImageView) view.findViewById(R.id.iv_question);
        tvNum.setText("第"+(position+1)+"题");
        return view;
    }
}
