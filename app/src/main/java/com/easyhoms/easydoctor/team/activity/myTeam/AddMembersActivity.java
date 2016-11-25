package com.easyhoms.easydoctor.team.activity.myTeam;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.easyhoms.easydoctor.common.response.BaseArrayResp;
import com.easyhoms.easydoctor.common.utils.CharacterParser;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.LogUtils;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 添加成员
 */
@ContentView(R.layout.activity_add_members)
public class AddMembersActivity extends BaseActivity implements MemberCheckAdapter.CheckCallBack ,SearchLayout.SearchCallback {
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

    private MemberCheckAdapter mCheckAdapter;

    private ArrayList<Doctor> mChooseDoctors = new ArrayList<>();
    private ArrayList<Doctor> mDoctors = new ArrayList<>();
    private ArrayList<Doctor> mSearchDoctors = new ArrayList<>();
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
        mSearchSl.setCallback(this);
        mCharacterParser = CharacterParser.getInstance();
        mComparator = new PinyinComparator();
        mSortSb.setTextView(mNoticeTv);
        mGroupId=getIntent().getIntExtra(Constants.KEY_GROUP_ID,0);
        mCheckAdapter = new MemberCheckAdapter(mContext, mDoctors, this);
        mHospitalLv.setAdapter(mCheckAdapter);
        mDoctors.add(new Doctor("爱"));
        mDoctors.add(new Doctor("网"));
        mDoctors.add(new Doctor("学"));
        mDoctors.add(new Doctor("包"));
        mDoctors.add(new Doctor("123"));
        mDoctors.add(new Doctor("您"));
        mDoctors.add(new Doctor("值"));
        mDoctors.add(new Doctor("xxxx"));
        mDoctors.add(new Doctor("9098"));
        mDoctors.add(new Doctor("123"));
        mDoctors.add(new Doctor("爱"));
        mDoctors.add(new Doctor("网"));
        mDoctors.add(new Doctor("学"));
        mDoctors.add(new Doctor("包"));
        mDoctors.add(new Doctor("123"));
        mDoctors.add(new Doctor("您"));
        mDoctors.add(new Doctor("值"));
        mDoctors.add(new Doctor("xxxx"));
        mDoctors.add(new Doctor("9098"));
        mDoctors.add(new Doctor("123"));
        mDoctors.add(new Doctor("爱"));


        initdata();
        mCheckAdapter.setData(mDoctors);

    }

    @Override
    protected void initActionbar() {
        mMyActionbar.setRightTv(R.string.complete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mChooseDoctors.size()==0){
                    return;
                }
                showdialog();
                BaseManager.staffGroupAdd(mGroupId+"",mChooseDoctors.get(0).id+"",mAddStaffCallback);
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
        mMyActionbar.setVisibility(View.VISIBLE);
        backgroundAlpha(1f);
//        showdialog();
//        BaseManager.getDoctors(UserManager.getBindHos().id, mCallback);
    }

    //adapter回调
    @Override
    public void check(int position, boolean checked) {
        Doctor doctor = mDoctors.get(position);
        if (checked) {
            mChooseDoctors.add(doctor);
        } else {
            for (Doctor chooseDoctor : mChooseDoctors) {
                if (doctor.id == chooseDoctor.id) {
                    mChooseDoctors.remove(chooseDoctor);
                    break;
                }
            }
        }
        LogUtils.i("doctors size:" + mChooseDoctors.size());
    }


    //为ListView填充数据
    private void initdata() {
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
        Collections.sort(mDoctors,mComparator );
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
    public void fillData(String s) {

    }

    @Override
    public void cancel() {
        mMyActionbar.setVisibility(View.VISIBLE);
        mSourceFl.getBackground().setAlpha(255);

    }

    @Override
    public void showEditView() {
        mMyActionbar.setVisibility(View.GONE);
        backgroundAlpha(0.5f);

    }


    //设置添加屏幕的背景透明度
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
}
