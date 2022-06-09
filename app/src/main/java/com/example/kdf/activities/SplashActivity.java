
package com.example.kdf.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.kdf.R;
import com.example.kdf.utils.Preferences;

import java.util.Objects;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(2500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (!Preferences.getIsFirstLaunch(SplashActivity.this)) {
                        startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                }
            }
        };
        timer.start();
    }
}