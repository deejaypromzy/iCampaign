package com.campaign.sey;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeVideo extends YouTubeBaseActivity {
    private YouTubePlayer mPlayer;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    private String key ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video);

        if (null != getIntent()){
              key = getIntent().getStringExtra(Model.YOUTUBE);
        }

        youTubePlayerView =  findViewById(R.id.youtube_view);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                youTubePlayerView.initialize("", onInitializedListener);

            }
        });




        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                mPlayer = player;
                mPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                mPlayer.setFullscreen(true);

                mPlayer.loadVideo(key);
                mPlayer.play();


            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {

         String Error =error.toString();
            Toast.makeText(getApplicationContext(),Error,Toast.LENGTH_LONG).show();
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        youTubePlayerView.initialize("", onInitializedListener);

    }
}
