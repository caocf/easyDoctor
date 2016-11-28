package com.easyhoms.easydoctor.team.fragment;


import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.fragment.BaseFragment;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.manager.UserManager;
import com.easyhoms.easydoctor.common.response.BaseArrayResp;
import com.easyhoms.easydoctor.common.response.Hospital;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.view.NoScrollExpandableListView;
import com.easyhoms.easydoctor.common.view.NoScrollListView;
import com.easyhoms.easydoctor.common.view.SearchLayout;
import com.easyhoms.easydoctor.message.listener.OnItemClickListener;
import com.easyhoms.easydoctor.team.activity.doctor.DoctorDataActivity;
import com.easyhoms.easydoctor.team.activity.myTeam.TeamManagerActivity;
import com.easyhoms.easydoctor.team.activity.user.UserMoreActivity;
import com.easyhoms.easydoctor.team.adapter.MedicalRecordExpandAdapter;
import com.easyhoms.easydoctor.team.adapter.StoreUserAdapter;
import com.easyhoms.easydoctor.team.adapter.TeamMemberAdapter;
import com.easyhoms.easydoctor.team.response.Doctor;
import com.easyhoms.easydoctor.team.response.Group;
import com.easyhoms.easydoctor.team.response.MyGroup;
import com.easyhoms.easydoctor.team.response.PatientByGroup;
import com.easyhoms.easydoctor.team.response.StoreUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.lang.reflect.Type;
import java.util.ArrayList;


@ContentView(R.layout.fragment_team)
public class TeamFragment extends BaseFragment implements MedicalRecordExpandAdapter.GroupClick {
    @BindView(R.id.my_team_ll)
    LinearLayout mMyTeamLl;
    @BindView(R.id.team_sl)
    SearchLayout mTeamSl;

    @BindView(R.id.textView2)
    TextView mTextView2;
    @BindView(R.id.store_selected_img)
    ImageView mStoreSelectedImg;
    @BindView(R.id.my_team_selected_img)
    ImageView mTeamselectedImg;
    @BindView(R.id.my_store_ll)
    LinearLayout mMyStoreLl;
    @BindView(R.id.team_manager_tv)
    TextView mTeamManagertv;
    @BindView(R.id.user_swip_smrv)
    SwipeMenuRecyclerView mStoreSmrv;
    @BindView(R.id.my_team_nslv)
    NoScrollListView mMyTeamNslv;

    @BindView(R.id.other_team_elv)
    NoScrollExpandableListView mOtherTeamElv;

    private Hospital mHospital;
    private MyGroup mMyGroup;
    private ArrayList<Doctor> mMyTeamDoctors=new ArrayList<>();
    private TeamMemberAdapter mMyTeamAdapter;
    private StoreUserAdapter mUserAdapter;
    private ArrayList<StoreUser> mStoreUsers=new ArrayList<>();
    private MedicalRecordExpandAdapter mAdapter;
    private ArrayList<Group> mGroups = new ArrayList<>();
    private ArrayList<ArrayList<PatientByGroup>> mPatients = new ArrayList<>();
    private ArrayList<PatientByGroup> mPatientParamses = new ArrayList<>();
    private ArrayList<String> mGroupNames;
    private boolean mStoreSelected=false;
    private boolean mMyTeamSelected=false;

    private NetCallback mOtherGroupCallback = new NetCallback(mContext) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                Type objectType = new TypeToken<BaseArrayResp<Group>>() {
                }.getType();
                BaseArrayResp<Group> res = new Gson().fromJson(result, objectType);

            } else {

            }
        }

        @Override
        protected void timeOut() {

        }

    };

    private NetCallback mItemCallback = new NetCallback(mContext) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                Type objectType = new TypeToken<BaseArrayResp<PatientByGroup>>() {
                }.getType();
                BaseArrayResp<PatientByGroup> res = new Gson().fromJson(result, objectType);


            } else {

            }
        }

        @Override
        protected void timeOut() {

        }
    };

    private NetCallback mCallback = new NetCallback(getActivity()) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                Type objectType = new TypeToken<BaseArrayResp<MyGroup>>() {
                }.getType();
                BaseArrayResp<MyGroup> res = new Gson().fromJson(result, objectType);
                mMyGroup = res.content.get(0);

            } else {

            }
        }

        @Override
        protected void timeOut() {

        }
    };
    private NetCallback mJoinedCallback = new NetCallback(getActivity()) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                Type objectType = new TypeToken<BaseArrayResp<MyGroup>>() {
                }.getType();
                BaseArrayResp<MyGroup> res = new Gson().fromJson(result, objectType);
                mMyGroup = res.content.get(0);

            } else {

            }
        }

        @Override
        protected void timeOut() {

        }
    };
    private NetCallback mFavoriteCallback = new NetCallback(getActivity()) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {


            } else {

            }
        }

        @Override
        protected void timeOut() {

        }
    };
    private NetCallback mGroupCallback = new NetCallback(getActivity()) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {

            } else {

            }
        }

        @Override
        protected void timeOut() {

        }
    };

    public TeamFragment() {
    }

    @Override
    protected void initView() {
        mHospital = UserManager.getBindHos();

        mStoreUsers.add(new StoreUser("zhang"));
        mStoreUsers.add(new StoreUser("222"));
        mStoreUsers.add(new StoreUser("333"));
        mStoreUsers.add(new StoreUser("456"));

        mUserAdapter = new StoreUserAdapter(getActivity(), mStoreUsers);
        mStoreSmrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mStoreSmrv.setHasFixedSize(true);
        mStoreSmrv.setItemAnimator(new DefaultItemAnimator());
       // mStoreSmrv.addItemDecoration(new ListViewDecoration());
        mStoreSmrv.setSwipeMenuCreator(swipeMenuCreator);
        mStoreSmrv.setSwipeMenuItemClickListener(menuItemClickListener);

        mUserAdapter.setOnItemClickListener(onItemClickListener);
        mStoreSmrv.setAdapter(mUserAdapter);

        //其他team
        mAdapter = new MedicalRecordExpandAdapter(mContext, mGroups, mPatients);
        mAdapter.setGroupClick(this);
        mOtherTeamElv.setAdapter(mAdapter);
        mOtherTeamElv.setGroupIndicator(null);

        mGroupNames = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            mGroups.add(new Group(i+"1","李医生"+i));
        }
        initGroup();

        mMyTeamDoctors.add(new Doctor("张先生"));
        mMyTeamDoctors.add(new Doctor("先生"));
        mMyTeamDoctors.add(new Doctor("张生"));
        mMyTeamDoctors.add(new Doctor("立先生"));
        mMyTeamAdapter=new TeamMemberAdapter(mContext,mMyTeamDoctors);
        mMyTeamNslv.setAdapter(mMyTeamAdapter);

    }

    @Override
    protected void initListener() {

    }

    @Event(R.id.my_team_ll)
    private void queryTeam(View view) {
        if (mMyTeamSelected) {
            mMyTeamNslv.setVisibility(View.GONE);
        }else{
            mMyTeamNslv.setVisibility(View.VISIBLE);
        }

        mMyTeamSelected=!mMyTeamSelected;
        mTeamselectedImg.setSelected(mMyTeamSelected);
//        showLoginDlg();
//        BaseManager.getMyGroupMembers(mMyGroup.id + "", mGroupCallback);
    }

    @Event(R.id.my_store_ll)
    private void queryStore(View view) {
        if(mStoreSelected){
            mStoreSmrv.setVisibility(View.GONE);
//            showLoginDlg();
//            BaseManager.getFavorites(mFavoriteCallback);

        }else{
            mStoreSmrv.setVisibility(View.VISIBLE);
        }

        mStoreSelected=!mStoreSelected;
        mStoreSelectedImg.setSelected(mStoreSelected);
    }

    @Event(R.id.team_manager_tv)
    private void teamManager(View view) {
        Intent intent = new Intent(getActivity(), TeamManagerActivity.class);
//        intent.putExtra(Constants.KEY_GROUP_ID, mMyGroup.id);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
     //   BaseManager.getMyGroup(mHospital.id + "", mCallback);
        //查询其他队伍
        BaseManager.getJoinedGroup(UserManager.getUser().id+"",mJoinedCallback);

    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Intent intent = new Intent(getContext(), UserMoreActivity.class);
            startActivity(intent);
        }
    };

    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {

        }
    };

    /**
     * 菜单创建器。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.height_70);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            SwipeMenuItem wechatItem = new SwipeMenuItem(mContext)
                    .setText(R.string.remark)
                    .setTextColor(getResources().getColor(R.color.white))
                    .setBackgroundColor(getResources().getColor(R.color.bg_remark))
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(wechatItem);

        }
    };

    //初始化群组
    private void initGroup() {
        ArrayList<PatientByGroup> item1 = new ArrayList<>();

        for (Group group : mGroups) {
            mPatients.add(item1);
        }
        mAdapter.setData(mGroups, mPatients);
        for (int i = 0; i < mGroups.size(); i++) {
            mOtherTeamElv.collapseGroup(i);//全部收缩
        }
        mGroupNames.clear();
        for (Group group : mGroups) {
            if (group.groupName != null) {
                mGroupNames.add(group.groupName);
            }
        }
    }

    @Override
    public void groupLongClick(int position) {

    }

    @Override
    public void groupClick(int position, boolean isExpanded) {

        if (isExpanded) {//已经展开
            mOtherTeamElv.collapseGroup(position);
        } else {
            mOtherTeamElv.expandGroup(position);
        }

        if (mOtherTeamElv.isGroupExpanded(position)) {
            //查询
            mPatientParamses=new ArrayList<>();
            mPatientParamses.add(new PatientByGroup("张"));
            mPatientParamses.add(new PatientByGroup("李"));
            mPatientParamses.add(new PatientByGroup("张2"));
            mPatients.set(position, mPatientParamses);
            mAdapter.setData(mGroups,mPatients);
        }
    }
}
