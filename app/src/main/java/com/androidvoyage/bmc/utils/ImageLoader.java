package com.androidvoyage.bmc.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;

import com.androidvoyage.bmc.BCApplication;
import com.androidvoyage.bmc.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

/**
 * Created by Siddhesh on 19-03-2019.
 */

public class ImageLoader {
    private static String IMAGE_BASE_URL = "https://docsmart.s3.ap-south-1.amazonaws.com/";

    public static void loadImageFromUrl(String url, ImageView targetImageView, Integer placeHolder) {
        try {
            Glide.with(targetImageView.getContext()).load(url).placeholder(placeHolder).into(targetImageView);
        } catch (Exception s) {

        }
    }

    public static void loadImageFromUrl(String url, ImageView targetImageView, Integer placeHolder, RequestListener<Drawable> listener) {
        try {
            Glide.with(targetImageView.getContext()).load(url).placeholder(placeHolder).listener(listener).into(targetImageView);
        } catch (Exception s) {
            try {
                Glide.with(targetImageView.getContext())
                        .load(url)
                        .placeholder(placeHolder)
                        .apply(new RequestOptions().override(480, 480))
                        .into(targetImageView);

            } catch (Exception e) {

            }
        }
    }

    public static void loadThumbnailFromUrl(String url, ImageView targetImageView, Integer placeHolder,int width,int height) {
        try {
            Glide.with(targetImageView.getContext())
                    .load(url)
                    .placeholder(placeHolder)
                    .apply(new RequestOptions().override(width,height))
                    .into(targetImageView);

        } catch (Exception s) {
        }
    }

    public static void loadThumbnailFromUrl(String url, ImageView targetImageView, Integer placeHolder) {
        try {
            Glide.with(targetImageView.getContext())
                    .load(url)
                    .placeholder(placeHolder)
                    .apply(new RequestOptions().override(240,240))
                    .into(targetImageView);

        } catch (Exception s) {

        }
    }

    public static void loadLocalImageFromURI(Uri uri, Context context, ImageView targetImageView, Integer placeHolder) {
        try {
            Glide.with(context)
                    .load(uri)
                    .placeholder(placeHolder)
                    .into(targetImageView);

        } catch (Exception s) {

        }
    }

    public static void loadCroppedImageFromURI(String url, ImageView targetImageView, Integer placeHolder) {
        Glide.with(BCApplication.getAppContext())
                .load(url)
                .centerCrop()
                .placeholder(placeHolder)
                .into(targetImageView);
    }

    public static String getImageURLwithBase(String endUrl) {
        if (!endUrl.startsWith("http"))
            endUrl = IMAGE_BASE_URL + endUrl;
        return endUrl;
    }


    public static void loadCircularImageUrl(String url, ImageView imvImage) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile);

        Glide.with(BCApplication.getAppContext())
                .applyDefaultRequestOptions(requestOptions)
                .load(R.drawable.profile)
                .apply(RequestOptions.circleCropTransform())
                .into(imvImage);
    }

    public static String getPathName() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), BCApplication.getAppContext().getResources().getString(R.string.app_name) + "/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }
}
