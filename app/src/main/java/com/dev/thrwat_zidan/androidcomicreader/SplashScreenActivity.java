package com.dev.thrwat_zidan.androidcomicreader;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //No setUp need setContentView because we use Theme

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                startActivity(new Intent(SplashScreenActivity.this,MainActivity.class));
                finish();
            }
        },3000); //delay 3 sec

    }
}
