package com.komputerkit.intentactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText etBarang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        load();
    }

    public void load() {
        etBarang = findViewById(R.id.etBarang);
    }

    public void btnBarang(View view) {
        String barang = etBarang.getText().toString();
        Intent intent = new Intent(this, Barang.class);

        intent.putExtra("ISI",barang);
        startActivity(intent);
    }

    public void btnJual(View view) {
        String jual = etBarang.getText().toString();
        Intent intent = new Intent(this, Penjualan.class);

        intent.putExtra("ISI1",jual);
        startActivity(intent);
    }
}