package com.easyhoms.easydoctor.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.utils.KeyBoardUtils;


/**
 * 可编辑,加号,减号编辑框
 */
public class AddEditView extends LinearLayout{
    private EditText mEditEt ;
    private ImageView mAddImg ;
    private ImageView mReduceImg ;
    private int mCount=0;
    private Context mContext;

    public AddEditView(Context context) {
        super(context);
    }

    public AddEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        LayoutInflater.from(context).inflate(R.layout.custom_view_add_edit, this);

        mAddImg = (ImageView) findViewById(R.id.add_img);
        mReduceImg = (ImageView) findViewById(R.id.reduce_img);
        mEditEt = (EditText) findViewById(R.id.reduce_et);
        mEditEt.setFocusable(false);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.DetailMenu);
        //设置值
        initWidget(typedArray);

        initListeners();
    }

    private void initListeners() {
        mAddImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.hideKeyboard(mEditEt);
                if(mEditEt.getText()!=null){
                    mCount=Integer.valueOf(mEditEt.getText().toString());
                }
                mCount++;
                mEditEt.setText(mCount+"");
            }
        });

        mReduceImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.hideKeyboard(mEditEt);
                if(mEditEt.getText()!=null){
                    mCount=Integer.valueOf(mEditEt.getText().toString());
                }
                mCount--;
                if(mCount<0){
                    mCount=0;
                }
                mEditEt.setText(mCount+"");
            }
        });
        mEditEt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.showKeyboard(mEditEt);
            }
        });
    }

    private void initWidget(TypedArray typedArray) {
        //资源回收
        typedArray.recycle();
    }



}
