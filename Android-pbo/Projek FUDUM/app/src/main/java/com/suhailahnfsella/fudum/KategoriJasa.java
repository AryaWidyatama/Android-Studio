package com.suhailahnfsella.fudum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class KategoriJasa extends AppCompatActivity {
    TextView tvJudul;
    ImageView btnBack,banner;
    LinearLayout headerjasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_jasa);

        getSupportActionBar().hide();

        tvJudul = findViewById(R.id.tvJudul);
        headerjasa = findViewById(R.id.headerjasa);
        banner = findViewById(R.id.banner);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        int value = getIntent().getIntExtra("idkjas", 1);

        if (value == 1){
            tvJudul.setText("Multimedia");
           headerjasa.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.mulmed)));
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(this.getResources().getColor(R.color.mulmed));
            }
            banner.setImageResource(R.drawable.banner_mm);
        } else if (value == 2){
            tvJudul.setText("Rekayasa Perangkat Lunak");
            headerjasa.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.rpl)));
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(this.getResources().getColor(R.color.rpl));
            }
            banner.setImageResource(R.drawable.banner_rpl);
        } else if (value == 3) {
            tvJudul.setText("Akutansi");
            headerjasa.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.ak)));
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(this.getResources().getColor(R.color.ak));
            }
            banner.setImageResource(R.drawable.banner_ak);
        } else if (value == 4) {
            tvJudul.setText("Perbankan");
            headerjasa.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pbk)));
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(this.getResources().getColor(R.color.pbk));
            }
            banner.setImageResource(R.drawable.banner_pbk);
        } else if (value == 5) {
            tvJudul.setText("Pemasaran");
            headerjasa.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.bd)));
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(this.getResources().getColor(R.color.bd));
            }
            banner.setImageResource(R.drawable.banner_bd);

        } else if (value == 6) {
            tvJudul.setText("Perkantoran");
            headerjasa.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.otkp)));
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(this.getResources().getColor(R.color.otkp));
            }
            banner.setImageResource(R.drawable.banner_otkp);
        }
    }

}