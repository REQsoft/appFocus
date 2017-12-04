package com.example.administrador.focus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimationDrawable animation;
                ImageView image=(ImageView) findViewById(R.id.launch_gif);
                image.setBackgroundResource(R.drawable.launch);
                animation = (AnimationDrawable) image.getBackground();
                animation.start();
                Intent i = new Intent(FullscreenActivity.this, LoginActivity.class );
                startActivity(i);
            }
        },5000);
    }
}
