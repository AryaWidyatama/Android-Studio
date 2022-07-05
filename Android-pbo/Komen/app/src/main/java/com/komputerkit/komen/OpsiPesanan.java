package com.komputerkit.komen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;

public class OpsiPesanan extends AppCompatActivity {
    SiswaModel loginModel;
    SharedPreferences sharedPreferences,sharedPreferences1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_opsi_pesanan);
                getSupportActionBar().setTitle("Status Pesanan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);

    }

    public void Proses(View view) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");
        loginModel = gson.fromJson(json,SiswaModel.class);
        Integer id = loginModel.getId();
        Intent intent = new Intent(OpsiPesanan.this,ProsesAct.class);
        intent.putExtra("idKategori", id);

        startActivity(intent);
    }

    public void Terima(View view) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");
        loginModel = gson.fromJson(json,SiswaModel.class);
        Integer id = loginModel.getId();
        Intent intent = new Intent(OpsiPesanan.this,TerimaAct.class);
        intent.putExtra("idKategori", id);

        startActivity(intent);
    }

    public void Selesai(View view) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");
        loginModel = gson.fromJson(json,SiswaModel.class);
        Integer id = loginModel.getId();
        Intent intent = new Intent(OpsiPesanan.this,SelesaiNew.class);
        intent.putExtra("idKategori", id);

        startActivity(intent);
    }
}