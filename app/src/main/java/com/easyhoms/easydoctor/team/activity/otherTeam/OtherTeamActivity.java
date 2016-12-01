package com.easyhoms.easydoctor.team.activity.otherTeam;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.easyhoms.easydoctor.Constants;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.manager.UserManager;
import com.easyhoms.easydoctor.common.response.BaseArrayResp;
import com.easyhoms.easydoctor.common.response.BaseResp;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.view.MyActionbar;
import com.easyhoms.easydoctor.common.view.SearchLayout;
import com.easyhoms.easydoctor.team.adapter.TeamMemberAdapter;
import com.easyhoms.easydoctor.team.response.Doctor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.lang.reflect.Type;
import java.util.ArrayList;


@ContentView(R.layout.activity_other_team)
public class OtherTeamActivity extends BaseActivity {

    @BindView(R.id.team_manager_ma)
    MyActionbar mTeamManagerMa;
    @BindView(R.id.team_sl)
    SearchLayout mTeamSl;
    @BindView(R.id.team_member_lv)
    ListView mTeamMemberLv;
    @BindView(R.id.exit_tv)
    TextView mExitTv;

    private TeamMemberAdapter mMemberAdapter;
    private ArrayList<Doctor> mDoctors=new ArrayList<>();
    private long mTeamId;
    private NetCallback mCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                Type objectType = new TypeToken<BaseArrayResp<Doctor>>() {
                }.getType();
                BaseArrayResp<Doctor> res = new Gson().fromJson(result, objectType);
                mDoctors=res.content;
                mMemberAdapter.setData(mDoctors);

            } else {

            }
        }
        @Override
        protected void timeOut() {

        }
    };
    private NetCallback mDelCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                Type objectType = new TypeToken<BaseResp<String>>() {
                }.getType();
                BaseResp<String> res = new Gson().fromJson(result, objectType);


            } else {

            }
        }
        @Override
        protected void timeOut() {

        }
    };
    @Override
    protected void initView() {
        mTeamId=getIntent().getIntExtra(Constants.KEY_GROUP_ID,0);
        mMemberAdapter=new TeamMemberAdapter(mContext,mDoctors);
        mTeamMemberLv.setAdapter(mMemberAdapter);
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
        BaseManager.getMembers(mTeamId, UserManager.getBindHos().id,mCallback);
    }

    @Event(R.id.exit_tv)
    private void exitTeam(View view) {
         BaseManager.staffGroupDel(mTeamId+"",UserManager.getUser().id+"",mDelCallback);
    }

}
