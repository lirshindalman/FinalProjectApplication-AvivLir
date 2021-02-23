package com.example.recipeapp;

import android.support.v7.app.AppCompatActivity;
import android.Manifest;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private ImageView splash_IMG_imageDisplay;
    private TextView splash_LBL_title;

    private Animation splash_top_animation;
    private Animation splash_bottom_Animation;

    private final int SPLASH_SCREEN = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        splash_top_animation = AnimationUtils.loadAnimation(this, R.anim.splash_top_animation);
        splash_bottom_Animation = AnimationUtils.loadAnimation(this, R.anim.splash_bottom_animation);

        findView();
        initAnimation();

        splash_IMG_imageDisplay.setImageResource(R.drawable.soup);

        handlerSplashScreen();
    }

    private void handlerSplashScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);

                Pair pair = new Pair<View,String>(splash_LBL_title, "logo_text");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pair);
                    startActivity(intent);
                    finish();
                }


            }
        }, SPLASH_SCREEN);
    }


    private void initAnimation() {
        splash_IMG_imageDisplay.startAnimation(splash_top_animation);
        splash_LBL_title.startAnimation(splash_bottom_Animation);
    }

    private void findView() {
        splash_IMG_imageDisplay = findViewById(R.id.splash_IMG_imageDisplay);
        splash_LBL_title = findViewById(R.id.splash_LBL_title);
    }

}