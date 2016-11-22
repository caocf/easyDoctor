package com.easyhoms.easydoctor.my.activity.phone;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.manager.UserManager;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;


@ContentView(R.layout.activity_update_phone)
public class UpdatePhoneFirstActivity extends BaseActivity {

    @BindView(R.id.my_phone_tv)
    TextView mMyPhoneTv;
    @BindView(R.id.my_passwd_auth_et)
    EditText mMyPasswdAuthEt;
    @BindView(R.id.next_tv)
    TextView mNextTv;
    @BindView(R.id.activity_update_phone)
    LinearLayout mActivityUpdatePhone;

    @Override
    protected void initView() {
        mMyPhoneTv.setText(String.format(getString(R.string.current_phone), UserManager.getUser().mobile));
        mMyPasswdAuthEt.addTextChangedListener(new TextWatcher() {
            String moblie;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                moblie = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                mNextTv.setSelected(moblie.length() == 11 ? true : false);
            }
        });
    }

    @Override
    protected void initActionbar() {

    }

    @Override
    protected void initListener() {

    }

}
