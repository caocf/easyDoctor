package com.easyhoms.easydoctor.team.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 德医互联 on 2016/11/4.
 */

public class StaffExtendBean implements Parcelable {
    public String createDate;
    public String modifyDate;
    public int id;
    public int user;
    public String sign;
    public String name;
    public String memo;
    public String imagePath;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createDate);
        dest.writeString(this.modifyDate);
        dest.writeInt(this.id);
        dest.writeInt(this.user);
        dest.writeString(this.sign);
        dest.writeString(this.name);
        dest.writeString(this.memo);
        dest.writeString(this.imagePath);
    }

    public StaffExtendBean() {
    }

    protected StaffExtendBean(Parcel in) {
        this.createDate = in.readString();
        this.modifyDate = in.readString();
        this.id = in.readInt();
        this.user = in.readInt();
        this.sign = in.readString();
        this.name = in.readString();
        this.memo = in.readString();
        this.imagePath = in.readString();
    }

    public static final Parcelable.Creator<StaffExtendBean> CREATOR = new Parcelable.Creator<StaffExtendBean>() {
        @Override
        public StaffExtendBean createFromParcel(Parcel source) {
            return new StaffExtendBean(source);
        }

        @Override
        public StaffExtendBean[] newArray(int size) {
            return new StaffExtendBean[size];
        }
    };
}
