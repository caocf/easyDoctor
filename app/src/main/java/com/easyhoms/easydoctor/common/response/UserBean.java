package com.easyhoms.easydoctor.common.response;

/**
 * Created by 德医互联 on 2016/10/31.
 */

public class UserBean {


    /**
     * access_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHQiOjE0Nzc5MDcyMjg3NjcsIm1vYmlsZSI6IjE4NjQ5NzAwNzEzIiwiaWF0IjoxNDc3OTAzNjI4NzY3fQ.oM92NKwPm74NSgpzWEfbIrtana32h8_1zNMDOdHYBg4
     * staff : {"createDate":"2016-10-20 16:35:29","modifyDate":"2016-10-20 16:35:29","id":4,"name":"18649700713","mobile":"18649700713","gender":null,"birth":null}
     * staff_im_id : staff_4
     * staffExtend : {"createDate":"2016-08-30 16:19:11","modifyDate":"2016-08-30 16:19:06","id":3,"user":4,"sign":null,"name":"一颗大草莓ll","memo":null,"imagePath":"group1/M00/00/00/wKgBIlfIbMCAeftMAAAh90DgwXM590.jpg"}
     * staff_im_id_token : 123456
     */

    public ContentBean content;
    /**
     * content : {"access_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHQiOjE0Nzc5MDcyMjg3NjcsIm1vYmlsZSI6IjE4NjQ5NzAwNzEzIiwiaWF0IjoxNDc3OTAzNjI4NzY3fQ.oM92NKwPm74NSgpzWEfbIrtana32h8_1zNMDOdHYBg4","staff":{"createDate":"2016-10-20 16:35:29","modifyDate":"2016-10-20 16:35:29","id":4,"name":"18649700713","mobile":"18649700713","gender":null,"birth":null},"staff_im_id":"staff_4","staffExtend":{"createDate":"2016-08-30 16:19:11","modifyDate":"2016-08-30 16:19:06","id":3,"user":4,"sign":null,"name":"一颗大草莓ll","memo":null,"imagePath":"group1/M00/00/00/wKgBIlfIbMCAeftMAAAh90DgwXM590.jpg"},"staff_im_id_token":"123456"}
     * code : 0
     * desc : success
     */

    public int code;
    public String desc;

    public static class ContentBean {
        public String access_token;
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
        public String staff_im_id;
        /**
         * createDate : 2016-08-30 16:19:11
         * modifyDate : 2016-08-30 16:19:06
         * id : 3
         * user : 4
         * sign : null
         * name : 一颗大草莓ll
         * memo : null
         * imagePath : group1/M00/00/00/wKgBIlfIbMCAeftMAAAh90DgwXM590.jpg
         */

        public StaffExtendBean staffExtend;
        public String staff_im_id_token;

        public static class StaffBean {
            public String createDate;
            public String modifyDate;
            public int id;
            public String name;
            public String mobile;
            public String gender;
            public String birth;
        }

        public static class StaffExtendBean {
            public String createDate;
            public String modifyDate;
            public int id;
            public int user;
            public Object sign;
            public String name;
            public Object memo;
            public String imagePath;
        }
    }
}
