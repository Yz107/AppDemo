package com.yz.appdemo.net;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 支持https图片加载
 * https://muyangmin.github.io/glide-docs-cn/doc/configuration.html#%E7%A8%8B%E5%BA%8F%E5%BA%93-libraries
 * https://blog.csdn.net/ysy950803/article/details/85083160
 */
@GlideModule
public final class HttpsAppGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(RetrofitManager.HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(RetrofitManager.HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(RetrofitManager.HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .sslSocketFactory(RetrofitManager.getSSLContext().getSocketFactory(), RetrofitManager.get509TM())
                .hostnameVerifier(RetrofitManager.getHostnameVerifier())
                .build();
        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(okHttpClient);
        registry.replace(GlideUrl.class, InputStream.class, factory);
    }

}
