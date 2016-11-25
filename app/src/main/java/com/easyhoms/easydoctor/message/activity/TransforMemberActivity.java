package com.easyhoms.easydoctor.message.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.Constants;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.manager.UserManager;
import com.easyhoms.easydoctor.common.response.BaseArrayResp;
import com.easyhoms.easydoctor.common.utils.CharacterParser;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.LocalTeamMemberSearch;
import com.easyhoms.easydoctor.common.utils.LogUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.utils.PinyinComparator;
import com.easyhoms.easydoctor.common.view.MyActionbar;
import com.easyhoms.easydoctor.common.view.SearchLayout;
import com.easyhoms.easydoctor.common.view.SideBar;
import com.easyhoms.easydoctor.message.adapter.TransforMemberAdapter;
import com.easyhoms.easydoctor.message.response.GroupMember;
import com.easyhoms.easydoctor.team.response.MyGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nimlib.sdk.team.model.TeamMember;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

@ContentView(R.layout.activity_transfor_member)
public class TransforMemberActivity extends BaseActivity implements SearchLayout.SearchCallback {
    @BindView(R.id.add_member_ma)
    MyActionbar mMyActionbar;
    @BindView(R.id.treat_empty_tv)
    private TextView mEmptyTv;
    @BindView(R.id.custom_search_ll)
    private SearchLayout mSearchSl;
    @BindView(R.id.treat_hospital_lv)
    private ListView mHospitalLv;
    @BindView(R.id.treat_catalog_tv)
    private TextView mCatlogTv;
    @BindView(R.id.treat_notice_tv)
    private TextView mNoticeTv;
    @BindView(R.id.hospital_name_tv)
    private TextView mNameTv;
    @BindView(R.id.treat_sort_sb)
    private SideBar mSortSb;
    @BindView(R.id.title_lv)
    private LinearLayout mTitleLl;
    @BindView(R.id.search_rl)
    private RelativeLayout mSearchRl;
    @BindView(R.id.soruce_fl)
    private FrameLayout mSourceFl;

    private TransforMemberAdapter mCheckAdapter;

    private long mGroupId;
    private String mYxTeamId;
    private MyGroup mMyGroup;
    private ArrayList<GroupMember> mGroupMembers=new ArrayList<>();
    private ArrayList<GroupMember> mSearchGroupMembers=new ArrayList<>();
    private ArrayList<TeamMember> mTeamMembers=new ArrayList<>();
    private int mLastFirstVisibleItem = -1;//上次第一个可见元素，用于滚动时记录标识。
    private CharacterParser mCharacterParser;//汉字转换成拼音的类
    private PinyinComparator mComparator;//根据拼音来排列ListView里面的数据类
    private NetCallback mMyGroupCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {

            if (CommonUtils.isResultOK(result)) {
                Type objectType = new TypeToken<BaseArrayResp<MyGroup>>() {
                }.getType();
                BaseArrayResp<MyGroup> res = new Gson().fromJson(result, objectType);
                if(res.content.size()!=0){
                    mMyGroup=res.content.get(0);
                    BaseManager.getMyGroupMembers(mMyGroup.id, mMemberCallback);
                }

            } else {
                closeDialog();
            }
        }

        @Override
        protected void timeOut() {

        }
    };
    private NetCallback mMemberCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                Type objectType = new TypeToken<BaseArrayResp<GroupMember>>() {
                }.getType();
                BaseArrayResp<GroupMember> res = new Gson().fromJson(result, objectType);
                mGroupMembers=res.content;
                initdata();
                mCheckAdapter.setData(mGroupMembers);
            } else {
                showToast(CommonUtils.getMsg(result));
            }
        }

        @Override
        protected void timeOut() {

        }
    };
    private NetCallback mAddStaffCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {

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
        mYxTeamId=getIntent().getStringExtra(Constants.KEY_YX_TEAM_ID);
        mTeamMembers= (ArrayList<TeamMember>) getIntent().getSerializableExtra(Constants.KEY_YX_TEAM_MEMBERS);
        mSearchSl.setCallback(this);
        mCharacterParser = CharacterParser.getInstance();
        mComparator = new PinyinComparator();
        mSortSb.setTextView(mNoticeTv);
        mGroupId = getIntent().getLongExtra(Constants.KEY_GROUP_ID, 0);
        mCheckAdapter = new TransforMemberAdapter(mContext, mGroupMembers);
        mHospitalLv.setAdapter(mCheckAdapter);


    }

    @Override
    protected void initActionbar() {
        mMyActionbar.setRightTv(R.string.complete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    protected void initListener() {

        // 设置右侧触摸监听
        mSortSb.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = mCheckAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mHospitalLv.setSelection(position);
                }
            }
        });
        mHospitalLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                int section = getSectionForPosition(firstVisibleItem);
                if (section == -1) {
                    return;
                }
                if (firstVisibleItem != mLastFirstVisibleItem) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mTitleLl
                            .getLayoutParams();
                    params.topMargin = 0;
                    mTitleLl.setLayoutParams(params);
                    mCatlogTv.setText(mGroupMembers.get(getPositionForSection(section)).sortLetters);
                }

                int nextSection = getSectionForPosition(firstVisibleItem + 1);
                int nextSecPosition = getPositionForSection(+nextSection);
                if (nextSection == -1) {
                    return;
                }
                if (nextSecPosition == firstVisibleItem + 1) {
                    View childView = view.getChildAt(0);
                    if (childView != null) {
                        int titleHeight = mTitleLl.getHeight();
                        int bottom = childView.getBottom();
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mTitleLl
                                .getLayoutParams();
                        if (bottom < titleHeight) {
                            float pushedDistance = bottom - titleHeight;
                            params.topMargin = (int) pushedDistance;
                            mTitleLl.setLayoutParams(params);
                        } else {
                            if (params.topMargin != 0) {
                                params.topMargin = 0;
                                mTitleLl.setLayoutParams(params);
                            }
                        }
                    }
                }
                mLastFirstVisibleItem = firstVisibleItem;
            }
        });

        mHospitalLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
                showAddStaffDlg();
                GroupMember member=mGroupMembers.get(position);
                BaseManager.addStaff(Constants.IM_DOCTOR+member.id,Long.valueOf(mYxTeamId), UserManager.getBindHos().id,mAddStaffCallback);
            }
        });
    }

    private void showAddStaffDlg() {
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mMyActionbar.setVisibility(View.VISIBLE);
//        backgroundAlpha(1f);
        showdialog();
        LogUtils.i("mGroupId "+mGroupId);
        BaseManager.getMyGroup(UserManager.getBindHos().id+"", mMyGroupCallback);
     //   BaseManager.getMy(UserManager.getUser(), mMyGroupCallback);

    }


    //为ListView填充数据
    private void initdata() {
        //删除已经存在的群成员
        for (TeamMember member : mTeamMembers) {
            LogUtils.i("原来成员"+member.getAccount());
            int size=mGroupMembers.size();
            for (int i = 0; i < size; i++) {
                GroupMember groupMember=mGroupMembers.get(i);
                LogUtils.i("组内成员"+groupMember.id);
                if(member.getAccount().replace(Constants.IM_DOCTOR,"").equals(groupMember.id+"")){
                    mGroupMembers.remove(i);
                    size--;
                }
            }
        }

        for (GroupMember friend : mGroupMembers) {
            // 汉字转换成拼音
            String pinyin = mCharacterParser.getSelling(friend.name);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                friend.sortLetters = sortString.toUpperCase();
            } else {
                friend.sortLetters = "#";
            }
        }

        // 根据a-z进行排序源数据
        Collections.sort(mGroupMembers, mComparator);
    }

    //根据ListView的当前位置获取分类的首字母的Char ascii值
    public int getSectionForPosition(int position) {
        if (mGroupMembers.size() <= position || mGroupMembers.size() == 0) {
            return -1;
        }

        return mGroupMembers.get(position).sortLetters.charAt(0);
    }

    //根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
    public int getPositionForSection(int section) {
        for (int i = 0; i < mGroupMembers.size(); i++) {
            String sortStr = mGroupMembers.get(i).sortLetters;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public void fillData(String s) {
        ArrayList<GroupMember> filterDateList = LocalTeamMemberSearch.searchGroup(s, mGroupMembers);
        mSearchGroupMembers=filterDateList;
        mCheckAdapter.setData(mSearchGroupMembers);
    }

    @Override
    public void cancel() {
        mMyActionbar.setVisibility(View.VISIBLE);
        mMyActionbar.setVisibility(View.VISIBLE);
        mCheckAdapter.setData(mGroupMembers);
    }

    @Override
    public void showEditView() {
        mMyActionbar.setVisibility(View.GONE);
        mMyActionbar.setVisibility(View.GONE);
        mCheckAdapter.setData(new ArrayList<GroupMember>());
    }



}