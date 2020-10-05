package com.priyendu.musicplayer;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

public class AlbumList extends Application {
    private static AlbumList instance=new AlbumList();
    public static AlbumList getInstance(){
        return instance;
    }
    public static void setInstance(AlbumList instance){
        AlbumList.instance=instance;
    }

    private ArrayList<AlbumItems> albumList=new ArrayList<AlbumItems>();
    private AlbumList(){

    }
    public ArrayList<AlbumItems> getSongList() {
        return albumList;
    }
    public void setSongList(ArrayList<AlbumItems> albumList) {
        this.albumList = albumList;
    }
}