package com.easyhoms.easydoctor.team.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 德医互联 on 2016/11/4.
 */

public class StaffBean implements Parcelable {

    public String createDate;
    public String modifyDate;
    public int id;
    public String name;
    public String mobile;
    public String gender;
    public String birth;

    public StaffBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createDate);
        dest.writeString(this.modifyDate);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.mobile);
        dest.writeString(this.gender);
        dest.writeString(this.birth);
    }

    protected StaffBean(Parcel in) {
        this.createDate = in.readString();
        this.modifyDate = in.readString();
        this.id = in.readInt();
        this.name = in.readString();
        this.mobile = in.readString();
        this.gender = in.readString();
        this.birth = in.readString();
    }

    public static final Creator<StaffBean> CREATOR = new Creator<StaffBean>() {
        @Override
        public StaffBean createFromParcel(Parcel source) {
            return new StaffBean(source);
        }

        @Override
        public StaffBean[] newArray(int size) {
            return new StaffBean[size];
        }
    };
}
