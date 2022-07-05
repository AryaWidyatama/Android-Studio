package com.komputerkit.ukomde_afa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

public class ProsesAct extends AppCompatActivity {
    private Button button,btn1;
    private ImageButton imageButton;
    ImageView imgdetail;
    ApiMenuInterface mApiInterface;
    String tittle,overview;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13;
    dataMenu dataMenu;
    private int idkategori;
    Button gh;
    Login login;
    ProgressDialog progressBar;
    Dialog dialog;
    Intent mIntent = getIntent();

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proses);
        getSupportActionBar().hide();

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backActivity();
            }
        });

        boolean installed = appInstalledOrNot("com.whatsapp");

        Intent mIntent = getIntent();
        imgdetail = (ImageView) findViewById(R.id.imgDetailUbahrcv);
        tv1 = (TextView) findViewById(R.id.idKaaategoriUbah);
        tv2 = (TextView) findViewById(R.id.KodeUbah);
        tv3 = (TextView) findViewById(R.id.tvKodeBarangUbah);
        tv4 = (TextView) findViewById(R.id.tvHargaDetailRcv);
        tv5 = (TextView) findViewById(R.id.status1);
        tv6 = (TextView) findViewById(R.id.tvKodeBarangUbahUser);
        gh = (Button) findViewById(R.id.btnwahstr);
        tv7 = (TextView) findViewById(R.id.hargahstr);
        tv8 = (TextView) findViewById(R.id.nomordetailhstr);
        tv9 = (TextView) findViewById(R.id.catatan);
        tv10 = (TextView) findViewById(R.id.jumlahpesanan);
        tv11 = (TextView) findViewById(R.id.createdat);
        tv12 = (TextView) findViewById(R.id.jumlahharga);
        String telp  = mIntent.getStringExtra("nomor");
        String namausr = mIntent.getStringExtra("harga");
        String nomortransaksi = mIntent.getStringExtra("nmrtrk");
        gh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProsesAct.this, "berhasil", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+62"+telp+"&text="+"Nama Pengguna = "+namausr+"\n"+"Nomor Transaksi = "+nomortransaksi+"\n"+"- Pesan / Keluhan anda : "));
                startActivity(intent1);
            }
        });



        tv1.setText(String.valueOf(mIntent.getStringExtra("menu")));
        tv2.setText(mIntent.getStringExtra("harga"));
        tv4.setText(mIntent.getStringExtra("deskripsi"));
        Glide.with(ProsesAct.this)
                .load("" + mIntent.getStringExtra("gambarbarang"))
                .apply(new RequestOptions().override(350, 550))
                .into(imgdetail);

        tv5.setText(mIntent.getStringExtra("kode"));
        tv6.setText(mIntent.getStringExtra("kodebarang"));
        tv7.setText(mIntent.getStringExtra("hargamn"));
        tv8.setText(mIntent.getStringExtra("nomor"));
        tv9.setText(mIntent.getStringExtra("keterangan"));
        tv10.setText(mIntent.getStringExtra("jumlah"));
        tv11.setText(mIntent.getStringExtra("tanggal"));
        tv12.setText(mIntent.getStringExtra("total"));
        tv3.setText(mIntent.getStringExtra("nmrtrk"));

        button = (Button) findViewById(R.id.btnoke);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Gson gson = new Gson();
                String json = sharedPreferences.getString("login", "");
                if (json != "") {
                    login = gson.fromJson(json, Login.class);

                    int idKategori = login.getId();
                    Intent intent = new Intent(getApplicationContext(), HIstoryFragment.class);
                    intent.putExtra("idKategori", idKategori);
                    overridePendingTransition(0, 0);
                    startActivity(intent);

                }
                finish();
            }
        });
    }

    public void backActivity(){
        finish();

    }

    private boolean appInstalledOrNot(String url){
        PackageManager packageManager = getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e){ ;
            app_installed = false;
        }
        return app_installed;
    }


}