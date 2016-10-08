package com.easyhoms.easydoctor.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.easyhoms.easydoctor.R;
import com.easyhoms.easydoctor.common.utils.CommonUtils;
import com.easyhoms.pickview.TimePickerView;
import com.easyhoms.pickview.adapter.NumericWheelAdapter;
import com.easyhoms.pickview.lib.WheelView;
import com.easyhoms.pickview.listener.OnItemSelectedListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Sai on 15/11/22.
 */
public class MyTimePickerView extends LinearLayout implements View.OnClickListener {
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private View view;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;

    private TimePickerView.Type type= TimePickerView.Type.YEAR_MONTH_DAY;
    public static final int DEFULT_START_YEAR = 1900;
    public static final int DEFULT_END_YEAR = Calendar.getInstance().get(Calendar.YEAR);
    private int startYear = DEFULT_START_YEAR;
    private int endYear = DEFULT_END_YEAR;
    private Context mContext;
    private OnTimeSelectListener timeSelectListener;
    private TimeCallback mTimeCallback;
    private int dayCorrent;

    public void setTimeCallback(TimeCallback timeCallback) {
        mTimeCallback = timeCallback;
    }

    public MyTimePickerView(final Context context, AttributeSet attrs) {

        super(context, attrs);
        mContext=context;
        LayoutInflater.from(context).inflate(R.layout.custom_view_my_time_pick,this);

        //默认选中当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        setPicker(year, month, day);
    }

    /**
     * 设置可以选择的时间范围
     *
     * @param startYear
     * @param endYear
     */
    public void setRange(int startYear, int endYear) {
        this.startYear=startYear;
        this.endYear=endYear;
    }

    /**
     * 设置选中时间
     * @param date
     */
    public void setTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date == null)
            calendar.setTimeInMillis(System.currentTimeMillis());
        else
            calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        setPicker(year, month, day);
    }

//    /**
//     * 指定选中的时间，显示选择器
//     *
//     * @param date
//     */
//    public void show(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        if (date == null)
//            calendar.setTimeInMillis(System.currentTimeMillis());
//        else
//            calendar.setTime(date);
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        int hours = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
//        wheelTime.setPicker(year, month, day, hours, minute);
//        show();
//    }


    @Override
    public void onClick(View v) {

    }



    public interface OnTimeSelectListener {
        public void onTimeSelect(Date date);
    }

    public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
        this.timeSelectListener = timeSelectListener;
    }


    /**
     * @Description: TODO 弹出日期时间选择器
     */
    public void setPicker(int year, int month, int day) {
        dayCorrent =day;
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);

        // 年
        wv_year = (WheelView) findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(startYear, endYear));// 设置"年"的显示数据
        wv_year.setLabel(mContext.getString(R.string.pickerview_year));// 添加文字
        wv_year.setCurrentItem(year - startYear);// 初始化时显示的数据

        // 月
        wv_month = (WheelView) findViewById(R.id.month);
        wv_month.setAdapter(new NumericWheelAdapter(1, 12));
        wv_month.setLabel(mContext.getString(R.string.pickerview_month));
        wv_month.setCurrentItem(month);

        // 日
        wv_day = (WheelView) findViewById(R.id.day);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (list_big.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 31));
        } else if (list_little.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 30));
        } else {
            // 闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                wv_day.setAdapter(new NumericWheelAdapter(1, 29));
            else
                wv_day.setAdapter(new NumericWheelAdapter(1, 28));
        }
        wv_day.setLabel(mContext.getString(R.string.pickerview_day));
        wv_day.setCurrentItem(day - 1);

        // 添加"年"监听
        OnItemSelectedListener wheelListener_year = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int year_num = index + startYear;
                // 判断大小月及是否闰年,用来确定"日"的数据
                int maxItem = 30;
                if (list_big
                        .contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                    maxItem = 31;
                } else if (list_little.contains(String.valueOf(wv_month
                        .getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                    maxItem = 30;
                } else {
                    if ((year_num % 4 == 0 && year_num % 100 != 0)
                            || year_num % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        maxItem = 29;
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        maxItem = 28;
                    }
                }

                if (wv_day.getCurrentItem() > maxItem - 1) {
                   // wv_day.setCurrentItem(maxItem - 1);
                    dayCorrent=maxItem-1;
                }
                wv_day.setCurrentItem(dayCorrent);
                mTimeCallback.getTime(getTime());
            }
        };
        // 添加"月"监听
        OnItemSelectedListener wheelListener_month = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int month_num = index + 1;
                int maxItem = 30;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                    maxItem = 31;
                } else if (list_little.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                    maxItem = 30;
                } else {
                    if (((wv_year.getCurrentItem() + startYear) % 4 == 0 && (wv_year
                            .getCurrentItem() + startYear) % 100 != 0)
                            || (wv_year.getCurrentItem() + startYear) % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        maxItem = 29;
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        maxItem = 28;
                    }
                }
                if (wv_day.getCurrentItem() > maxItem - 1) {
                  //  wv_day.setCurrentItem(maxItem - 1);
                    dayCorrent=maxItem-1;
                }
                wv_day.setCurrentItem(dayCorrent);
                mTimeCallback.getTime(getTime());
            }
        };
        wv_year.setOnItemSelectedListener(wheelListener_year);
        wv_month.setOnItemSelectedListener(wheelListener_month);
        wv_day.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                dayCorrent=index;
                mTimeCallback.getTime(getTime());
            }
        });
        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        int textSize = 6;
        switch (type) {
            case ALL:
                textSize = textSize * 3;
                break;
            case YEAR_MONTH_DAY:
                textSize = textSize * 4;
                break;

        }
        wv_day.setTextSize(textSize);
        wv_month.setTextSize(textSize);
        wv_year.setTextSize(textSize);

    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wv_year.setCyclic(cyclic);
        wv_month.setCyclic(cyclic);
        wv_day.setCyclic(cyclic);

    }

    public String getTime() {
        StringBuffer sb = new StringBuffer();

        sb.append((wv_year.getCurrentItem() + startYear)).append("-")
                .append(CommonUtils.toTwoDigits(wv_month.getCurrentItem() + 1)).append("-")
                .append(CommonUtils.toTwoDigits(dayCorrent+1)).append("-");

        return sb.toString();
    }

    public  interface  TimeCallback{
        void getTime(String time);
    }

}
