package com.bitpolarity.zzzoom.db;


import android.app.PendingIntent;

import androidx.room.TypeConverter;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {
    @TypeConverter
    public static ArrayList<PendingIntent> fromString(String value) {
        Type listType = new TypeToken<ArrayList<PendingIntent>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<PendingIntent> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}