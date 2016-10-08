package com.netease.nim.common.ui.popupmenu;

import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.easydoctor.common.view.CircleImageView;

import java.util.List;

public class PopupMenuAdapter extends BaseAdapter {

    public static int TYPE_BG_WHITE = 0;
    public static int TYPE_BG_BLACK = 1;

    private int typeBg = 0;

    private Context context;

    private List<PopupMenuItem> list;

    private LayoutInflater inflater;

    public PopupMenuAdapter(Context context, List<PopupMenuItem> list, int typeBg) {
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.typeBg = typeBg;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PopupMenuItem item = list.get(position);
        ViewHolder cache=null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.nim_popup_menu_list_item, null);
            cache= new ViewHolder();
            cache.icon = (CircleImageView) convertView.findViewById(R.id.popup_menu_icon);
            cache.title = (TextView) convertView.findViewById(R.id.popup_menu_title);
            cache.selectetImg = (ImageView) convertView.findViewById(R.id.popup_menu_selected_img);
            convertView.setTag(cache);
        } else {
            cache = (ViewHolder) convertView.getTag();
        }

        if (position==0){
            cache.icon.setVisibility(View.INVISIBLE);
            cache.title.setText(Html.fromHtml("<font color='#2a3c4c'>全部医院</font><font color='#9ca6af'> (默认)</font>"));
        }else{
            if (item.iconUrl != null) {
                cache.icon.setVisibility(View.VISIBLE);
                CommonUtils.loadImg(item.iconUrl,cache.icon);
            } else {
                cache.icon.setVisibility(View.INVISIBLE);
            }
            cache.title.setGravity(Gravity.LEFT);
            cache.title.setText(item.getTitle());
        }
        if(item.selected){
            cache.selectetImg.setVisibility(View.VISIBLE);
        }else{
            cache.selectetImg.setVisibility(View.INVISIBLE);
        }

        // 下面代码实现数据绑定
        return convertView;
    }

    public void setData(List<PopupMenuItem> data) {
        list = data;
        notifyDataSetChanged();
    }

    private final class ViewHolder {

        public CircleImageView icon;

        public TextView title;
        public ImageView selectetImg;
    }
}
