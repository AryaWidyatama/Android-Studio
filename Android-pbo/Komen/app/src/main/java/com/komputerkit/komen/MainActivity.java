package com.komputerkit.komen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences, sharedPreferences1;
//    LoginModel loginModel;
private int currentApiVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

            Runnable r = new Runnable() {
                @Override
                public void run() {
//                loginUser(loginModel);
//                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//                    Gson gson = new Gson();
//                    String json = sharedPreferences.getString("login", "");
//                    if (json != "") {
//                        loginModel = gson.fromJson(json, LoginModel.class);
//                        if (loginModel.getLevel().matches("pelanggan")) {
//                            startActivity(new Intent(MainActivity.this, Home.class));
//                            finish();
//                        } else if (loginModel.getLevel().matches("admin")) {
//                            startActivity(new Intent(MainActivity.this, AdminPage.class));
//                            finish();
//                        }
//                    } else {
                        startActivity(new Intent(MainActivity.this, Home.class));
                        finish();
//                    }
                }
            };


            Handler h = new Handler();
            h.postDelayed(r, 3000);

        }
    }

    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus)
        {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}