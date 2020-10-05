package com.priyendu.musicplayer.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.priyendu.musicplayer.SongItems;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class StorageUtil {

    private final String storage="com.priyendu.musicplayer.STORAGE";
    private SharedPreferences sharedPreferences;
    private Context context;

    public StorageUtil(Context context){
        this.context=context;
    }

    public void storeAudio(ArrayList<SongItems> arrayList){
        sharedPreferences=context.getSharedPreferences(storage,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(arrayList);
        editor.putString("musicArrayList",json);
        editor.apply();
    }

    public ArrayList<SongItems> loadAudio(){
        sharedPreferences=context.getSharedPreferences(storage,Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("musicArrayList",null);
        Type type=new TypeToken<ArrayList<SongItems>>(){}.getType();
        return gson.fromJson(json,type);
    }

    public void clearCachedAudioPlaylist(){
        sharedPreferences=context.getSharedPreferences(storage,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
