package com.easyhoms.easydoctor.common.bean;

/**
 * Created by 德医互联 on 2016/11/28.
 */

public class UserAuthInfo {

    /**
     * content : {"createDate":"2016-11-23 17:12:51","modifyDate":"2016-11-23 17:12:51","id":49,"staff":46,"idNumber":"46","profDocterQualCer1":"group1/M00/00/01/O24Z7lg4DxKAGojXAAApbZTu5Ps176.jpg","profDocterQualCer2":"group1/M00/00/01/O24Z7lg4DxmAYS83AAA82pwfoDk544.jpg","profDocterRegisterCer1":"group1/M00/00/01/O24Z7lg4DyKAUfWLAABKJstcinE076.jpg","profDocterRegisterCer2":"group1/M00/00/01/O24Z7lg4DyiAATIHAAA5YI1-5Og317.jpg","profTitleCer":"group1/M00/00/01/O24Z7lg4DzyAaEH9AAA00viRKvs349.jpg","auditStatus":1}
     * code : 0
     * desc : success
     */

    public ContentBean content;
    public int code;
    public String desc;

    public static class ContentBean {
        /**
         * createDate : 2016-11-23 17:12:51
         * modifyDate : 2016-11-23 17:12:51
         * id : 49
         * staff : 46
         * idNumber : 46
         * profDocterQualCer1 : group1/M00/00/01/O24Z7lg4DxKAGojXAAApbZTu5Ps176.jpg
         * profDocterQualCer2 : group1/M00/00/01/O24Z7lg4DxmAYS83AAA82pwfoDk544.jpg
         * profDocterRegisterCer1 : group1/M00/00/01/O24Z7lg4DyKAUfWLAABKJstcinE076.jpg
         * profDocterRegisterCer2 : group1/M00/00/01/O24Z7lg4DyiAATIHAAA5YI1-5Og317.jpg
         * profTitleCer : group1/M00/00/01/O24Z7lg4DzyAaEH9AAA00viRKvs349.jpg
         * auditStatus : 1
         */

        public String createDate;
        public String modifyDate;
        public int id;
        public int staff;
        public String idNumber;
        public String profDocterQualCer1;
        public String profDocterQualCer2;
        public String profDocterRegisterCer1;
        public String profDocterRegisterCer2;
        public String profTitleCer;
        public int auditStatus;
    }
}
