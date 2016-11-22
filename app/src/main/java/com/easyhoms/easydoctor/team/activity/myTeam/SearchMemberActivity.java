package com.easyhoms.easydoctor.team.activity.myTeam;

import android.view.Window;
import android.view.WindowManager;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.activity.BaseActivity;

import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_search_member)
public class SearchMemberActivity extends BaseActivity {


    @Override
    protected void initView() {
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
//        //设置dialog在屏幕底部
//        window.setGravity(Gravity.Top);
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
    }

    @Override
    protected void initActionbar() {

    }

    @Override
    protected void initListener() {

    }
}
