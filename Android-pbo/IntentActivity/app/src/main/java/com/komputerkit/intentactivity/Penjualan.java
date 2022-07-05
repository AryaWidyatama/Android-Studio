package com.komputerkit.intentactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Penjualan extends AppCompatActivity {

    TextView tvPenjualan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);
        load();
        ambilData();
    }

    public void load() {
        tvPenjualan = findViewById(R.id.tvPenjualan);
    }

    public void ambilData () {
        String ambil1 = getIntent().getStringExtra("ISI1");
        tvPenjualan.setText(ambil1);
    }
}