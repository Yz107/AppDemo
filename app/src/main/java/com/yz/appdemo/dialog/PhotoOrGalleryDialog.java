package com.yz.appdemo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;

import com.yz.appdemo.R;


public class PhotoOrGalleryDialog extends Dialog {

    private final OnSelectListener listener;

    public PhotoOrGalleryDialog(@NonNull Context context, OnSelectListener listener) {
        super(context);
        this.listener = listener;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_gallery_dialog);
    }

    @Override
    protected void onStart() {
        super.onStart();
        findViewById(R.id.gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSelect(PhotoOrGalleryDialog.this, true);
                }
            }
        });
        findViewById(R.id.take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSelect(PhotoOrGalleryDialog.this, false);
                }
            }
        });
    }

    public interface OnSelectListener {
        void onSelect(Dialog dialog, boolean gallery);
    }
}
