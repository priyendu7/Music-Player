package com.priyendu.musicplayer;

public class AlbumItems{
    String albumName;
    String albumKey;
    String albumArtPath;
    String songNumber;
    AlbumItems(String albumName,String albumArtPath,String albumKey,String songNumber){
        this.albumKey=albumKey;
        this.albumName=albumName;
        this.albumArtPath=albumArtPath;
        this.songNumber=songNumber;
    }

}
