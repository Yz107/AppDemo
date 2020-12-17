package com.yz.appdemo.net;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Url;

//服务器接口定义
public interface ServiceApi {

    @POST
    Call<String> login(@Url String url, @Query("loginStr") String loginStr);


    @POST("/cad/app/alarm/add")
    Call<String> alarmAdd(@Body RequestBody body);

    @FormUrlEncoded
    @POST("/cad/app/alarm/page")
    Call<String> alarmList(@Field("userId") String userId, @Field("orgId") String orgId,
                           @Field("page") int page, @Field("size") int size);

    /**
     * File file = new File(fileUrl);
     * // 创建 RequestBody，用于封装构建RequestBody
     * // RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
     * RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
     * // MultipartBody.Part  和后端约定好Key，这里的partName是用file
     * MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
     * // 添加描述
     * String descriptionString = "hello, 这是文件描述";
     * RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
     */
    @Multipart
    @POST("uploadImgs")
    Call<String> uploadFile(@Part("description") RequestBody description, @Part MultipartBody.Part file);

    /**
     * Map<String, RequestBody> map = new HashMap<>();
     * for (String imgUrl : imgStrs) {
     * File file = new File(imgUrl);
     * // create RequestBody instance from file
     * // RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
     * RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
     * //注意：file就是与服务器对应的key,后面filename是服务器得到的文件名
     * map.put("file\"; filename=\"" + file.getName(), requestFile);
     * }
     */
    @Multipart
    @POST("/cad/file/upload")
    Call<String> uploadFiles(@PartMap Map<String, okhttp3.RequestBody> files);
}
