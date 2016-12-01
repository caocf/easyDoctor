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
import com.easyhoms.easydoctor.common.bean.FavoritePatient;
import com.easyhoms.easydoctor.common.fragment.BaseFragment;
import com.easyhoms.easydoctor.common.manager.BaseManager;
import com.easyhoms.easydoctor.common.manager.UserManager;
import com.easyhoms.easydoctor.common.response.BaseArrayResp;
import com.easyhoms.easydoctor.common.response.Hospital;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.view.SearchLayout;
import com.easyhoms.easydoctor.message.listener.OnItemClickListener;
import com.easyhoms.easydoctor.message.view.ListViewDecoration;
import com.easyhoms.easydoctor.team.activity.myTeam.AllTeamActivity;
import com.easyhoms.easydoctor.team.activity.user.UserDataActivity;
import com.easyhoms.easydoctor.team.adapter.StoreUserAdapter;
import com.easyhoms.easydoctor.team.response.MyGroup;
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
public class TeamFragment extends BaseFragment {
    @BindView(R.id.team_ll)
    LinearLayout mMyTeamLl;
    @BindView(R.id.favite_ll)
    LinearLayout mFaviteLl;
    @BindView(R.id.team_sl)
    SearchLayout mTeamSl;
    @BindView(R.id.swipe_v)
    View mSwipV;
    @BindView(R.id.favite_tv)
    TextView mFaviteTv;
    @BindView(R.id.my_team_selected_img)
    ImageView mTeamselectedImg;
    @BindView(R.id.user_swip_smrv)
    SwipeMenuRecyclerView mStoreSmrv;

    private Hospital mHospital;
    private MyGroup mMyGroup;

    private StoreUserAdapter mUserAdapter;
    private ArrayList<FavoritePatient> mStoreUsers = new ArrayList<>();

    private NetCallback mFavoriteCallback = new NetCallback(getActivity()) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                Type objectType = new TypeToken<BaseArrayResp<FavoritePatient>>() {
                }.getType();
                BaseArrayResp<FavoritePatient> res = new Gson().fromJson(result, objectType);

                mStoreUsers=res.content;
                mFaviteLl.setVisibility(mStoreUsers.size()==0?View.GONE:View.VISIBLE);
                mUserAdapter.setData(mStoreUsers);
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

        mUserAdapter = new StoreUserAdapter(getActivity(), mStoreUsers);
        mStoreSmrv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mStoreSmrv.setHasFixedSize(true);
        mStoreSmrv.setItemAnimator(new DefaultItemAnimator());
        mStoreSmrv.addItemDecoration(new ListViewDecoration());
        mStoreSmrv.setSwipeMenuCreator(swipeMenuCreator);
        mStoreSmrv.setSwipeMenuItemClickListener(menuItemClickListener);

        mUserAdapter.setOnItemClickListener(onItemClickListener);
        mStoreSmrv.setAdapter(mUserAdapter);

    }

    @Override
    protected void initListener() {

    }

    @Event(R.id.team_ll)
    private void teamManager(View view) {
        Intent intent = new Intent(getActivity(), AllTeamActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        BaseManager.getFavorites(mFavoriteCallback);
    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Intent intent=new Intent(mContext(), UserDataActivity.class);
            startActivity(intent);
        }
    };

    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {

        }
    };

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


}
