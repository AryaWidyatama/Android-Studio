package com.komputerkit.komen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class OpsiProduk extends AppCompatActivity {
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opsi_produk);
        getSupportActionBar().hide();
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void btnmkn(View view) {
        Integer idkategori = 1;
        Intent intent = new Intent(this, MakananAct.class);
        intent.putExtra("IdKategori", idkategori);
        startActivity(intent);
    }

    public void btnminum(View view) {
        Integer idkategori = 1;
        Intent intent = new Intent(this, MinumatAct.class);
        intent.putExtra("IdKategori", idkategori);
        startActivity(intent);
    }

    public void btnatk(View view) {
        Integer idkategori = 1;
        Intent intent = new Intent(this, Kategori.class);
        intent.putExtra("IdKategori", idkategori);
        startActivity(intent);
    }

    public void btnatribut(View view) {
        Integer idkategori = 2;
        Intent intent = new Intent(this, AtributActivity.class);
        intent.putExtra("IdKategori", idkategori);
        startActivity(intent);
    }
}