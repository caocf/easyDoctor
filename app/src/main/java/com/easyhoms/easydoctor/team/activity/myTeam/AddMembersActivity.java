package com.easyhoms.easydoctor.team.activity.myTeam;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
import com.easyhoms.easydoctor.common.utils.LocalDoctorSearch;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.utils.PinyinComparator;
import com.easyhoms.easydoctor.common.view.MyActionbar;
import com.easyhoms.easydoctor.common.view.SearchLayout;
import com.easyhoms.easydoctor.common.view.SideBar;
import com.easyhoms.easydoctor.team.adapter.MemberCheckAdapter;
import com.easyhoms.easydoctor.team.response.Doctor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 添加成员
 */
@ContentView(R.layout.activity_add_members)
public class AddMembersActivity extends BaseActivity implements MemberCheckAdapter.CheckCallBack, SearchLayout.SearchCallback {
    @BindView(R.id.add_member_ma)
    MyActionbar mMyActionbar;

    @BindView(R.id.treat_empty_tv)
    private TextView mEmptyTv;
    @BindView(R.id.num_confirm_tv)
    private TextView mConfirmTv;
    @BindView(R.id.choose_num_tv)
    private TextView mChooseNumTv;
    @BindView(R.id.custom_search_ll)
    private SearchLayout mSearchSl;
    @BindView(R.id.treat_hospital_lv)
    private ListView mHospitalLv;
    @BindView(R.id.treat_catalog_tv)
    private TextView mCatlogTv;
    @BindView(R.id.treat_notice_tv)
    private TextView mNoticeTv;
    @BindView(R.id.treat_sort_sb)
    private SideBar mSortSb;
    @BindView(R.id.title_lv)
    private LinearLayout mTitleLl;
    @BindView(R.id.search_rl)
    private RelativeLayout mSearchRl;
    @BindView(R.id.soruce_fl)
    private FrameLayout mSourceFl;

    private boolean mIsSearch=false;
    private MemberCheckAdapter mCheckAdapter;

    private ArrayList<Doctor> mChooseDoctors = new ArrayList<>();
    private ArrayList<Doctor> mDoctors = new ArrayList<>();
    private ArrayList<Doctor> mSearchDoctors = new ArrayList<>();
    private ArrayList<Doctor> mExitDoctors = new ArrayList<>();
    private int mGroupId;
    private int mLastFirstVisibleItem = -1;//上次第一个可见元素，用于滚动时记录标识。
    private CharacterParser mCharacterParser;//汉字转换成拼音的类
    private PinyinComparator mComparator;//根据拼音来排列ListView里面的数据类
    private NetCallback mCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                Type objectType = new TypeToken<BaseArrayResp<Doctor>>() {
                }.getType();
                BaseArrayResp<Doctor> res = new Gson().fromJson(result, objectType);
                mDoctors = res.content;
                initdata();
                mCheckAdapter.setData(mDoctors);

            } else {

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
                showToast(R.string.add_staff_ok);
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
        mGroupId = getIntent().getIntExtra(Constants.KEY_GROUP_ID, 0);
        mExitDoctors = getIntent().getParcelableArrayListExtra(Constants.KEY_GROUP_DOCTOR);
        mSearchSl.setCallback(this);
        mCharacterParser = CharacterParser.getInstance();
        mComparator = new PinyinComparator();
        mSortSb.setTextView(mNoticeTv);

        mCheckAdapter = new MemberCheckAdapter(mContext, mDoctors, this, true);
        mHospitalLv.setAdapter(mCheckAdapter);

        mCheckAdapter.setData(mDoctors);

    }

    @Override
    protected void initActionbar() {
        String str=getString(R.string.add_member)+"\n"+UserManager.getBindHos().companyName;
        mMyActionbar.setSubTitle(str,4);
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
                    mCatlogTv.setText(mDoctors.get(getPositionForSection(section)).sortLetters);
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


    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseManager.getDoctors(UserManager.getBindHos().id, mCallback);
    }

    //adapter回调
    @Override
    public void check(int position) {
        if(mIsSearch){
            Doctor doctor = mSearchDoctors.get(position);
            boolean checked=mSearchDoctors.get(position).isChoose;
            if (!checked) {
                mChooseDoctors.add(doctor);
            } else {
                for (Doctor chooseDoctor : mChooseDoctors) {
                    if (doctor.id == chooseDoctor.id) {
                        mChooseDoctors.remove(chooseDoctor);
                        break;
                    }
                }
            }
            mSearchDoctors.get(position).isChoose=!checked;
            mCheckAdapter.setData(mSearchDoctors);
        }else{
            Doctor doctor = mDoctors.get(position);
            boolean checked=mDoctors.get(position).isChoose;
            if (!checked) {
                mChooseDoctors.add(doctor);
            } else {
                for (Doctor chooseDoctor : mChooseDoctors) {
                    if (doctor.id == chooseDoctor.id) {
                        mChooseDoctors.remove(chooseDoctor);
                        break;
                    }
                }
            }
            mDoctors.get(position).isChoose=!checked;
            mCheckAdapter.setData(mDoctors);
        }
        mChooseNumTv.setText(mChooseDoctors.size()+"");
        mChooseNumTv.setVisibility(mChooseDoctors.size()==0?View.INVISIBLE:View.VISIBLE);
    }


    //为ListView填充数据
    private void initdata() {
        for (Doctor exitDoctor : mExitDoctors) {
            int size = mDoctors.size();
            for (int i = 0; i < size; i++) {
                Doctor doctor = mDoctors.get(i);
                if (exitDoctor.id==doctor.id) {
                    mDoctors.remove(i);
                    size--;
                }
            }
        }
        for (Doctor friend : mDoctors) {
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
        Collections.sort(mDoctors, mComparator);
    }

    //根据ListView的当前位置获取分类的首字母的Char ascii值
    public int getSectionForPosition(int position) {
        if (mDoctors.size() <= position || mDoctors.size() == 0) {
            return -1;
        }

        return mDoctors.get(position).sortLetters.charAt(0);
    }

    //根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
    public int getPositionForSection(int section) {
        for (int i = 0; i < mDoctors.size(); i++) {
            String sortStr = mDoctors.get(i).sortLetters;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public void fillData(String filterStr) {
        ArrayList<Doctor> filterDateList = LocalDoctorSearch.searchGroup(filterStr, mDoctors);
        mSearchDoctors =filterDateList;
        mCheckAdapter.setData(mSearchDoctors);
    }

    @Override
    public void cancel() {
        mIsSearch=false;
        mCheckAdapter.setSessionVisible(true);
        mCatlogTv.setVisibility(View.VISIBLE);
        for (Doctor searhDoctor : mSearchDoctors) {
            if (searhDoctor.isChoose) {
                for (int i = 0; i < mDoctors.size(); i++) {
                    Doctor doctor=mDoctors.get(i);
                    if (searhDoctor.id==doctor.id) {
                        mDoctors.get(i).isChoose=true;
                    }
                }
            }
        }
        mMyActionbar.setVisibility(View.VISIBLE);
        mCheckAdapter.setData(mDoctors);
    }

    @Override
    public void showEditView() {
        mIsSearch=true;
        mCatlogTv.setVisibility(View.GONE);
        mCheckAdapter.setSessionVisible(false);
        mMyActionbar.setVisibility(View.GONE);
        mCheckAdapter.setData(new ArrayList<Doctor>());
    }

    @Event(R.id.num_confirm_tv)
    private void addMember(View view) {

        if (mChooseDoctors.size() != 1) {
            showToast("因系统原因,有且只能选择1个成员,之后再做修改");
            return;
        }
        showdialog();
        BaseManager.staffGroupAdd(mGroupId + "", mChooseDoctors.get(0).id + "", mAddStaffCallback);
    }

}
