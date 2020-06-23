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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
                            .compress(true)
                            .compressSavePath(Constant.FILE_IMAGE_PATH)
                            .minimumCompressSize(200)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                } else {
                    PictureSelector.create(SelectPictureActivity.this)
                            .openCamera(PictureMimeType.ofImage())
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

    public void showImage(View view) {
        if (TextUtils.isEmpty(imagePath)) return;
        ArrayList<LocalMedia> medias = new ArrayList<>();
        medias.add(new LocalMedia(imagePath, 0, 0, ""));
        PictureSelector.create(this).themeStyle(R.style.picture_default_style)
                .openExternalPreview(0, medias);
    }
}
