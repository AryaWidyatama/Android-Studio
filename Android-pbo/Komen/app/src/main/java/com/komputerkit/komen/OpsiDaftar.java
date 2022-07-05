package com.komputerkit.komen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class OpsiDaftar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opsi_daftar);
        getSupportActionBar().hide();
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);

    }
//
//    public void Guru(View view) {
//        Intent intent = new Intent(OpsiDaftar.this,DaftarGuru.class);
//        startActivity(intent);
//    }
//
//    public void Murid(View view) {
//        Intent intent = new Intent(OpsiDaftar.this,Daftar.class);
//        startActivity(intent);
//    }
//
//    public void BukanWarga(View view) {
//        Intent intent = new Intent(OpsiDaftar.this,DaftarLuarSmenda.class);
//        startActivity(intent);
//    }

    public void guru(View view) {
        Intent intent = new Intent(OpsiDaftar.this,DaftarGuru.class);
        startActivity(intent);
    }

    public void murid(View view) {
        Intent intent = new Intent(OpsiDaftar.this,Daftar.class);
        startActivity(intent);
    }

    public void wargaluar(View view) {
        Intent intent = new Intent(OpsiDaftar.this,DaftarLuarSmenda.class);
        startActivity(intent);
    }
}