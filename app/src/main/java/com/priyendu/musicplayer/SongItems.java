package com.priyendu.musicplayer;

public class SongItems {
    String songTitle;
    String songArtist;
    String songAlbum;
    String artistId;
    String albumKey;
    String songId;
    String albumArtPath;
    public String songPath;
    SongItems(String songTitle,String songArtist,String artistId, String albumKey,String songId,String songAlbum,String albumArtPath,String songPath){
        this.songArtist=songArtist;
        this.songTitle=songTitle;
        this.albumKey=albumKey;
        this.artistId=artistId;
        this.songId=songId;
        this.songAlbum=songAlbum;
        this.albumArtPath=albumArtPath;
        this.songPath=songPath;
    }
}
