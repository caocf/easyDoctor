package com.easyhoms.easydoctor.team.adapter;

import android.content.Context;
import android.view.View;

import com.easyhoms.easydoctor.common.adapter.CommAdapter;
import com.easyhoms.easydoctor.team.response.MyTeamMember;

import java.util.ArrayList;

/**
 * 我的团队管理
 */

public class MyTeamAdapter extends CommAdapter<MyTeamMember> {
    public MyTeamAdapter(Context context, ArrayList<MyTeamMember> list) {
        super(context, list);
    }

    @Override
    protected View convert(MyTeamMember member, View convertView, Context context, int position, int layoutId) {
        return null;
    }
}
