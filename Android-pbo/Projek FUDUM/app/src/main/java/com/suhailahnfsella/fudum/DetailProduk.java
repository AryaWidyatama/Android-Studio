package com.suhailahnfsella.fudum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

public class DetailProduk extends AppCompatActivity {

    SharedPreferences sharedPreferences,sharedPreferences1;
    LoginModel loginModel;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);
        TextView tvNamaProduk, tvKategoriProduk, tvHargaProduk, tvDeskripsiProduk, tvJumlahStok, tvNamaToko
                , tvKecProduk, tvKabProduk;
        ImageView imgProduk, imgToko;
        Button btnKunjungiToko, btnBeli;
        ApiInterface mApiInterface;

        getSupportActionBar().setTitle("Detail Produk");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientku));
        }

//        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        Intent mIntent = getIntent();

        tvNamaProduk = (TextView) findViewById(R.id.tvNamaProduk);
        tvKategoriProduk = (TextView) findViewById(R.id.tvNamaPjg);
        tvHargaProduk = (TextView) findViewById(R.id.tvHargaProduk);
        tvDeskripsiProduk = (TextView) findViewById(R.id.tvDeskripsiProduk);
        tvJumlahStok = (TextView) findViewById(R.id.tvJumlahStok);
//        tvNamaToko = (TextView) findViewById(R.id.tvNamaToko);
//        tvKecProduk = (TextView) findViewById(R.id.tvTglOrder);
//        tvKabProduk = (TextView) findViewById(R.id.tvKabProduk);
        imgProduk = (ImageView) findViewById(R.id.imgProduk);
//        imgToko = (ImageView) findViewById(R.id.btnInsertImgToko);

        tvNamaProduk.setText(mIntent.getStringExtra("Produk"));
        tvKategoriProduk.setText(mIntent.getStringExtra("Kategori"));
        tvHargaProduk.setText(String.valueOf(mIntent.getIntExtra("Rp",0)));
        tvDeskripsiProduk.setText(mIntent.getStringExtra("Deskripsi"));
        tvJumlahStok.setText(String.valueOf(mIntent.getIntExtra("Stok",0)));
//        tvNamaToko.setText(mIntent.getStringExtra("Owner"));
//        tvKecProduk.setText(mIntent.getStringExtra("Kec"));
//        tvKabProduk.setText(mIntent.getStringExtra("Kab"));
        Glide.with(DetailProduk.this)
                .load("" + mIntent.getStringExtra("GambarProduk"))
                .apply(new RequestOptions().override(0, 200))
                .into(imgProduk);
//        Glide.with(DetailProduk.this)
//                .load("" + mIntent.getStringExtra("GambarToko"))
//                .apply(new RequestOptions().override(0, 200))
//                .into(imgToko);

//        edtId.setTag(edtId.getKeyListener());
//        edtId.setKeyListener(null);

        btnBeli = (Button) findViewById(R.id.btnBeli);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");
        btnBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (json != ""){
                    Intent intent = new Intent(v.getContext(), CheckOut.class);
//                    intent.putExtra("GambarToko", mIntent.getStringExtra("GambarToko"));
//                    intent.putExtra("Owner", mIntent.getStringExtra("Owner"));
                    intent.putExtra("Produk", mIntent.getStringExtra("Produk"));
                    intent.putExtra("Kategori", mIntent.getStringExtra("Kategori"));
                    intent.putExtra("Rp", mIntent.getIntExtra("Rp",0));
//                    intent.putExtra("KodeProduk", mIntent.getStringExtra("KodeProduk"));
                    intent.putExtra("GambarProduk", mIntent.getStringExtra("GambarProduk"));
//                    intent.putExtra("Whatsapp", mIntent.getStringExtra("Whatsapp"));
//                    intent.putExtra("Email", mIntent.getStringExtra("Email"));
//                    intent.putExtra("IdToko", mIntent.getIntExtra("IdToko",0));
                    startActivity(intent);
                } else {
                    loginModel = gson.fromJson(json,LoginModel.class);
                    Intent mIntent = new Intent(DetailProduk.this,Login.class);
                    startActivity(mIntent);
                }
            }
        });

//        btnKunjungiToko = (Button) findViewById(R.id.btnKunjungiToko);
//        btnKunjungiToko.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(), KunjungiToko.class);
//                intent.putExtra("Owner", mIntent.getStringExtra("Owner"));
//                intent.putExtra("Alamat", mIntent.getStringExtra("Alamat"));
//                intent.putExtra("Sosmed", mIntent.getStringExtra("Sosmed"));
//                intent.putExtra("Tahun", mIntent.getIntExtra("Tahun",0));
//                intent.putExtra("GambarToko", mIntent.getStringExtra("GambarToko"));
//                intent.putExtra("IdToko", mIntent.getIntExtra("IdToko",0));
//                startActivity(intent);
//            }
//        });

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
}