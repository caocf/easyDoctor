package com.easyhoms.easydoctor.team.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 收藏用户
 */
public class StoreUser implements Parcelable {

    /**
     * createDate : 2016-10-20 16:35:29
     * modifyDate : 2016-10-20 16:35:29
     * id : 4
     * name : 18649700713
     * mobile : 18649700713
     * gender : null
     * birth : null
     */

    public StaffBean staff;
    /**
     * createDate : 2016-10-20 16:35:29
     * modifyDate : 2016-10-20 16:35:29
     * id : 3
     * user : 4
     * sign : null
     * name : 18649700713
     * memo : null
     * imagePath : ic_launcher.168.1.34/group1/M00/00/00/wKgBIlfG5AaATWhXAAAhnQw_J_I696.png
     */
    public StaffExtendBean staffExtend;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.staff, flags);
        dest.writeParcelable(this.staffExtend, flags);
    }

    public StoreUser() {
    }
    public StoreUser(String name) {
        staff=new StaffBean();
        staff.name=name;
    }



    protected StoreUser(Parcel in) {
        this.staff = in.readParcelable(StaffBean.class.getClassLoader());
        this.staffExtend = in.readParcelable(StaffExtendBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<StoreUser> CREATOR = new Parcelable.Creator<StoreUser>() {
        @Override
        public StoreUser createFromParcel(Parcel source) {
            return new StoreUser(source);
        }

        @Override
        public StoreUser[] newArray(int size) {
            return new StoreUser[size];
        }
    };
}
