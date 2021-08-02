package com.komputerkit.intentactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Barang extends AppCompatActivity {

    TextView tvBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);
        load();
        ambilData();
    }

    public void load() {
        tvBarang = findViewById(R.id.tvBarang);
    }

    public void ambilData () {
        String ambil = getIntent().getStringExtra("ISI");
        tvBarang.setText(ambil);
    }
}