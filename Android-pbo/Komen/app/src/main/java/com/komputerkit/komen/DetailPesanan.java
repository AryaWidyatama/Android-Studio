package com.komputerkit.komen;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailPesanan extends AppCompatActivity {
    TextView a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan);

        a1 = findViewById(R.id.tvNamaPelanggan);
        a2 = findViewById(R.id.tvKelas);
        a3 = findViewById(R.id.tvJurusan);
        a4 = findViewById(R.id.tvNamaProduk);
        a5 = findViewById(R.id.tvDeskripsi);
        a6 = findViewById(R.id.tvHarga);
        a7 = findViewById(R.id.tvJumlahBarang);
        a8 = findViewById(R.id.tvKodeBarang);
        a9 = findViewById(R.id.tvTanggalPesanan);
        a10 = findViewById(R.id.tvStatusPesanan);
        a11 = findViewById(R.id.tvPengiriman);
        imageView = findViewById(R.id.imgProduk);

        Intent intent1 = getIntent();
        a1.setText(intent1.getStringExtra("NamaP"));
        a2.setText(intent1.getStringExtra("KelasP"));
        a3.setText(intent1.getStringExtra("JurusanP"));
        a4.setText(intent1.getStringExtra("NamaB"));
        a5.setText(intent1.getStringExtra("DescP"));
        a6.setText(String.valueOf(intent1.getIntExtra("HargaB",0)));
        a7.setText(String.valueOf(intent1.getIntExtra("JumlahP",0)));
        a8.setText(String.valueOf(intent1.getIntExtra("JumlahH",0)));
        a9.setText(intent1.getStringExtra("Tanggal"));
        a10.setText(intent1.getStringExtra("Status"));
        a11.setText(intent1.getStringExtra("Tujuan"));
        Glide.with(DetailPesanan.this)
                .load("" + intent1.getStringExtra("Gambar"))
                .apply(new RequestOptions().override(0, 200))
                .into(imageView);


        getSupportActionBar().setTitle("Detail Pesanan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(this.getResources().getColor(R.color.limedash));
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


    public void QrCode(View view) {
        Intent intent1 = getIntent();
        Intent intent = new Intent(DetailPesanan.this,QrCodePesan.class);
        intent.putExtra("kode", intent1.getStringExtra("KodeP"));
        startActivity(intent);
    }

    public void wa(View view) {
        Intent intent2 = getIntent();
        String kode = intent2.getStringExtra("KodeP");
        Intent intent1 = new Intent(Intent.ACTION_VIEW);
        intent1.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+62"+"83856469750"+"&text="+"Kapan Barang Saya Dikirim yaa ?"+"\n"+"Kode Pesanan Saya = "+kode));
        startActivity(intent1);


    }
}
