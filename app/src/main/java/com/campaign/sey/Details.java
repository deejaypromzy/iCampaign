package com.campaign.sey;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

public class Details extends AppCompatActivity {

TextView details,main;
TextView news;

//    private void setDarkMode(Window window) {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        changeStatusBar(1,window);
//    }
//
//    public void changeStatusBar(int mode, Window window){
//        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getColor(android.R.color.transparent));
//            //white mode
//            if(mode==1){
//                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            }
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setDarkMode(getWindow());
    setContentView(R.layout.activity_details);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle(getIntent().getStringExtra(Model.NAME_KEY));




        main = findViewById(R.id.Detail);
        details = findViewById(R.id.subTitleDetail);
        news = findViewById(R.id.newsTitleDetail);

        main.setText(getIntent().getStringExtra(Model.DETAIL_KEY));
        news.setText(getIntent().getStringExtra(Model.NAME_KEY));
        details.setText(getIntent().getStringExtra(Model.TITLE_KEY));
        ImageView sportsImage = findViewById(R.id.backdrop);
        //Load the image using the glide library and the Intent extra
        Glide.with(this).load(getIntent().getStringExtra(Model.IMAGE_KEY)).placeholder(R.drawable.no_image).into(sportsImage);

    }
    public void playVideo(View view) {
        Intent intent = new Intent(Details.this,YoutubeVideo.class);
        intent.putExtra(Model.YOUTUBE,getIntent().getStringExtra(Model.YOUTUBE));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
