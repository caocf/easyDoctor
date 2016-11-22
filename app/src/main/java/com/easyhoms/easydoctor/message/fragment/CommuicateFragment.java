package com.easyhoms.easydoctor.message.fragment;


import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.fragment.BaseFragment;
import com.easyhoms.easydoctor.common.utils.LocalSearch;
import com.easyhoms.easydoctor.common.utils.LogUtils;
import com.easyhoms.easydoctor.common.view.MyActionbar;
import com.easyhoms.easydoctor.common.view.SearchLayout;
import com.easyhoms.easydoctor.message.adapter.RecentContactAdapter;
import com.easyhoms.easydoctor.message.adapter.SearchRecentContactAdapter;
import com.easyhoms.easydoctor.message.listener.OnItemClickListener;
import com.easyhoms.easydoctor.message.view.ListViewDecoration;
import com.netease.nim.cache.FriendDataCache;
import com.netease.nim.reminder.ReminderManager;
import com.netease.nim.session.SessionHelper;
import com.netease.nim.session.activity.P2PMessageActivity;
import com.netease.nim.session.activity.TeamMessageActivity;
import com.netease.nim.uinfo.UserInfoHelper;
import com.netease.nim.uinfo.UserInfoObservable;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.xutils.view.annotation.BindView;
import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 消息fragment
 */
@ContentView(R.layout.fragment_commuicate)
public class CommuicateFragment extends BaseFragment implements SearchLayout.SearchCallback{
    // 置顶功能可直接使用，也可作为思路，供开发者充分利用RecentContact的tag字段
    public static final long RECENT_TAG_STICKY = 1; // 联系人置顶tag
    public static final String SYSTEM_NOTICE = "0"; // 联系人置顶tag
    @BindView(R.id.search_recent_sl)
    SearchLayout mSearchRecentSl;
    @BindView(R.id.search_lv)
    ListView mSearchLv;
    @BindView(R.id.communicate_recent_lv)
    SwipeMenuRecyclerView swipeMenuRecyclerView;
    @BindView(R.id.message_null_bg_tv)
    TextView mNullBgTv;
    @BindView(R.id.communicate_ma)
    MyActionbar mMyActionbar;

    private ArrayList<RecentContact> items;
    private List<RecentContact> loadedRecents;
    private ArrayList<RecentContact> mSearchRecents=new ArrayList<>();
    private RecentContactAdapter adapter;
    private SearchRecentContactAdapter mSearchAdapter;
    private boolean msgLoaded = false;

    private UnreadCallback mUnreadCallback;

    public void setUnreadCallback(UnreadCallback unreadCallback) {
        mUnreadCallback = unreadCallback;
    }

    private UserInfoObservable.UserInfoObserver userInfoObserver;



    public CommuicateFragment() {
    }

    @Override
    protected void initView() {
        initMessageList();
        initSwipeMenu();
        //获取所有的会话列表
        requestMessages(true);//延迟
        registerObservers(true);
    }

    @Override
    protected void initListener() {
        mMyActionbar.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        registerObservers(false);
    }

    /**
     * 设置侧滑删除
     */
    private void initSwipeMenu() {

    }

    /**
     * 初始化消息列表
     */
    private void initMessageList() {
        items = new ArrayList<>();

        adapter = new RecentContactAdapter(getActivity(), items);
        swipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeMenuRecyclerView.setHasFixedSize(true);
        swipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
        swipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());
        swipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        swipeMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);

        adapter.setOnItemClickListener(onItemClickListener);

        swipeMenuRecyclerView.setAdapter(adapter);

        mSearchAdapter=new SearchRecentContactAdapter(mContext,mSearchRecents);
        mSearchLv.setAdapter(mSearchAdapter);

        mSearchRecentSl.setCallback(this);

        //点击事件
        mSearchLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecentContact recent = items.get(position);
                if (recent.getSessionType() == SessionTypeEnum.P2P) {
                    P2PMessageActivity.start(getActivity(), recent.getContactId(), SessionHelper.getTextP2pCustomization());
                } else {
                    LogUtils.i(recent.getContactId());
                    TeamMessageActivity.start(getActivity(), recent.getContactId(), SessionHelper.getTeamCustomization(), null, null);
                }
            }
        });

    }

    private void requestMessages(boolean delay) {
        if (msgLoaded) {
            return;
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (msgLoaded) {
                    return;
                }
                // 查询最近联系人列表数据
                NIMClient.getService(MsgService.class).queryRecentContacts().setCallback(new RequestCallbackWrapper<List<RecentContact>>() {

                    @Override
                    public void onResult(int code, List<RecentContact> recents, Throwable exception) {
                        if (code != ResponseCode.RES_SUCCESS || recents == null) {
                            return;
                        }
                        loadedRecents = recents;

                        // 此处如果是界面刚初始化，为了防止界面卡顿，可先在后台把需要显示的用户资料和群组资料在后台加载好，然后再刷新界面
                        //
                        msgLoaded = true;
                        if (isAdded()) {
                            onRecentContactsLoaded();
                        }
                    }
                });
            }
        }, delay ? 250 : 0);
    }

    private void onRecentContactsLoaded() {
        items.clear();
        if (loadedRecents != null) {
            items.addAll(loadedRecents);
            loadedRecents = null;
        }
        refreshMessages(true);
    }

    private void refreshMessages(boolean unreadChanged) {
        sortRecentContacts(items);
        adapter.notifyDataSetChanged();

        if (unreadChanged) {
            // 方式一：累加每个最近联系人的未读（快）

            int unreadNum = 0;
            for (RecentContact r : items) {
                unreadNum += r.getUnreadCount();
            }
            if (unreadNum != 0) {
                mMyActionbar.setTitle(String.format(getResources().getString(
                        R.string.commuicate_count), unreadNum + ""));
            } else {
                mMyActionbar.setTitle(R.string.communication);
            }

            ReminderManager.getInstance().updateSessionUnreadNum(unreadNum);
            mUnreadCallback.unread(unreadNum);
        }

        if (items.size() == 0) {
            mNullBgTv.setVisibility(View.VISIBLE);
        } else {
            mNullBgTv.setVisibility(View.GONE);
        }
    }

    /**
     * **************************** 排序 ***********************************
     */
    private void sortRecentContacts(List<RecentContact> list) {
        if (list.size() == 0) {
            return;
        }
        Collections.sort(list, comp);
    }

    private static Comparator<RecentContact> comp = new Comparator<RecentContact>() {

        @Override
        public int compare(RecentContact o1, RecentContact o2) {
            // 先比较置顶tag
            long sticky = (o1.getTag() & RECENT_TAG_STICKY) - (o2.getTag() & RECENT_TAG_STICKY);
            if (sticky != 0) {
                return sticky > 0 ? -1 : 1;
            } else {
                long time = o1.getTime() - o2.getTime();
                return time == 0 ? 0 : (time > 0 ? -1 : 1);
            }
        }
    };

    /**
     * ********************** 收消息，处理状态变化 ************************
     */
    private void registerObservers(boolean register) {
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeRecentContact(messageObserver, register);
        service.observeMsgStatus(statusObserver, register);
        service.observeRecentContactDeleted(deleteObserver, register);

        FriendDataCache.getInstance().registerFriendDataChangedObserver(friendDataChangedObserver, register);
        if (register) {
            registerUserInfoObserver();
        } else {
            unregisterUserInfoObserver();
        }
    }

    Observer<List<RecentContact>> messageObserver = new Observer<List<RecentContact>>() {
        @Override
        public void onEvent(List<RecentContact> messages) {
            int index;
            for (RecentContact msg : messages) {
                index = -1;
                for (int i = 0; i < items.size(); i++) {
                    if (msg.getContactId().equals(items.get(i).getContactId())
                            && msg.getSessionType() == (items.get(i).getSessionType())) {
                        index = i;
                        break;
                    }
                }

                if (index >= 0) {
                    items.remove(index);
                }

                items.add(msg);
            }

            refreshMessages(true);
        }
    };

    Observer<IMMessage> statusObserver = new Observer<IMMessage>() {
        @Override
        public void onEvent(IMMessage message) {
            int index = getItemIndex(message.getUuid());
            if (index >= 0 && index < items.size()) {
                RecentContact item = items.get(index);
                item.setMsgStatus(message.getStatus());
                refreshViewHolderByIndex(index);//局部更新
            }
        }
    };

    Observer<RecentContact> deleteObserver = new Observer<RecentContact>() {
        @Override
        public void onEvent(RecentContact recentContact) {
            if (recentContact != null) {
                for (RecentContact item : items) {
                    if (TextUtils.equals(item.getContactId(), recentContact.getContactId())
                            && item.getSessionType() == recentContact.getSessionType()) {
                        items.remove(item);
                        refreshMessages(true);
                        break;
                    }
                }
            } else {
                items.clear();
                refreshMessages(true);
            }
        }
    };

    private int getItemIndex(String uuid) {
        for (int i = 0; i < items.size(); i++) {
            RecentContact item = items.get(i);
            if (TextUtils.equals(item.getRecentMessageId(), uuid)) {
                return i;
            }
        }

        return -1;
    }

    protected void refreshViewHolderByIndex(final int index) {
//        getActivity().runOnUiThread(new Runnable() {
//
//            @Override
//            public void run() {
//                Object tag = ListViewUtil.getViewHolderByIndex(swipeMenuRecyclerView, index);
//                if (tag instanceof RecentViewHolder) {
//                    RecentViewHolder viewHolder = (RecentViewHolder) tag;
//                    viewHolder.refreshCurrentItem();
//                }
//            }
//        });
    }

    private void registerUserInfoObserver() {
        if (userInfoObserver == null) {
            userInfoObserver = new UserInfoObservable.UserInfoObserver() {
                @Override
                public void onUserInfoChanged(List<String> accounts) {
                    refreshMessages(false);
                }
            };
        }

        UserInfoHelper.registerObserver(userInfoObserver);
    }

    private void unregisterUserInfoObserver() {
        if (userInfoObserver != null) {
            UserInfoHelper.unregisterObserver(userInfoObserver);
        }
    }

    FriendDataCache.FriendDataChangedObserver friendDataChangedObserver = new FriendDataCache.FriendDataChangedObserver() {
        @Override
        public void onAddedOrUpdatedFriends(List<String> accounts) {
            refreshMessages(false);
        }

        @Override
        public void onDeletedFriends(List<String> accounts) {
            refreshMessages(false);
        }

        @Override
        public void onAddUserToBlackList(List<String> account) {
            refreshMessages(false);
        }

        @Override
        public void onRemoveUserFromBlackList(List<String> account) {
            refreshMessages(false);
        }
    };

    /**
     * 菜单创建器。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int deleteWidth = getResources().getDimensionPixelSize(R.dimen.height_82);
            int transforWidth = getResources().getDimensionPixelSize(R.dimen.height_110);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            if (viewType == RecentContactAdapter.VIEW_TYPE_MENU_SINGLE) {// 需要添加单个菜单的Item。
                SwipeMenuItem wechatItem = new SwipeMenuItem(mContext)
                        .setBackgroundColor(getResources().getColor(R.color.letter_red))
                        .setText(R.string.delete)
                        .setTextColor(getResources().getColor(R.color.white))
                        .setWidth(deleteWidth)
                        .setHeight(height);

                swipeRightMenu.addMenuItem(wechatItem);

            } else if (viewType == RecentContactAdapter.VIEW_TYPE_MENU_MULTI) { // 是需要添加多个菜单的Item。
                SwipeMenuItem wechatItem = new SwipeMenuItem(mContext)
                        .setBackgroundColor(getResources().getColor(R.color.main_color_blue))
                        .setText(R.string.transfer_member)
                        .setTextColor(getResources().getColor(R.color.white))
                        .setWidth(transforWidth)
                        .setHeight(height);

                SwipeMenuItem addItem = new SwipeMenuItem(mContext)
                        .setBackgroundColor(getResources().getColor(R.color.letter_red))
                        .setText(R.string.delete)
                        .setTextColor(getResources().getColor(R.color.white))
                        .setWidth(deleteWidth)
                        .setHeight(height);

                swipeRightMenu.addMenuItem(wechatItem);
                swipeRightMenu.addMenuItem(addItem);
            }
        }
    };

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            RecentContact recent = items.get(position);
            if (recent.getSessionType() == SessionTypeEnum.P2P) {
                P2PMessageActivity.start(getActivity(), recent.getContactId(), SessionHelper.getTextP2pCustomization());
            } else {
                LogUtils.i(recent.getContactId());
                TeamMessageActivity.start(getActivity(), recent.getContactId(), SessionHelper.getTeamCustomization(), null, null);
            }
        }
    };

    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                Toast.makeText(mContext, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(mContext, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    public void fillData(String filterStr) {

        ArrayList<RecentContact> filterDateList = LocalSearch.searchGroup(filterStr, items);
        mSearchRecents=filterDateList;
        mSearchAdapter.setData(mSearchRecents);
//        if (filterDateList.size() == 0) {
//            mNameTv.setVisibility(View.GONE);
//            mEmptyTv.setVisibility(View.VISIBLE);
//        } else {
//            mNameTv.setVisibility(View.VISIBLE);
//            mEmptyTv.setVisibility(View.INVISIBLE);
//        }
//        if(TextUtils.isEmpty(filterStr)){
//            mEmptyTv.setVisibility(View.INVISIBLE);
//        }
    }

    @Override
    public void cancel() {
        mMyActionbar.setVisibility(View.VISIBLE);
        mSearchLv.setVisibility(View.GONE);
        swipeMenuRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEditView() {
        mMyActionbar.setVisibility(View.GONE);
        mSearchLv.setVisibility(View.VISIBLE);
        mNullBgTv.setVisibility(View.GONE);
        swipeMenuRecyclerView.setVisibility(View.GONE);
        mSearchAdapter.setData(new ArrayList<RecentContact>());

    }

    public interface UnreadCallback {
        void unread(int count);
    }
}

