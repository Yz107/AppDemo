package com.yz.appdemo.common;

import android.os.Environment;

public class Constant {

    public static String FILE_ROOT_PATH = Environment.getExternalStorageDirectory() + "/demo";
    public static String FILE_IMAGE_PATH = FILE_ROOT_PATH + "/image";
    public static String SERVER_IP = "";

    //ftp服务器配置
    public static String FTP_SERVER_IP = "115.28.179.198";
    public static String FTP_SERVER_USER = "iscc_ftp";
    public static String FTP_SERVER_PWD = "Nuc+tech";

}
