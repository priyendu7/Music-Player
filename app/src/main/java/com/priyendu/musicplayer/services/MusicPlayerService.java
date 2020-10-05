package com.priyendu.musicplayer.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

public class MusicPlayerService extends Service implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnSeekCompleteListener,
        MediaPlayer.OnInfoListener,
        MediaPlayer.OnPreparedListener{

    private final IBinder iBinder = new LocalBinder();
    private MediaPlayer musicPlayer;
    private String mediaFile;
    private int resumePosition;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try{
            mediaFile=intent.getExtras().getString("mediaFile");
        } catch (Exception e) {
            stopSelf();
        }
        if(mediaFile!=null&&mediaFile!=""){
            initMusicPlayer();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        playMusic(mp);
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }
    public class LocalBinder extends Binder {
        public MusicPlayerService getService() {
            return MusicPlayerService.this;
        }
    }

    private void initMusicPlayer()
    {
        musicPlayer=new MediaPlayer();
        musicPlayer.setOnCompletionListener(this);
        musicPlayer.setOnErrorListener(this);
        musicPlayer.setOnInfoListener(this);
        musicPlayer.setOnSeekCompleteListener(this);
        musicPlayer.setOnPreparedListener(this);
        musicPlayer.reset();
        musicPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try{
            musicPlayer.setDataSource(mediaFile);
            //playMusic();
        } catch (IOException e) {
            e.printStackTrace();
            stopSelf();
        }
        musicPlayer.prepareAsync();
        //playMusic();
    }


    private void playMusic(MediaPlayer musicPlayer) {
        if(!musicPlayer.isPlaying()) {
            try {
                musicPlayer.prepare();
                musicPlayer.start();
                Log.d("message", "media player Started" + mediaFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            stopMusic();
            try {
                musicPlayer.prepare();
                musicPlayer.start();
                Log.d("message", "media player Started" + mediaFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }
    private void stopMusic(){
        if(musicPlayer==null){
            return;
        }
        if(musicPlayer.isPlaying()){
            musicPlayer.stop();
            musicPlayer.reset();
        }
    }
    private void pauseMusic(){
        if(musicPlayer.isPlaying()){
            resumePosition=musicPlayer.getCurrentPosition();
            musicPlayer.pause();
        }
    }
    private void resumeMusic(){
        if(musicPlayer.isPlaying()){
            musicPlayer.seekTo(resumePosition);
            musicPlayer.start();
        }
    }
}
