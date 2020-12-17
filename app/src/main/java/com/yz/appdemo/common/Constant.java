package com.yz.appdemo.common;

import android.os.Environment;

public class Constant {

    public static final String FILE_ROOT_PATH = Environment.getExternalStorageDirectory() + "/demo/";
    public static final String FILE_LOG_PATH = FILE_ROOT_PATH + "log/";//log文件位置
    public static final String SERVER_IP = "";
    public static final String FILE_IMAGE_PATH = FILE_ROOT_PATH + "image/";

    //ftp服务器配置
    public static String FTP_SERVER_IP = "115.28.179.198";
    public static String FTP_SERVER_USER = "iscc_ftp";
    public static String FTP_SERVER_PWD = "Nuc+tech";

}
