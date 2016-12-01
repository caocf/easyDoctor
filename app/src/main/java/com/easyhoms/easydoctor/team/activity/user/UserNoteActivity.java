package com.easyhoms.easydoctor.team.activity.user;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.manager.UserManager;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.KeyBoardUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.utils.ToastUtils;
import com.easyhoms.easydoctor.common.view.MyActionbar;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_user_note)
public class UserNoteActivity extends BaseActivity {

    @BindView(R.id.user_note_ma)
    private MyActionbar mActionbar;
    @BindView(R.id.user_note_et)
    private EditText mNoteEt;

    private String mYxId;

    private String mRemark;

    private NetCallback mNoteCallback = new NetCallback(mContext) {
        @Override
        protected void requestOK(String result) {
            if (CommonUtils.isResultOK(result)){
                ToastUtils.showToast(mContext,"修改成功");
                Intent intent = getIntent().putExtra("remark",mRemark);
                setResult(1,intent);
                KeyBoardUtils.hideKeyboard(mNoteEt);
                finish();
            }else {
                ToastUtils.showToast(mContext,CommonUtils.getMsg(result));
            }
        }

        @Override
        protected void timeOut() {

        }
    };

    @Override
    protected void initView() {
        mYxId = getIntent().getStringExtra("yxId");
    }

    @Override
    protected void initActionbar() {
        mActionbar.setRightTv(getString(R.string.complete), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mYxId!=null&& !TextUtils.isEmpty(mNoteEt.getText().toString())){
                    mRemark = mNoteEt.getText().toString();
                    BaseManager.addALias(mYxId,mRemark, UserManager.getBindHos().id,mNoteCallback);
                }else {
                    ToastUtils.showToast(mContext,"备注不能为空");
                }
            }
        });
    }

    @Override
    protected void initListener() {

    }


}
