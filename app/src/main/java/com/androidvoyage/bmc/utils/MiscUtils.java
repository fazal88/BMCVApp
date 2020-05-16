package com.androidvoyage.bmc.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

/**
 * To get the gson instance.
 * Used in Retrofit.Builders convertor factory to convert response in this format.
 */
public class MiscUtils {

    static Gson gson;

    static {
        gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new UTCDateTypeAdapter("yyyy-MM-dd HH:mm:ss"))
                .create();
    }

    public static Gson getGsonInstance() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }
}
