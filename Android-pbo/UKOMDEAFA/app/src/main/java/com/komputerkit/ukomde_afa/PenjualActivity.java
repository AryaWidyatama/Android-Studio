package com.komputerkit.ukomde_afa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

public class PenjualActivity extends AppCompatActivity {

    Button btntambah,btnhapus,btnubah,btnkeluar,btnOrder;
    public static PenjualActivity ma;
    Login login;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

         PenjualActivity ma;
         ma=this;
        PenjualActivity ma1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjual);
        getSupportActionBar().hide();

        btnubah = (Button) findViewById(R.id.btnubah);
        btnubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UbahData();
            }
        });

        btnOrder = (Button) findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order();
            }
        });

        btntambah = (Button) findViewById(R.id.btntambah);
        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tambahdata();
            }
        });
        btnhapus = (Button) findViewById(R.id.btnhapus);
        btnhapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HapusData();
            }
        });

        btnkeluar = (Button) findViewById(R.id.btnkeluar);
        btnkeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeluarData();
            }
        });


    }


    public void UbahData(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");
        if (json != ""){
            login = gson.fromJson(json,Login.class);

            String idKategori = login.getRelasi();
            Intent intent = new Intent(this,TambahData.class);
            intent.putExtra("idKategori", idKategori);
            startActivity(intent);
        }


    }

    public void Tambahdata(){
        Intent intent = new Intent(this,DaftarBaruDataProduk.class);
        startActivity(intent);
    }

    public void Order(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");
        if (json != ""){
            login = gson.fromJson(json,Login.class);

            String idKategori = login.getRelasi();
            Intent intent = new Intent(this,OrderPenjual.class);
            intent.putExtra("idKategori", idKategori);
            startActivity(intent);
        }
    }


    public void HapusData(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");
        if (json != ""){
            login = gson.fromJson(json,Login.class);

            String idKategori = login.getRelasi();
            Intent intent = new Intent(this,KurangData.class);
            intent.putExtra("idKategori", idKategori);
            startActivity(intent);
        }
    }

    public void KeluarData(){
        Intent intent = new Intent(this,InfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}