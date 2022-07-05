package com.suhailahnfsella.fudum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class JasaActivity extends AppCompatActivity {
    TextView tvJudul, tvPenjelasan;
    ImageView btnBack;
    CardView btnMM, btnRPL,btnAK, btnPBK, btnBD, btnOTKP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jasa);
        load();
        tvJudul.setText("JASA JURUSAN DI SMKN 2 BUDURAN");
        tvPenjelasan.setText("Mempersembahkan para murid berbakat dan berpotensi untuk melayani dan memudahkan pekerjaan anda");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void load(){
        tvJudul = findViewById(R.id.tvJudul);
        tvPenjelasan = findViewById(R.id.tvPenjelasan);
        btnBack = findViewById(R.id.btnBack);
    }

    public void btnMM(View view){
        Integer idkategori = 1;
        Intent intent = new Intent(this, KategoriJasa.class);
        intent.putExtra("idkjas", idkategori);
        startActivity(intent);
    }

    public void btnRPL(View view){
        Integer idkategori = 2;
        Intent intent = new Intent(this, KategoriJasa.class);
        intent.putExtra("idkjas", idkategori);
        startActivity(intent);
    }

    public void btnAK(View view){
        Integer idkategori = 3;
        Intent intent = new Intent(this, KategoriJasa.class);
        intent.putExtra("idkjas", idkategori);
        startActivity(intent);
    }
    public void btnPBK(View view){
        Integer idkategori = 4;
        Intent intent = new Intent(this, KategoriJasa.class);
        intent.putExtra("idkjas", idkategori);
        startActivity(intent);
    }

    public void btnBD(View view){
        Integer idkategori = 5;
        Intent intent = new Intent(this, KategoriJasa.class);
        intent.putExtra("idkjas", idkategori);
        startActivity(intent);
    }

    public void btnOTKP(View view){
        Integer idkategori = 6;
        Intent intent = new Intent(this, KategoriJasa.class);
        intent.putExtra("idkjas", idkategori);
        startActivity(intent);
    }
}