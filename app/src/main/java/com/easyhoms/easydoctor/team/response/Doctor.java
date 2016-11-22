package com.easyhoms.easydoctor.team.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.easyhoms.easydoctor.common.bean.ContactLetter;

/**
 * Created by 德医互联 on 2016/11/2.
 */

public class Doctor extends ContactLetter implements Parcelable {


    /**
     * createDate : 2016-09-29 17:10:23
     * modifyDate : 2016-09-29 17:10:19
     * id : 1
     * user : 1
     * sign : null
     * name : archer0000
     * memo : null
     * imagePath : /group1/M00/00/03/wKgBIlgYyqqAYxgpABXxU208ZqE485.png
     */

    public String createDate;
    public String modifyDate;
    public int id;
    public int user;
    public String sign;
    public String name;
    public String memo;
    public String imagePath;
    public boolean isChoose;

    public Doctor() {
    }
    public Doctor(String name) {
        this.name=name;
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
        dest.writeInt(this.user);
        dest.writeString(this.sign);
        dest.writeString(this.name);
        dest.writeString(this.memo);
        dest.writeString(this.imagePath);
        dest.writeByte(this.isChoose ? (byte) 1 : (byte) 0);
    }

    protected Doctor(Parcel in) {
        this.createDate = in.readString();
        this.modifyDate = in.readString();
        this.id = in.readInt();
        this.user = in.readInt();
        this.sign = in.readString();
        this.name = in.readString();
        this.memo = in.readString();
        this.imagePath = in.readString();
        this.isChoose = in.readByte() != 0;
    }

    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel source) {
            return new Doctor(source);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };
}
