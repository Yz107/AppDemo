package com.yz.appdemo.net;

import android.text.TextUtils;

import com.yz.appdemo.common.Constant;
import com.yz.appdemo.util.log.Logger;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * RetrofitManager,封装retrofit
 */
public class RetrofitManager {
    private static String TAG = "RetrofitManager";
    private static Retrofit mRetrofit;
    private static OkHttpClient okHttpClient;
    private static final long HTTP_CONNECT_TIMEOUT = 10;
    private static final long HTTP_READ_TIMEOUT = 30;
    private static final long HTTP_WRITE_TIMEOUT = 30;
    public static String TOKEN = "token";

    private static ServiceApi API;


    private RetrofitManager() {
    }

    public static ServiceApi instance() {
        if (null == okHttpClient) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                    message -> {
                        //打印retrofit日志
                        Logger.dTag(TAG, message);
                    });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .sslSocketFactory(getSSLContext().getSocketFactory(), get509TM())
                    .hostnameVerifier(getHostnameVerifier())
                    .addInterceptor(loggingInterceptor)
                    /*.addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request oldRequest = chain.request();
                            HttpUrl.Builder builder = oldRequest.url().newBuilder();
//                                    .setEncodedQueryParameter("token", TOKEN);
                            Request.Builder newBuilder = oldRequest.newBuilder()
                                    .method(oldRequest.method(), oldRequest.body())
                                    .url(builder.build());
                            *//**
             * Cookie:
             * JSESSIONID=672977B08482DE9E0600253FAFE77BCA;
             * _user_token=385C413168964D23AE0B0D9A8A6CF375;
             * S-CSRF-TOKEN=a5ac2f336be125c26bec576233ec7717faefc38c8fbf2aba7780c947169747d5
             *//*
                            newBuilder.header("Cookie", Constant.LOGIN_HEADERS.toString());
                            newBuilder.header("X-CSRF-TOKEN", Constant.X_CSRF_TOKEN.toString());
                            Request newRequest = newBuilder.build();
                            return chain.proceed(newRequest);
                        }
                    })*/
                    .build();
        }
        if (null == mRetrofit || !TextUtils.equals(mRetrofit.baseUrl().host(), Constant.SERVER_IP)) {
            Retrofit.Builder builder = new Retrofit
                    .Builder()
                    .baseUrl(getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient);
            mRetrofit = builder.build();
            API = mRetrofit.create(ServiceApi.class);
        }
        return API;
    }

    private static String getBaseUrl() {
        return "http://" + Constant.SERVER_IP + "/demo/";
    }


    public static abstract class SimpleCallback<T> implements Callback<T> {

        @Override
        public final void onResponse(Call<T> call, retrofit2.Response<T> response) {
            if (response.isSuccessful() && response.body() != null) {
                onResponseSuccess(response.body());
            } else {
                Logger.e("response onFailed:" + response);
                onResponseFailure();
            }
        }

        @Override
        public final void onFailure(Call<T> call, Throwable t) {
            Logger.e("request onFailed:" + t.toString());
            onResponseFailure();
        }

        public void onResponseFailure() {
        }

        public abstract void onResponseSuccess(T result);
    }

    public static SSLContext getSSLContext() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new X509TrustManager[]{get509TM()}, new SecureRandom());
        } catch (Exception e) {
        }
        return sslContext;
    }

    public static X509TrustManager get509TM() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    public static HostnameVerifier getHostnameVerifier() {
        return (hostname, session) -> true;
    }
}
