package com.easyhoms.easydoctor.my.activity.personal;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.manager.UserManager;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.view.MyActionbar;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;


@ContentView(R.layout.activity_real_name)
public class RealNameActivity extends BaseActivity {

    @BindView(R.id.real_name_ma)
    MyActionbar mRealNameMa;
    @BindView(R.id.real_name_et)
    EditText mRealNameEt;

    private String mNameStr;

    //上传图片地址
    private NetCallback mCallback = new NetCallback(mContext) {
        @Override
        protected void requestOK(String result) {
            if (CommonUtils.isResultOK(result)) {
                UserManager.setUser("name", mNameStr);
                showToast(R.string.save_ok);
                finish();
            } else {
                showToast(CommonUtils.getMsg(result));
            }
        }

        @Override
        protected void timeOut() {
        }
    };

    @Override
    protected void initView() {
        mNameStr= UserManager.getUser().name;
        mRealNameEt.setText(TextUtils.isEmpty(mNameStr)?"":mNameStr);
        mRealNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNameStr=s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
               mRealNameMa.setRightTvVisible(TextUtils.isEmpty(mNameStr)?View.INVISIBLE:View.VISIBLE);
            }
        });
    }

    @Override
    protected void initActionbar() {
        mRealNameMa.setRightTv(R.string.complete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BaseManager.userDetailInfoModify("", mNameStr, "", "", mCallback);
            }
        });
        mRealNameMa.setRightTvVisible(TextUtils.isEmpty(mNameStr)?View.INVISIBLE:View.VISIBLE);
    }

    @Override
    protected void initListener() {

    }

}
