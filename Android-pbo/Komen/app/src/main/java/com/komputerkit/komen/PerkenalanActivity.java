package com.komputerkit.komen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class PerkenalanActivity extends AppCompatActivity {
    private int currentApiVersion;
    SiswaModel loginModel;
    SharedPreferences sharedPreferences,sharedPreferences1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perkenalan);
//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        getSupportActionBar().hide();
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);



        Gson gson = new Gson();
        String json = sharedPreferences.getString("login", "");
        loginModel = gson.fromJson(json,SiswaModel.class);
//        navView.setItemIconTintList(null);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.ic_apaitu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.ic_apaitu:
                        return true;
                    case R.id.ic_profil:
                        if (json != "") {
                            loginModel = gson.fromJson(json, SiswaModel.class);
//                            if (loginModel.getStatus() == 1) {
                            startActivity(new Intent(getApplicationContext(), AccountV2.class));
                            overridePendingTransition(0,0);
//                            } else if (loginModel.getStatus() == 2) {
////                                Intent mIntent = new Intent(Home.this, AccountV1.class);
////                                startActivity(mIntent);
//                            }
                        } else {
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        }
                        return true;
                        }
                        return false;
                }

        });
        }



}