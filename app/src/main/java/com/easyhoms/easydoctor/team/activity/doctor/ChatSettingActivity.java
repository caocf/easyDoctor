package com.easyhoms.easydoctor.team.activity.doctor;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.Constants;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.response.BaseResp;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.view.MyActionbar;
import com.easyhoms.easydoctor.message.activity.TransforMemberActivity;
import com.easyhoms.easydoctor.my.reponse.UserDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.NimUIKit;
import com.netease.nim.common.ui.imageview.HeadImageView;
import com.netease.nimlib.sdk.team.model.TeamMember;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 聊天设置
 */
@ContentView(R.layout.activity_chat_setting)
public class ChatSettingActivity extends BaseActivity {

    @BindView(R.id.user_head_hiv)
    HeadImageView mHeadHiv;
    @BindView(R.id.user_store_img)
    ImageView mStoreImg;
    @BindView(R.id.transfor_rl)
    RelativeLayout mTransforRl;
    @BindView(R.id.chat_set_ma)
    MyActionbar mChatSetMa;
    @BindView(R.id.real_name_tv)
    TextView mRealNameTv;
    @BindView(R.id.nick_name_tv)
    TextView mNickNameTv;
    @BindView(R.id.age_tv)
    TextView mAgeTv;

    private boolean mIsStored = false;
    private String mUserImId;
    private long mGroupId;
    private String mYxTeamId;
    private UserDetail mUser;
    private ArrayList<TeamMember> mTeamMembers = new ArrayList<>();
    private NetCallback mCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                mIsStored = !mIsStored;
                mStoreImg.setSelected(mIsStored);
                if (mIsStored) {
                    showToast(R.string.store_user_ok);
                } else {
                    showToast(R.string.cancel_store_user_ok);
                }
            } else {

            }
        }

        @Override
        protected void timeOut() {

        }
    };

    private NetCallback mStoreCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                Type objectType = new TypeToken<BaseResp<Boolean>>() {
                }.getType();
                BaseResp<Boolean> res = new Gson().fromJson(result, objectType);
                mIsStored = res.content;
                mStoreImg.setSelected(mIsStored);
            } else {

            }
        }

        @Override
        protected void timeOut() {

        }
    };
    private TeamMember mLastMember;

    @Override
    protected void initView() {
        mUser = getIntent().getParcelableExtra(Constants.KEY_DATA);
        mUserImId = getIntent().getStringExtra(Constants.KEY_USER_ID);
        mYxTeamId = getIntent().getStringExtra(Constants.KEY_YX_TEAM_ID);
        mTeamMembers = (ArrayList<TeamMember>) getIntent().getSerializableExtra(Constants.KEY_YX_TEAM_MEMBERS);
        CommonUtils.loadImg(Constants.HOST_HEAD + "/" + mUser.image_path, mHeadHiv);
        if (mTeamMembers.size() == 2) {
            for (TeamMember member : mTeamMembers) {
                if (member.getAccount().startsWith(Constants.IM_DOCTOR)) {
                    mLastMember = member;
                    break;
                }
            }
        } else if (mTeamMembers.size() > 2) {
            mLastMember = mTeamMembers.get(mTeamMembers.size() - 1);
        }

        mTransforRl.setVisibility(mLastMember.getAccount().equals(NimUIKit.getAccount()) ? View.VISIBLE : View.VISIBLE);

        setText(mAgeTv,CommonUtils.getAge(mUser.birth)+"");
        setText(mNickNameTv,mUser.name);
        setText(mRealNameTv,mUser.name);
    }



    @Override
    protected void initActionbar() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        BaseManager.getFavoriteStatus(mUserImId, mStoreCallback);
    }

    @Event(R.id.user_store_img)
    private void userStore(View view) {
        showdialog();
        if (mIsStored) {
            BaseManager.cancelFavorite(mUserImId, mCallback);
        } else {
            BaseManager.addFavorite(mUserImId, mCallback);
        }
    }

    @Event(R.id.transfor_rl)
    private void transforMember(View view) {
        Intent intent = new Intent(mContext, TransforMemberActivity.class);
        // intent.putExtra(Constants.KEY_GROUP_ID,mGroupId);
        intent.putExtra(Constants.KEY_YX_TEAM_ID, mYxTeamId);
        intent.putExtra(Constants.KEY_YX_TEAM_MEMBERS, mTeamMembers);
        startActivity(intent);
    }

}
