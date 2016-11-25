package com.easyhoms.easydoctor.common.request;


import com.easyhoms.easydoctor.Constants;

import org.xutils.http.RequestParams;

import java.io.File;

/**
 * 上传文件
 */
public class UploadFileParams extends RequestParams {
    public String fileType;
    public File file;

    public UploadFileParams(String fileType, File file) {
        super(Constants.HOST + "/mutual/mobile_upload.jhtml");
        this.fileType = fileType;
        this.file = file;
    }
}
