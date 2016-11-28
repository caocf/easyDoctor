package com.easyhoms.easydoctor.my.activity.personal;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.view.MyActionbar;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_my_introduce)
public class MyIntroduceActivity extends BaseActivity {

    @BindView(R.id.my_introduce_ma)
    private MyActionbar mActionbar;
    @BindView(R.id.my_introduce_et)
    private EditText mEditText;
    @BindView(R.id.my_introduce_num)
    private TextView mTextView;

    private String mIntroduce;

    @Override
    protected void initView() {
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mIntroduce = s.toString();
                mTextView.setText(s.length()+getString(R.string.hundred_num));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initActionbar() {
        mActionbar.setRightTv("完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.showToast(MyIntroduceActivity.this,"完成");
            }
        });
    }

    @Override
    protected void initListener() {

    }
}
