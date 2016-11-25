package com.easyhoms.easydoctor.my.activity.personal;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.manager.UserManager;
import com.easyhoms.easydoctor.common.view.MyActionbar;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;


@ContentView(R.layout.activity_sex)
public class SexActivity extends BaseActivity {

    @BindView(R.id.actionbar)
    MyActionbar mActionbar;
    @BindView(R.id.img_my_update_male_check)
    ImageView mImgMyUpdateMaleCheck;
    @BindView(R.id.ll_my_update_male)
    LinearLayout mLlMyUpdateMale;
    @BindView(R.id.img_my_update_female_check)
    ImageView mImgMyUpdateFemaleCheck;
    @BindView(R.id.ll_my_update_female)
    LinearLayout mLlMyUpdateFemale;

    private boolean mCheckMale = true;

    @Override
    protected void initView() {
        if (TextUtils.isEmpty(UserManager.getUser().gender)) {
            mCheckMale = true;
        } else {
            mCheckMale = UserManager.getUser().gender.equals("1") ? true : false;
        }
        checkSex();
    }

    @Override
    protected void initActionbar() {

    }

    @Override
    protected void initListener() {

    }

    @Event(R.id.ll_my_update_male)
    private void checkMale(View view) {
        mCheckMale = true;
        checkSex();
    }

    @Event(R.id.ll_my_update_female)
    private void checkFemale(View view) {
        mCheckMale = false;
        checkSex();
    }

    public void checkSex() {
        mImgMyUpdateFemaleCheck.setVisibility(mCheckMale ? View.INVISIBLE : View.VISIBLE);
        mImgMyUpdateMaleCheck.setVisibility(mCheckMale ? View.VISIBLE : View.INVISIBLE);
    }

}
