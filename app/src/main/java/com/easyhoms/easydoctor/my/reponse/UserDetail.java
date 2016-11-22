package com.easyhoms.easydoctor.my.reponse;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 德医互联 on 2016/11/21.
 */

public class UserDetail implements Parcelable {

    /**
     * gender : 1
     * image_path : group1/M00/00/00/O24Z7lgtB6GAVXs1AAAjgewsTHA457.jpg
     * name : 考虑
     * birth : 2008-11-18 00:00:00
     */

    public int gender;
    public String image_path;
    public String name;
    public String birth;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.gender);
        dest.writeString(this.image_path);
        dest.writeString(this.name);
        dest.writeString(this.birth);
    }

    public UserDetail() {
    }

    protected UserDetail(Parcel in) {
        this.gender = in.readInt();
        this.image_path = in.readString();
        this.name = in.readString();
        this.birth = in.readString();
    }

    public static final Parcelable.Creator<UserDetail> CREATOR = new Parcelable.Creator<UserDetail>() {
        @Override
        public UserDetail createFromParcel(Parcel source) {
            return new UserDetail(source);
        }

        @Override
        public UserDetail[] newArray(int size) {
            return new UserDetail[size];
        }
    };
}
