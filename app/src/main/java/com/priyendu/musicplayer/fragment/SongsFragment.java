package com.priyendu.musicplayer.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.priyendu.musicplayer.R;
import com.priyendu.musicplayer.SongItems;
import com.priyendu.musicplayer.SongList;
import com.priyendu.musicplayer.SongListAdapter;
import com.priyendu.musicplayer.services.MusicPlayerService;

import java.util.ArrayList;

public class SongsFragment extends Fragment {
    private RecyclerView recyclerView;
    private SongListAdapter songListAdapter;
    private ArrayList<SongItems> songs=new ArrayList<SongItems>();
    private String mediaFile;
    //ThingsAdapter adapter;
    //FragmentActivity listener;


    // This event fires 2nd, before views are created for the fragment
    // The onCreate method is called when the Fragment instance is being created, or re-created.
    // Use onCreate for any standard setup that does not require the activity to be fully created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public SongsFragment(){

    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_songs, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        final Intent musicPlayerServiceIntent=new Intent(getContext(),MusicPlayerService.class);
        SongList songList=SongList.getInstance();
        songs=songList.getSongList();
        recyclerView = (RecyclerView) view.findViewById(R.id.song_list);
        songListAdapter=new SongListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(songListAdapter);
        songListAdapter.setOnItemClickListener(new SongListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
//                Intent musicPlayerServiceIntent=new Intent(getContext(),MusicPlayerService.class);
                SongItems songItems=songs.get(position);
                mediaFile=songItems.songPath;
                playMusic();
//                musicPlayerServiceIntent.putExtra("mediaFile",mediaFile);
//                startService(musicPlayerServiceIntent);
                Log.d("message", "onItemClick position: " + position+mediaFile);
            }
        });
    }

    // This method is called when the fragment is no longer connected to the Activity
    // Any references saved in onAttach should be nulled out here to prevent memory leaks.

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        this.listener = null;
//    }

    // This method is called after the parent Activity's onCreate() method has completed.
    // Accessing the view hierarchy of the parent activity must be done in the onActivityCreated.
    // At this point, it is safe to search for activity View objects by their ID, for example.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public void playMusic(){
        Intent musicPlayerServiceIntent=new Intent(getActivity(),MusicPlayerService.class);
        musicPlayerServiceIntent.putExtra("mediaFile",mediaFile);
        getActivity().startService(musicPlayerServiceIntent);
    }
}
