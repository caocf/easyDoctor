package com.easyhoms.easydoctor.common.request;


import com.easyhoms.easydoctor.ConstantValues;

import org.xutils.http.RequestParams;

import java.io.File;

/**
 * 上传文件
 */
public class UploadFileParams extends RequestParams {
    public String fileType;
    public File file;

    public UploadFileParams(String fileType, File file) {
        super(ConstantValues.HOST + "/mutual/upload.jhtml");
        this.fileType = fileType;
        this.file = file;
    }
}
