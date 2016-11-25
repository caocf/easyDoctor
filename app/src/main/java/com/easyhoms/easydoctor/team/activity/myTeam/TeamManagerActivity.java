package com.easyhoms.easydoctor.team.activity.myTeam;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.easyhoms.easydoctor.Constants;
import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;
import com.easyhoms.easydoctor.common.response.BaseArrayResp;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.utils.NetCallback;
import com.easyhoms.easydoctor.common.view.MyActionbar;
import com.easyhoms.easydoctor.message.listener.OnItemClickListener;
import com.easyhoms.easydoctor.message.view.ListViewDecoration;
import com.easyhoms.easydoctor.team.adapter.MenuAdapter;
import com.easyhoms.easydoctor.team.response.Doctor;
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

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 我的团队管理
 */
@ContentView(R.layout.activity_team_manager)
public class TeamManagerActivity extends BaseActivity {

    @BindView(R.id.team_manager_ma)
    MyActionbar mMyActionbar;
    @BindView(R.id.team_member_lv)
    SwipeMenuRecyclerView mMemberLv;

    private int mGroupId;
    private ArrayList<Doctor> mDoctors = new ArrayList<>();
    private MenuAdapter mMemberAdapter;
    private NetCallback mGroupCallback = new NetCallback(this) {
        @Override
        protected void requestOK(String result) {
            closeDialog();
            if (CommonUtils.isResultOK(result)) {
                Type objectType = new TypeToken<BaseArrayResp<Doctor>>() {
                }.getType();
                BaseArrayResp<Doctor> res = new Gson().fromJson(result, objectType);
                mDoctors = res.content;
                mMemberAdapter.setData(mDoctors);
            } else {

            }
        }

        @Override
        protected void timeOut() {

        }
    };

    @Override
    protected void initView() {
        mGroupId = getIntent().getIntExtra(Constants.KEY_GROUP_ID, 0);
        mDoctors.add(new Doctor("zhang"));
        mDoctors.add(new Doctor("来"));
        mDoctors.add(new Doctor("爱"));
        mDoctors.add(new Doctor("学"));
        mMemberAdapter=new MenuAdapter(mContext,mDoctors);

        mMemberLv.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        mMemberLv.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mMemberLv.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        mMemberLv.addItemDecoration(new ListViewDecoration());// 添加分割线。

        // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
        // 设置菜单创建器。
        mMemberLv.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        mMemberLv.setSwipeMenuItemClickListener(menuItemClickListener);

        mMemberAdapter.setOnItemClickListener(onItemClickListener);
        mMemberLv.setAdapter(mMemberAdapter);
    }

    @Override
    protected void initActionbar() {

    }

    @Override
    protected void initListener() {
        mMyActionbar.setRightTv(R.string.add_member, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,AddMembersActivity.class);
              //  intent.putExtra(Constants.KEY_GROUP_ID,mGroupId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
     //   showdialog();
     //   BaseManager.getMyGroupMembers(mGroupId + "", mGroupCallback);
    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.height_66);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                        .setBackgroundColor(getResources().getColor(R.color.letter_red))
                        .setText(R.string.remove) // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(getResources().getColor(R.color.white))
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

            }
        }
    };

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

        }
    };

    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView#RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

        }
    };


}
