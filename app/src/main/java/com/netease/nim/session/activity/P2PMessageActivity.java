package com.netease.nim.session.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.utils.AppManager;
import com.easyhoms.easydoctor.common.utils.LogUtils;
import com.easyhoms.easydoctor.common.view.FlowLayout;
import com.netease.nim.cache.FriendDataCache;
import com.netease.nim.common.ui.imageview.HeadImageView;
import com.netease.nim.common.ui.popupmenu.NIMPopupMenu;
import com.netease.nim.common.ui.popupmenu.PopupMenuItem;
import com.netease.nim.session.SessionCustomization;
import com.netease.nim.session.constant.Extras;
import com.netease.nim.session.fragment.MessageFragment;
import com.netease.nim.uinfo.UserInfoHelper;
import com.netease.nim.uinfo.UserInfoObservable;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 点对点聊天界面
 * <p/>
 * Created by huangjun on 2015/2/1.
 */

@ContentView(R.layout.nim_message_activity)
public class P2PMessageActivity extends BaseMessageActivity {
    @ViewInject(R.id.message_back_img)
    private ImageView mBackImg;
    @ViewInject(R.id.message_head_hiv)
    private HeadImageView mHeadHiv;
    @ViewInject(R.id.message_name_tv)
    private TextView mDoctorNameTv;
    @ViewInject(R.id.message_hospital_tv)
    private TextView mHospitalTv;
    @ViewInject(R.id.message_doctor_tab_fl)
    private FlowLayout mDoctorTabFl;

    private boolean isResume = false;

    private NIMPopupMenu popupMenu;
    private List<PopupMenuItem> menuItemList;
    private int[] mColors=new int[]{R.color.message_tab_color1,R.color.message_tab_color2,R.color.message_tab_color3,R.color.message_tab_color4};
    private ArrayList<String> mTabStrings=new ArrayList<>();

    public static void start(Context context, String contactId, SessionCustomization customization) {
        Intent intent = new Intent();
        intent.putExtra(Extras.EXTRA_ACCOUNT, contactId);
        intent.putExtra(Extras.EXTRA_CUSTOMIZATION, customization);
        intent.setClass(context, P2PMessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    public static void start(Context context, String contactId, SessionCustomization customization, Parcelable object) {
        Intent intent = new Intent();
        intent.putExtra(Extras.EXTRA_ACCOUNT, contactId);
        intent.putExtra(Extras.EXTRA_CUSTOMIZATION, customization);
        intent.setClass(context, P2PMessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        super.initView();
        // 单聊特例话数据，包括个人信息，
        registerObservers(true);

        initFlowLayout();

        LogUtils.i("对方账号:"+sessionId);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initActionbar() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerObservers(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResume = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isResume = false;
    }

    private void registerObservers(boolean register) {
        if (register) {
            registerUserInfoObserver();
        } else {
            unregisterUserInfoObserver();
        }
        FriendDataCache.getInstance().registerFriendDataChangedObserver(friendDataChangedObserver, register);
    }

    FriendDataCache.FriendDataChangedObserver friendDataChangedObserver = new FriendDataCache.FriendDataChangedObserver() {
        @Override
        public void onAddedOrUpdatedFriends(List<String> accounts) {
            // setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
        }

        @Override
        public void onDeletedFriends(List<String> accounts) {
            //  setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
        }

        @Override
        public void onAddUserToBlackList(List<String> account) {
            //   setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
        }

        @Override
        public void onRemoveUserFromBlackList(List<String> account) {
            // setTitle(UserInfoHelper.getUserTitleName(sessionId, SessionTypeEnum.P2P));
        }
    };

    private UserInfoObservable.UserInfoObserver uinfoObserver;

    private void registerUserInfoObserver() {
        if (uinfoObserver == null) {
            uinfoObserver = new UserInfoObservable.UserInfoObserver() {
                @Override
                public void onUserInfoChanged(List<String> accounts) {
                    if (accounts.contains(sessionId)) {

                    }
                }
            };
        }

        UserInfoHelper.registerObserver(uinfoObserver);
    }

    private void unregisterUserInfoObserver() {
        if (uinfoObserver != null) {
            UserInfoHelper.unregisterObserver(uinfoObserver);
        }
    }

    @Override
    protected MessageFragment fragment() {
        Bundle arguments = getIntent().getExtras();
        arguments.putSerializable(Extras.EXTRA_TYPE, SessionTypeEnum.P2P);
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(arguments);
        fragment.setContainerId(R.id.message_fragment_container);
        return fragment;
    }

    @Event(R.id.message_back_img)
    private void back(View view){
        AppManager.getAppManager().finishActivity();
    }

    @Event(R.id.message_head_hiv)
    private void doctorDetail(View view){

    }


    private void initFlowLayout() {
        mTabStrings.add("玻尿酸");
        mTabStrings.add("脂肪填充");
        mTabStrings.add("肉毒素");
        mTabStrings.add("水光针");
        mTabStrings.add("爱情小小小");

        for (int i = 0; i < mTabStrings.size(); i++) {
            TextView btn = new TextView(this);
            btn.setTextSize(13);
            btn.setPadding(8,3,8,3);
            btn.setGravity(Gravity.CENTER);
            btn.setTextColor(getResources().getColor(mColors[i%4]));
            btn.setBackgroundResource(R.drawable.shape_round_line_yellow_tv);

            GradientDrawable bgShape = (GradientDrawable)btn.getBackground();
            bgShape.setStroke(1,getResources().getColor(mColors[i%4]));
            btn.setText(mTabStrings.get(i));
            mDoctorTabFl.addView(btn);
        }

    }


}
