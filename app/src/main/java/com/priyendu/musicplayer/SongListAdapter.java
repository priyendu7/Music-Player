package com.priyendu.musicplayer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder> {

    private static ClickListener clickListener;
    private ArrayList<SongItems> songs=new ArrayList<SongItems>();
    private ArrayList<AlbumItems> albums=new ArrayList<AlbumItems>();
    private int size=0;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_song,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        SongItems songItems=songs.get(i);
        viewHolder.songName.setText(songItems.songTitle);
        viewHolder.albumName.setText(songItems.songAlbum);
        viewHolder.songName.setText(songItems.songTitle);
        Drawable image=Drawable.createFromPath(songItems.albumArtPath);
        viewHolder.albumArt.setImageDrawable(image);

    }
    public SongListAdapter(){
        SongList songList=SongList.getInstance();
        songs=songList.getSongList();

        size=songs.size();
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView songName;
        public TextView artistName;
        public TextView albumName;
        public ImageView albumArt;
        public ViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);

            songName = (TextView) itemView.findViewById(R.id.song_name);
            artistName = (TextView) itemView.findViewById(R.id.song_artist);
            albumName = (TextView) itemView.findViewById(R.id.song_album);
            albumArt=(ImageView) itemView.findViewById(R.id.album_art);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(),v);
        }
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        SongListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}
