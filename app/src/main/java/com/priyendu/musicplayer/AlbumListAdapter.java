package com.priyendu.musicplayer;

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

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.ViewHolder> {
    private ArrayList<AlbumItems> albumItemsArrayList = new ArrayList<>();

    private int size = 0;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_album, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        AlbumItems albumItems = albumItemsArrayList.get(i);
        Log.d("message 2",albumItems.albumName);
        viewHolder.albumName.setText(albumItems.albumName);
        viewHolder.songNumber.setText(albumItems.songNumber);
        Drawable image=Drawable.createFromPath(albumItems.albumArtPath);
        viewHolder.albumArt.setImageDrawable(image);
    }

    public AlbumListAdapter() {
        AlbumList albumList = AlbumList.getInstance();
        albumItemsArrayList = albumList.getSongList();
        size = albumItemsArrayList.size();
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView albumArt;
        public TextView songNumber;
        public TextView albumName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            albumArt = (ImageView)itemView.findViewById(R.id.album_art);
            songNumber = (TextView)itemView.findViewById(R.id.song_number);
            albumName = (TextView)itemView.findViewById(R.id.album_name);
        }
    }
}
