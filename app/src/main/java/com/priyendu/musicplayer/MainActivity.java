package com.priyendu.musicplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.priyendu.musicplayer.activities.HomePageActivity;
import com.priyendu.musicplayer.services.StorageUtil;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    public ArrayList<SongItems> songArrayList = new ArrayList<SongItems>();
    //    private ArrayList<SongItems> list2 = new ArrayList<SongItems>();
    private ArrayList<AlbumItems> albumArrayList = new ArrayList<AlbumItems>();

    private int STORAGE_PERMISSION_CODE = 1;
    boolean permissionAskedFirstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonRequest = findViewById(R.id.button);
        TextView textView = findViewById(R.id.text);
        textView.setVisibility(View.GONE);
        if (ContextCompat.checkSelfPermission((this), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            StorageUtil storageUtil = new StorageUtil(getApplicationContext());
            ArrayList<SongItems> arrayList = storageUtil.loadAudio();
            if (arrayList == null) {
                Log.d("message", "shared preference is empty");
            } else {
                Log.d("message", "shared preference is not empty");
                Iterator iterator2 = arrayList.iterator();
                while (iterator2.hasNext()) {
                    SongList songList=SongList.getInstance();
                    songList.setSongList(arrayList);
                    SongItems songItems = (SongItems) iterator2.next();
                    Log.d("message", "Shared prefernce"+songItems.songTitle + songItems.songArtist + " IIIII " + songItems.artistId + " AAAAAA " + songItems.albumKey + " IIIII " + songItems.songId + "AAAAA" + songItems.albumArtPath);
                }
            }

            getMusic();
            Log.d("message", "already given");
        } else if (permissionAskedFirstTime == true) {
            permissionAskedFirstTime = false;
//            Log.d("message","calling for permission"+permissionAskedFirstTime);
            requestStoragePermission();
        }
        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("message", "permission requested");
                requestStoragePermission();
            }
        });
    }

    private void requestStoragePermission() {

//        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)) {

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                StorageUtil storageUtil = new StorageUtil(getApplicationContext());
                ArrayList<SongItems> arrayList = storageUtil.loadAudio();
//                if (arrayList == null) {
//                    Log.d("message", "shared preference is empty");
//                    getMusic();
//                } else {
//                    Log.d("message", "shared preference is not empty");
//                    Iterator iterator2 = arrayList.iterator();
//                    while (iterator2.hasNext()) {
//                        SongList songList=SongList.getInstance();
//                        songList.setSongList(arrayList);
//                        SongItems songItems = (SongItems) iterator2.next();
//                        Log.d("message", "Shared prefernce"+songItems.songTitle + songItems.songArtist + " IIIII " + songItems.artistId + " AAAAAA " + songItems.albumKey + " IIIII " + songItems.songId + "AAAAA" + songItems.albumArtPath);
//                        goToHomePage();
//                    }
//                }
                getMusic();
            }
        }
    }

    public void getMusic() {
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Log.d("message", songUri.toString());
        Log.d("message", albumUri.toString());

// Album cursor

        try {
            Cursor albumCursor = contentResolver.query(albumUri, null, null, null, null, null);
            if (albumCursor != null && albumCursor.moveToFirst()) {
                int albumKeyIndex2 = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_KEY);
                int albumArtIndex2 = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                int albumNameIndex2 = albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
                int songNumberIndex2 = albumCursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS);
                Log.d("message", "stage 1");
                do {
                    String albumArtPath = albumCursor.getString(albumArtIndex2);
                    String albumName = albumCursor.getString(albumNameIndex2);
                    String albumKey = albumCursor.getString(albumKeyIndex2);
                    String songNumber = albumCursor.getString(songNumberIndex2);
                    AlbumItems albumItems = new AlbumItems(albumName, albumArtPath, albumKey, songNumber);
                    albumArrayList.add(albumItems);

                } while (albumCursor.moveToNext());
            }
            albumCursor.close();
            Log.d("message", "stage 3");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        AlbumList albumList = AlbumList.getInstance();
        albumList.setSongList(albumArrayList);
        Iterator iterator3 = albumArrayList.iterator();
        while (iterator3.hasNext()) {
            AlbumItems albumItems = (AlbumItems) iterator3.next();
            Log.d("message", albumItems.albumName + " IIIII " + albumItems.songNumber + " AAAAAA " + albumItems.albumKey + " IIIII " + albumItems.albumArtPath);
        }

        //Song Cursor

        Cursor songCursor = contentResolver.query(songUri, null, null, null, null, null);
        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitleIndex = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songPathIndex = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int songArtistIndex = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songAlbumIndex = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int artistIdIndex = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID);
            int albumKeyIndex = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_KEY);
            int songIdIndex = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            do {

                String songTitle = songCursor.getString(songTitleIndex);
                String songPath=songCursor.getString(songPathIndex);
                String songArtist = songCursor.getString(songArtistIndex);
                String songAlbum = songCursor.getString(songAlbumIndex);
                String artistId = songCursor.getString(artistIdIndex);
                String albumKey = songCursor.getString(albumKeyIndex);
                String songId = songCursor.getString(songIdIndex);

                String albumArtPath = null;
                Iterator iterator = albumArrayList.iterator();
                while (iterator.hasNext()) {
                    AlbumItems albumItems = (AlbumItems) iterator.next();
                    if (albumKey.equals(albumItems.albumKey)) {
                        albumArtPath = albumItems.albumArtPath;
                        break;
                    }
                }

                SongItems songItems = new SongItems(songTitle, songArtist, artistId, albumKey, songId, songAlbum, albumArtPath,songPath);
                songArrayList.add(songItems);
            } while (songCursor.moveToNext());
        }
        songCursor.close();
//        Iterator iterator2 = songArrayList.iterator();
//        while (iterator2.hasNext()) {
//            SongItems songItems = (SongItems) iterator2.next();
//            Log.d("message", songItems.songTitle + songItems.songArtist + " IIIII " + songItems.artistId + " AAAAAA " + songItems.albumKey + " IIIII " + songItems.songId+"AAAAA"+songItems.albumArtPath);
//        }


        SongList songList = SongList.getInstance();
        songList.setSongList(songArrayList);
//        list2 = songList.getSongList();


//        Iterator iterator2 = list2.iterator();
//        while (iterator2.hasNext()) {
//            SongItems songItems = (SongItems) iterator.next();
//            Log.d("message", songItems.songTitle + songItems.songArtist + " IIIII " + songItems.artistId + " IIIII " + songItems.albumId + " IIIII " + songItems.songId);
//        }
        StorageUtil storageUtil=new StorageUtil(getApplicationContext());
        storageUtil.storeAudio(songArrayList);
        goToHomePage();
    }

    public void goToHomePage() {
        if (songArrayList.isEmpty()) {
            Button button = findViewById(R.id.button);
            button.setVisibility(View.GONE);
            TextView textView = findViewById(R.id.text);
            textView.setVisibility(View.VISIBLE);
        } else {
            Intent homePageIntent = new Intent(this, HomePageActivity.class);
            startActivity(homePageIntent);
        }
    }
}
