package com.yz.appdemo.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 网络工具类.
 * *
 */
public class NetUtils {

    /**
     * 判断当前手机是否连上Wifi.
     *
     * @param context 上下文
     * @return boolean 是否连上网络
     * <p>
     * *
     */
    static public boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                if (mWiFiNetworkInfo.isAvailable()) {
                    return mWiFiNetworkInfo.isConnected();
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 判断当前手机的网络是否可用.
     *
     * @param context 上下文
     * @return boolean 是否连上网络
     * <p>
     * *
     */
    static public boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                if (mMobileNetworkInfo.isAvailable()) {
                    return mMobileNetworkInfo.isConnected();
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 判断当前网络是手机网络还是WIFI.
     *
     * @param context 上下文
     * @return ConnectedType 数据类型
     * <p>
     * *
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取代表联网状态的NetWorkInfo对象
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            // 判断NetWorkInfo对象是否为空；判断当前的网络连接是否可用
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    /**
     * 获取当前WIFI的SSID.
     *
     * @param context 上下文
     * @return ssid
     * <p>
     * *
     */
    public static String getCurentWifiSSID(Context context) {
        String ssid = "";
        if (context != null) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();

            if (wifiInfo != null) {
                ssid = wifiInfo.getSSID();

                if (!TextUtils.isEmpty(ssid) && ssid.substring(0, 1).equals("\"")
                        && ssid.substring(ssid.length() - 1).equals("\"")) {
                    ssid = ssid.substring(1, ssid.length() - 1);
                }
            }

        }
        return ssid;
    }

    /**
     * 按wifi名称前缀搜索wifi列表
     */
    public static List<ScanResult> getWifiList(Context context, String ssidPrefix) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(context.WIFI_SERVICE);
        List<ScanResult> scanWifiList = wifiManager.getScanResults();
        List<ScanResult> wifiList = new ArrayList<>();
        if (scanWifiList != null && scanWifiList.size() > 0) {
            HashMap<String, Integer> signalStrength = new HashMap<String, Integer>();
            for (int i = 0; i < scanWifiList.size(); i++) {
                ScanResult scanResult = scanWifiList.get(i);
                if (!scanResult.SSID.isEmpty()) {
                    String key = scanResult.SSID + " " + scanResult.capabilities;
                    if (!signalStrength.containsKey(key) && scanResult.SSID.startsWith(ssidPrefix)) {
                        signalStrength.put(key, i);
                        wifiList.add(scanResult);
                    }
                }
            }
        } else {
            Log.e("CZZ", "没有搜索到wifi");
        }
        return wifiList;
    }


    public static List<String> getWifiList(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(context.WIFI_SERVICE);
        List<ScanResult> scanWifiList = wifiManager.getScanResults();
        List<String> wifiList = new ArrayList<>();
        if (scanWifiList != null && scanWifiList.size() > 0) {
            HashMap<String, Integer> signalStrength = new HashMap<String, Integer>();
            for (int i = 0; i < scanWifiList.size(); i++) {
                ScanResult scanResult = scanWifiList.get(i);
                if (!scanResult.SSID.isEmpty()) {
                    String key = scanResult.SSID + " " + scanResult.capabilities;
                    if (!signalStrength.containsKey(key)) {
                        signalStrength.put(key, i);
                        wifiList.add(scanResult.SSID);
                    }
                }
            }
        } else {
            Log.e("CZZ", "没有搜索到wifi");
        }
        return wifiList;
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    /**
     * 用来获得手机扫描到的所有wifi的信息.
     *
     * @param c 上下文
     * @return the current wifi scan result
     */
    public static List<ScanResult> getCurrentWifiScanResult(Context c) {
        WifiManager wifiManager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
        return wifiManager.getScanResults();

    }


    public static boolean connectWifi(Context context, String ssid, String password) {
        ssid = "\"" + ssid + "\"";
        WifiConfiguration apConfig = new WifiConfiguration();
        apConfig.SSID = ssid;
        apConfig.preSharedKey = "\"" + password + "\"";
        apConfig.hiddenSSID = true;
        apConfig.status = WifiConfiguration.Status.ENABLED;
        apConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        apConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        apConfig.allowedPairwiseCiphers
                .set(WifiConfiguration.PairwiseCipher.TKIP);
        apConfig.allowedPairwiseCiphers
                .set(WifiConfiguration.PairwiseCipher.CCMP);
        apConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //6.0以上已连接过的wifi不可以再addNetwork
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration configuration : configuredNetworks) {
            if (TextUtils.equals(ssid, configuration.SSID)) {
//                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
//                boolean b = wifiManager.disableNetwork(connectionInfo.getNetworkId());
                boolean b = wifiManager.disableNetwork(configuration.networkId);//不断开可能连接不上
                return wifiManager.enableNetwork(configuration.networkId, true);
            }
        }
        int wcgID = wifiManager.addNetwork(apConfig);
        return wifiManager.enableNetwork(wcgID, true);
    }


    public interface WifiSsidList {

        public void ssidList(List<ScanResult> list);
    }

    static public String getConnectWifiSsid(Context c) {
        String ssid = "";
        WifiManager wifiManager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null) {
            ssid = wifiInfo.getSSID();
        }
        return ssid;
    }

    // 以下是获得版本信息的工具方法

    // 版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    // 版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    // 检测android 应用在前台还是后台

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                /*
                 * BACKGROUND=400 EMPTY=500 FOREGROUND=100 GONE=1000
                 * PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
                 */
                Log.i(context.getPackageName(), "此appimportace =" + appProcess.importance
                        + ",context.getClass().getName()=" + context.getClass().getName());
                if (appProcess.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.i(context.getPackageName(), "处于后台" + appProcess.processName);
                    return true;
                } else {
                    Log.i(context.getPackageName(), "处于前台" + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    public static byte[] listTobyte(List<Integer> list) {
        List<byte[]> byteList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int value = list.get(i);
            byte[] byteArray = new byte[1];
            byteArray[0] = (byte) (value & 0xFF);
//			byteArray[2] = (byte) (value >> 8  & 0xFF);
//			byteArray[1] = (byte) (value >> 16 & 0xFF);
//			byteArray[0] = (byte) (value >> 24 & 0xFF);
            byteList.add(byteArray);
        }
        int lengthByte = 0;
        for (int i = 0; i < byteList.size(); i++) {
            lengthByte += byteList.get(i).length;
        }
        byte[] allByte = new byte[lengthByte];
        int countLength = 0;
        for (int i = 0; i < byteList.size(); i++) {
            byte[] b = byteList.get(i);
            System.arraycopy(b, 0, allByte, countLength, b.length);
            countLength += b.length;
        }

        return allByte;
    }

    public static List<Integer> byteArrayToInt(byte[] b) {
        byte[] bytes = new byte[]{(byte) -42};

        ByteArrayInputStream in = new ByteArrayInputStream(b);
        int c = 0;
        List<Integer> a = new ArrayList<>();
        while ((c = in.read()) != -1) {
            System.out.println(Character.toUpperCase((char) c));
            a.add(c);
        }

        return a;
    }
}
