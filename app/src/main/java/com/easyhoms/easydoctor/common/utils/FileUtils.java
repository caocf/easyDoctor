package com.easyhoms.easydoctor.common.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/7/1.
 */
public class FileUtils {
    private String SD_PATH;

    public String getSD_PATH() {
        return SD_PATH;
    }

    public FileUtils() {
        //得到当前外部存储设备的目录
        SD_PATH = Environment.getExternalStorageDirectory() + "/";
    }

    public File createSDFile(String fileName) {
        File file = new File(SD_PATH + fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public File createSDDir(String dirName) {
        File dir = new File(SD_PATH + dirName);
        dir.mkdir();
        return dir;
    }

    public boolean isFileExist(String fileName) {
        File file = new File(SD_PATH + fileName);
        return file.exists();
    }

    public File write2SDFromInput(String path, String fileName, InputStream input) {
        File file = null;
        OutputStream output = null;
        try {
            createSDDir(path);
            file = createSDFile(path + fileName);
            output = new FileOutputStream(file);
            byte buffer[] = new byte[4 * 1024];
            while (input.read(buffer) != -1) {
                output.write(buffer);
            }
            //清掉缓存
            output.flush();
        } catch (Exception e) {
            Log.e("write2SDFromInput", e.getMessage());
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException ioe) {
                Log.e("write2SDFromInput", ioe.getMessage());
            }
        }
        return file;
    }

    public static long getFileSize(String path) {
        URL url = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        int code = 0;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            code = conn.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int length=0;
        if (code == 200) {
            // 1 获取服务器资源的大小
            length= conn.getContentLength();
        }
        return length;
    }

}
