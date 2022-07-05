package com.komputerkit.komen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Random;

public class OpsiAntar extends AppCompatActivity {
    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opsi_antar);
        getSupportActionBar().hide();
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);

    }


    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    public void Lantai1New(View view) {
        int ongkir = 500;
        String lantai1 = "Lantai 1";
        Intent mIntent = getIntent();
        Intent intent = new Intent(OpsiAntar.this, Keterangan_Jumlah.class);
//                    intent.putExtra("GambarToko", mIntent.getStringExtra("GambarToko"));
//                    intent.putExtra("Owner", mIntent.getStringExtra("Owner"));
        intent.putExtra("Produk", mIntent.getStringExtra("Produk"));
        intent.putExtra("Ongkir", ongkir);
        intent.putExtra("Stok", mIntent.getIntExtra("Stok",0));
        intent.putExtra("id", mIntent.getIntExtra("id",0));
        intent.putExtra("Opsi", lantai1);
        intent.putExtra("Kategori", mIntent.getStringExtra("Kategori"));
        intent.putExtra("Rp", mIntent.getIntExtra("Rp",0));
        intent.putExtra("KodeProduk", mIntent.getStringExtra("KodeProduk"));
        intent.putExtra("GambarProduk", mIntent.getStringExtra("GambarProduk"));
        intent.putExtra("kode",getRandomString(12));
//                    intent.putExtra("Whatsapp", mIntent.getStringExtra("Whatsapp"));
//                    intent.putExtra("Email", mIntent.getStringExtra("Email"));
//                    intent.putExtra("IdToko", mIntent.getIntExtra("IdToko",0));
        startActivity(intent);

    }

    public void Lantai2New(View view) {

        int ongkir = 1000;
        String lantai2 = "Lantai 2";
        Intent mIntent = getIntent();
        Intent intent = new Intent(OpsiAntar.this, Lantai2Checkout.class);
//                    intent.putExtra("GambarToko", mIntent.getStringExtra("GambarToko"));
//                    intent.putExtra("Owner", mIntent.getStringExtra("Owner"));
        intent.putExtra("Produk", mIntent.getStringExtra("Produk"));
        intent.putExtra("Opsi", lantai2);
        intent.putExtra("Kategori", mIntent.getStringExtra("Kategori"));
        intent.putExtra("Stok", mIntent.getIntExtra("Stok",0));
        intent.putExtra("id", mIntent.getIntExtra("id",0));
        intent.putExtra("Rp", mIntent.getIntExtra("Rp",0));
        intent.putExtra("Ongkir", ongkir);
        intent.putExtra("KodeProduk", mIntent.getStringExtra("KodeProduk"));
        intent.putExtra("GambarProduk", mIntent.getStringExtra("GambarProduk"));
        intent.putExtra("kode",getRandomString(12));
//                    intent.putExtra("Whatsapp", mIntent.getStringExtra("Whatsapp"));
//                    intent.putExtra("Email", mIntent.getStringExtra("Email"));
//                    intent.putExtra("IdToko", mIntent.getIntExtra("IdToko",0));
        startActivity(intent);

    }

    public void LuarSekolah(View view) {
        int ongkirluar = 0;
        String luar = "luar smenda";
        Intent mIntent = getIntent();
        Intent intent = new Intent(OpsiAntar.this, CheckoutLuarSmenda.class);
//                    intent.putExtra("GambarToko", mIntent.getStringExtra("GambarToko"));
//                    intent.putExtra("Owner", mIntent.getStringExtra("Owner"));
        intent.putExtra("Produk", mIntent.getStringExtra("Produk"));
        intent.putExtra("Opsi", luar);
        intent.putExtra("Kategori", mIntent.getStringExtra("Kategori"));
        intent.putExtra("Rp", mIntent.getIntExtra("Rp",0));
        intent.putExtra("Ongkir", ongkirluar);
        intent.putExtra("KodeProduk", mIntent.getStringExtra("KodeProduk"));
        intent.putExtra("GambarProduk", mIntent.getStringExtra("GambarProduk"));
        intent.putExtra("kode",getRandomString(12));
        intent.putExtra("Stok", mIntent.getIntExtra("Stok",0));
        intent.putExtra("id", mIntent.getIntExtra("id",0));
//                    intent.putExtra("Whatsapp", mIntent.getStringExtra("Whatsapp"));
//                    intent.putExtra("Email", mIntent.getStringExtra("Email"));
//                    intent.putExtra("IdToko", mIntent.getIntExtra("IdToko",0));
        startActivity(intent);
    }

    public void ambildikoperasi(View view) { int ongkirluar = 0;
        String luar = "Dikoperasi";
        Intent mIntent = getIntent();
        Intent intent = new Intent(OpsiAntar.this, Checkoutbelidikoperasi.class);
//                    intent.putExtra("GambarToko", mIntent.getStringExtra("GambarToko"));
//                    intent.putExtra("Owner", mIntent.getStringExtra("Owner"));
        intent.putExtra("Produk", mIntent.getStringExtra("Produk"));
        intent.putExtra("Opsi", luar);
        intent.putExtra("Kategori", mIntent.getStringExtra("Kategori"));
        intent.putExtra("Rp", mIntent.getIntExtra("Rp",0));
        intent.putExtra("Ongkir", ongkirluar);
        intent.putExtra("KodeProduk", mIntent.getStringExtra("KodeProduk"));
        intent.putExtra("Stok", mIntent.getIntExtra("Stok",0));
        intent.putExtra("id", mIntent.getIntExtra("id",0));
        intent.putExtra("GambarProduk", mIntent.getStringExtra("GambarProduk"));
        intent.putExtra("kode",getRandomString(12));
//                    intent.putExtra("Whatsapp", mIntent.getStringExtra("Whatsapp"));
//                    intent.putExtra("Email", mIntent.getStringExtra("Email"));
//                    intent.putExtra("IdToko", mIntent.getIntExtra("IdToko",0));
        startActivity(intent);

    }
}