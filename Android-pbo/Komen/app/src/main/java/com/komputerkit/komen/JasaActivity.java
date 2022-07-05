package com.komputerkit.komen;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class JasaActivity extends AppCompatActivity {
    TextView tvJudul, tvPenjelasan;
    ImageView btnBack;
    CardView btnMM, btnRPL,btnAK, btnPBK, btnBD, btnOTKP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jasa);

        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);

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
        Intent intent = new Intent(this, MMAct.class);
        intent.putExtra("idkjas", idkategori);
        startActivity(intent);
    }

    public void btnRPL(View view){
        Integer idkategori = 2;
        Intent intent = new Intent(this, RPLAct.class);
        intent.putExtra("idkjas", idkategori);
        startActivity(intent);
    }

    public void btnAK(View view){
        Integer idkategori = 3;
        Intent intent = new Intent(this, AKAct.class);
        intent.putExtra("idkjas", idkategori);
        startActivity(intent);
    }
    public void btnPBK(View view){
        Integer idkategori = 4;
        Intent intent = new Intent(this, PBKAct.class);
        intent.putExtra("idkjas", idkategori);
        startActivity(intent);
    }

    public void btnBD(View view){
        Integer idkategori = 5;
        Intent intent = new Intent(this, BDPAct.class);
        intent.putExtra("idkjas", idkategori);
        startActivity(intent);
    }

    public void btnOTKP(View view){
        Integer idkategori = 6;
        Intent intent = new Intent(this, OTKPAct.class);
        intent.putExtra("idkjas", idkategori);
        startActivity(intent);
    }
}