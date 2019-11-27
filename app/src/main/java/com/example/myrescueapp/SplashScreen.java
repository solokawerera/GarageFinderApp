package com.example.myrescueapp;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread thread=new Thread(){
            @Override
            public void run(){
                try
                {
                    sleep(4000);

                }
                catch ( Exception e){
                    e.printStackTrace();

                }

                finally {
                    Intent welcomeintent=new Intent(SplashScreen.this,WelcomeActivity.class);
                    startActivity(welcomeintent);

                }
            }
        };
        thread.start();
    }
    @Override
    public void onPause(){
        super.onPause();
    }

}
