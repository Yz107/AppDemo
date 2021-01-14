package com.yz.appdemo.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yz.appdemo.R;
import com.yz.appdemo.common.Constant;
import com.yz.appdemo.dialog.PhotoOrGalleryDialog;
import com.yz.appdemo.net.GlideEngine;
import com.yz.appdemo.net.RetrofitManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class SelectPictureActivity extends AppCompatActivity {

    @BindView(R.id.image)
    ImageView image;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture);
        ButterKnife.bind(this);
    }

    public void select(View view) {
        new PhotoOrGalleryDialog(this, new PhotoOrGalleryDialog.OnSelectListener() {
            @Override
            public void onSelect(Dialog dialog, boolean gallery) {
                dialog.dismiss();
                File dir = new File(Constant.FILE_IMAGE_PATH);
                if (!dir.exists() || !dir.isDirectory()) {
                    dir.mkdirs();
                }
                if (gallery) {
                    PictureSelector.create(SelectPictureActivity.this)
                            .openGallery(PictureMimeType.ofImage())
                            .imageEngine(GlideEngine.createGlideEngine())
                            .compress(true)
                            .compressSavePath(Constant.FILE_IMAGE_PATH)
                            .minimumCompressSize(200)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                } else {
                    PictureSelector.create(SelectPictureActivity.this)
                            .openCamera(PictureMimeType.ofImage())
                            .imageEngine(GlideEngine.createGlideEngine())
                            .compress(true)
                            .compressSavePath(Constant.FILE_IMAGE_PATH)
                            .minimumCompressSize(200)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                }
            }
        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList == null || selectList.isEmpty()) return;
                LocalMedia media = selectList.get(0);
                String path = media.isCompressed() ? media.getCompressPath() : media.getPath();
                Glide.with(this).load(path).into(image);
                imagePath = path;
                break;
        }
    }

    private void uploadPic(String path) {
        //1.创建MultipartBody.Builder对象
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//表单类型
        //2.获取图片，创建请求体
        File file = new File(path);
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);//表单类型
        //3.调用MultipartBody.Builder的addFormDataPart()方法添加表单数据
        builder.addFormDataPart("fileType", "ALARM");//传入服务器需要的key，和相应value值
        builder.addFormDataPart("files", file.getName(), body); //添加图片数据，body创建的请求体
        //4.创建List<MultipartBody.Part> 集合，
        List<MultipartBody.Part> parts = builder.build().parts();
        //5.最后进行HTTP请求，传入parts即可
        try {
            Response<String> execute = RetrofitManager.instance().uploadFile(parts).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showImage(View view) {
        if (TextUtils.isEmpty(imagePath)) return;
        ArrayList<LocalMedia> medias = new ArrayList<>();
        medias.add(new LocalMedia(imagePath, 0, 0, ""));
        PictureSelector.create(this).themeStyle(R.style.picture_default_style)
                .imageEngine(GlideEngine.createGlideEngine())
                .openExternalPreview(0, medias);
    }
}
