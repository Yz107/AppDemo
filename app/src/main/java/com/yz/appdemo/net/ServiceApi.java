package com.yz.appdemo.net;

import java.util.List;
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
     *使用form-data表单上传多个文件，同时携带参数
     *     --c93a97d6-d8de-459e-86f1-1d8c42b579c8
     *     Content-Disposition: form-data; name="fileType"
     *     Content-Length: 5
     *     ALARM
     *     --c93a97d6-d8de-459e-86f1-1d8c42b579c8
     *     Content-Disposition: form-data; name="files"; filename="1608540169250380.jpeg"
     *     Content-Type: multipart/form-data
     *     Content-Length: 39465
     */
    @Multipart
    @POST("/cad/file/upload")
    Call<String> uploadFile(@Part List<MultipartBody.Part> partLis);
}
