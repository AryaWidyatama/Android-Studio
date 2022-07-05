package com.suhailahnfsella.fudum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class PerkenalanActivity extends AppCompatActivity {
    LoginModel loginModel;
    SharedPreferences sharedPreferences,sharedPreferences1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perkenalan);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        getSupportActionBar().setTitle("Perkenalan");



        Gson gson = new Gson();
        String json = sharedPreferences.getString("login", "");
        loginModel = gson.fromJson(json,LoginModel.class);
//        navView.setItemIconTintList(null);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.ic_apaitu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        startActivity(new Intent(getApplicationContext(), Home.class).putExtra("data",loginModel));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.ic_apaitu:
                        return true;
                    case R.id.ic_profil:
                        if (json != ""){
                            loginModel = gson.fromJson(json,LoginModel.class);
                            if (loginModel.getStatus() == 0) {
                                Intent mIntent = new Intent(PerkenalanActivity.this,AccountV1.class);
                                startActivity(mIntent);
                            } else if (loginModel.getStatus() == 1){
                                Intent mIntent = new Intent(PerkenalanActivity.this,AccountV2.class);
                                startActivity(mIntent);
                            } else if (loginModel.getStatus() == 2){
                                Intent mIntent = new Intent(PerkenalanActivity.this,AccountV1.class);
                                startActivity(mIntent);
                            }
                        } else {
                            Intent mIntent = new Intent(PerkenalanActivity.this,Login.class);
                            startActivity(mIntent);
                        }
                        return true;
                }
                return false;
            }
        });
    }
}