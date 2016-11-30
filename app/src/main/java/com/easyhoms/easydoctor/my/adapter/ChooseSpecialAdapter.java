package com.easyhoms.easydoctor.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.adapter.CommAdapter;
import com.easyhoms.easydoctor.common.utils.ToastUtils;

import org.xutils.view.annotation.BindView;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 德医互联 on 2016/11/29.
 */

public class ChooseSpecialAdapter extends CommAdapter<String> {

    private SpecialCallback callback;
    private int count = 0;

    public ChooseSpecialAdapter(Context context, ArrayList<String> list) {
        super(context, list);
    }

    public ChooseSpecialAdapter(Context context, List<String> list, SpecialCallback callback){
        super(context,list);
        this.callback = callback;
    }

    @Override
    protected View convert(final String s, View convertView, Context context, int position, int layoutId) {
        final ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_choose_special,null);
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (callback!=null){
            holder.mSpecialLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count>=3){
                        if (holder.mSpecialLayout.isSelected()){
                            holder.mSpecialLayout.setSelected(false);
                            holder.mSpecialMark.setVisibility(View.INVISIBLE);
                            count--;
                        }else {
                            ToastUtils.showToast(mContext,"最多选择三项哦~");
                        }
                    }else {
                        callback.chooseThis(s);
                        if (holder.mSpecialLayout.isSelected()){
                            holder.mSpecialLayout.setSelected(false);
                            count--;
                            holder.mSpecialMark.setVisibility(View.INVISIBLE);
                        }else {
                            holder.mSpecialLayout.setSelected(true);
                            holder.mSpecialMark.setVisibility(View.VISIBLE);
                            count++;
                        }
                    }
                }
            });
        }
        holder.mSpecialName.setText(s);
        return convertView;
    }


    static class ViewHolder{
        @BindView(R.id.special_layout)
        LinearLayout mSpecialLayout;
        @BindView(R.id.special_name)
        TextView mSpecialName;
        @BindView(R.id.special_mark)
        ImageView mSpecialMark;
    }

    public interface SpecialCallback{
        void chooseThis(String s);
    }
}
