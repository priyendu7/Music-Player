package com.priyendu.musicplayer;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

public class SongList extends Application {
    private static SongList instance=new SongList();
    public static SongList getInstance(){
        return instance;
    }
    public static void setInstance(SongList instance){
        SongList.instance=instance;
    }

    private ArrayList<SongItems> songList=new ArrayList<SongItems>();
    private SongList(){

    }
    public ArrayList<SongItems> getSongList() {
        return songList;
    }
    public void setSongList(ArrayList<SongItems> songList) {
        this.songList = songList;
    }
}
