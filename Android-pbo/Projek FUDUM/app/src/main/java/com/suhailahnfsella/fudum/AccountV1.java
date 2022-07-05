package com.suhailahnfsella.fudum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class AccountV1 extends AppCompatActivity {

    LoginModel loginModel;
    ImageView imgUser;
    TextView namaPanjang, tvUsNam;
    SharedPreferences sharedPreferences,sharedPreferences1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_v1);
        BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setItemIconTintList(null);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

        getSupportActionBar().setTitle("Akun");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientku));
        }

        navView.setSelectedItemId(R.id.ic_profil);


        namaPanjang = findViewById(R.id.namaPanjang);
        tvUsNam = findViewById(R.id.tvUsNam);
        imgUser = findViewById(R.id.btnInsertImgToko);

        loginModel = gson.fromJson(json,LoginModel.class);
//        namaPanjang.setText(loginModel.getNamapanjang());
//        tvUsNam.setText(loginModel.getUsername());
//        Glide.with(AccountV1.this)
//                .load("" + loginModel.getFotoprofil())
//                .apply(new RequestOptions().override(0, 200))
//                .into(imgUser);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        startActivity(new Intent(getApplicationContext()
                                ,Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.ic_profil:

                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void pengaturanAkun(View view) {
        Intent intent = new Intent(this, SettingAccount.class);
        startActivity(intent);
    }

    public void btnHistoryUser(View view) {
        Intent intent = new Intent(this, HistoryOrderan.class);
        startActivity(intent);
    }

    public void btnMulaiJual(View view) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");
        loginModel = gson.fromJson(json,LoginModel.class);

        if (loginModel.getStatus() == 0){
            Intent intent = new Intent(this, FormToko.class).putExtra("data",loginModel);
            startActivity(intent);
        } else if (loginModel.getStatus() == 2){
            Intent intent = new Intent(this, Langganan.class);
            startActivity(intent);
        }
    }
}