package com.komputerkit.komen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class AccountV2 extends AppCompatActivity {
    private int currentApiVersion;
    SiswaModel loginModel;
    ImageView imgUser,verif;
    int h;
    TextView namaPanjang, tvUsNam,tvNoTelp1;
    SharedPreferences sharedPreferences,sharedPreferences1;
    public static AccountV2 ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_v2);

        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);

//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
//        BottomNavigationView navView = findViewById(R.id.navigation);
//        navView.setItemIconTintList(null);

//        getSupportActionBar().setTitle("Akun");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar !=null){
//            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientku));
//        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

        namaPanjang = findViewById(R.id.namaPanjang);
        tvUsNam = findViewById(R.id.tvUsNam);
        imgUser = findViewById(R.id.btnInsertImgToko);
        tvNoTelp1 = findViewById(R.id.tvNoTelp);
        verif = findViewById(R.id.iconverif);



        loginModel = gson.fromJson(json,SiswaModel.class);


//        if (json != "") {
//            loginModel = gson.fromJson(json, SiswaModel.class);
//            int g = 1;
//            if (loginModel.getStatus() == g){
//                verif.setImageAlpha(1);
//            }else if ()
//
//
//        } else {
//            verif.setImageAlpha(1);
//        }

        namaPanjang.setText(loginModel.getNamaLengkap());
        tvUsNam.setText(loginModel.getKelas() + " / " + loginModel.getJurusan());
        tvNoTelp1.setText("+62"+loginModel.getNoTelp());

        Glide.with(AccountV2.this)
                .load("" + loginModel.getFotoSiswa())
                .apply(new RequestOptions().override(0, 200))
                .into(imgUser);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.ic_profil);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.ic_apaitu:
                        startActivity(new Intent(getApplicationContext(), PerkenalanActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.ic_profil:

                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }




    public void pengaturanAkun(View view) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

        loginModel = gson.fromJson(json,SiswaModel.class);

        if (loginModel.getKelas().equals("Guru")){
            Intent intent = new Intent(this, Ubahprofilguru.class);
            startActivity(intent);
                }else if (loginModel.getKelas().equals("Luar")){
            Intent intent = new Intent(this, Ubahprofilluarsmenda.class);
            startActivity(intent);
                }else{
            Intent intent = new Intent(this, UbahProfil.class);
            startActivity(intent);
        }


    }

    public void btnScanqrcode(View view) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

        loginModel = gson.fromJson(json,SiswaModel.class);


        if (json != "") {
            loginModel = gson.fromJson(json, SiswaModel.class);
            int g = 1;
            if (loginModel.getStatus() == g){
                Intent intent = new Intent(AccountV2.this,SudahVerif.class);
                startActivity(intent);
            }else if(loginModel.getStatus() == 2){
                Intent intent = new Intent(AccountV2.this,TungguVerifAct.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(this, VerifikasiAkunAct.class);
                startActivity(intent);
            }

        } else {

            Intent intent = new Intent(this, VerifikasiAkunAct.class);
            startActivity(intent);
        }

        return;


    }

    public void btnHistoryUser(View view) {
        Intent intent = new Intent(this, OpsiPesanan.class);
        startActivity(intent);
    }

    public void btnpasangjasa(View view) {
        Intent intent1 = new Intent(Intent.ACTION_VIEW);
        intent1.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+62"+"82245560656"+"&text="+"Halo Admin! "+"\n"+"Saya ( nama,kelas,jurusan ) ingin menawarkan jasa ( isi dengan jasa yang anda tawarkan )"));
        startActivity(intent1);
    }

    public void btnhubungics(View view) {
        Intent intent1 = new Intent(Intent.ACTION_VIEW);
        intent1.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+62"+"82137754441"+"&text="+"Hai Admin!"+"\n"+"Saya mau bertanya..."));
        startActivity(intent1);
    }
//
//    public void btnHistoryUser(View view) {
//        Intent intent = new Intent(this, HistoryOrderan.class);
//        startActivity(intent);
//    }
//
//    public void btnMulaiJual(View view) {
//        Intent intent = new Intent(this, FormToko.class);
//        startActivity(intent);
//    }
//
//    public void btnTokoSaya(View view) {
//        Intent intent = new Intent(this, Toko.class);
//        startActivity(intent);
//    }
//
//    public void btnScanqrcode(View view) {
//        Intent intent = new Intent(this, ScanQrCodeActivity.class);
//        startActivity(intent);
//    }
}