package com.easyhoms.easydoctor.team.activity.myTeam;

import android.content.Intent;
import android.view.View;

import com.easyhoms.easydoctor.Constants;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.manager.UserManager;
import com.easyhoms.easydoctor.common.response.BaseArrayResp;
import com.easyhoms.easydoctor.common.response.Hospital;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.view.MyActionbar;
import com.easyhoms.easydoctor.common.view.NoScrollListView;
import com.easyhoms.easydoctor.common.view.TeamItem;
import com.easyhoms.easydoctor.team.adapter.OtherTeamAdapter;
import com.easyhoms.easydoctor.team.response.MyGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.lang.reflect.Type;
import java.util.ArrayList;




@ContentView(R.layout.activity_show_all_team)
public class ShowAllTeamActivity extends BaseActivity {
    @BindView(R.id.all_team_ma)
    MyActionbar mAllTeamMa;
    @BindView(R.id.create_ti)
    TeamItem mCreateTi;
    @BindView(R.id.my_team_ti)
    TeamItem mMyTeamTi;
    @BindView(R.id.team_lv)
    NoScrollListView mOtherTeamLv;

    private Hospital mHospital;
    private MyGroup mMyGroup;
    private ArrayList<MyGroup> mOtherGroups = new ArrayList<>();
    private OtherTeamAdapter mOtherTeamAdapter;
    private NetCallback mCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                Type objectType = new TypeToken<BaseArrayResp<MyGroup>>() {
                }.getType();
                BaseArrayResp<MyGroup> res = new Gson().fromJson(result, objectType);
                mMyGroup = res.content.get(0);
                refreshMyGroup();
            } else {

            }
        }

        @Override
        protected void timeOut() {

        }
    };

    private void refreshMyGroup() {
        if (mMyGroup != null) {
            mMyTeamTi.setRightTv(R.string.my_Team);
            mMyTeamTi.setImgSrc(R.drawable.icon_team);
        }
        mMyTeamTi.setVisibility(mMyGroup != null ? View.VISIBLE : View.GONE);
        mCreateTi.setVisibility(mMyGroup != null ? View.GONE : View.VISIBLE);
    }

    private NetCallback mJoinedCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                Type objectType = new TypeToken<BaseArrayResp<MyGroup>>() {
                }.getType();
                BaseArrayResp<MyGroup> res = new Gson().fromJson(result, objectType);
                mOtherGroups = res.content;
                mOtherTeamAdapter.setData(mOtherGroups);

            } else {

            }
        }

        @Override
        protected void timeOut() {

        }
    };

    @Override
    protected void initView() {
        mOtherTeamAdapter=new OtherTeamAdapter(this,mOtherGroups);
        mOtherTeamLv.setAdapter(mOtherTeamAdapter);
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
        mHospital = UserManager.getBindHos();
        BaseManager.getMyGroup(mHospital.id + "", mCallback);

        //查询其他队伍
        BaseManager.getJoinedGroup(UserManager.getUser().id + "", mJoinedCallback);
    }

    @Event(R.id.create_ti)
    private void createTeam(View view) {
        Intent intent=new Intent(mContext, CreateMyTeamActivity.class);
        startActivity(intent);
    }

    @Event(R.id.my_team_ti)
    private void myTeam(View view) {
        Intent intent=new Intent(mContext, MyTeamActivity.class);
        intent.putExtra(Constants.KEY_GROUP_ID,mMyGroup.id);
        startActivity(intent);
    }

}
