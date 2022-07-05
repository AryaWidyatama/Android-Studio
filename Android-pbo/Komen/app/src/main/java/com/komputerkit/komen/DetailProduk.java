package com.komputerkit.komen;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.util.Random;

public class DetailProduk extends AppCompatActivity {

    SharedPreferences sharedPreferences,sharedPreferences1;
    LoginModel loginModel;
    Dialog dialog;
    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);
        TextView tvNamaProduk, tvKategoriProduk, tvHargaProduk, tvDeskripsiProduk, tvJumlahStok, tvNamaToko
                , tvKecProduk, tvKabProduk;
        ImageView imgProduk, imgToko;
        Button btnKunjungiToko, btnBeli;
        ApiInterface mApiInterface;
        getSupportActionBar().hide();
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);



//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        }

//        getSupportActionBar().setTitle("Detail Produk");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientku));
        }





//        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        Intent mIntent = getIntent();
        int stk = mIntent.getIntExtra("Stok",0);
        if (stk == 0){
            dialog=new Dialog(DetailProduk.this);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_stok_kosong);



            Button btnoke=dialog.findViewById(R.id.btnok);
            btnoke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(DetailProduk.this,Home.class);

                    startActivity(intent);
                    finish();
                    //  finish();



                }
            });
            dialog.show();
        }

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
                Intent mIntent1 = getIntent();
                int stk = mIntent1.getIntExtra("Stok",0);
                if (stk == 0){

                    dialog=new Dialog(DetailProduk.this);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                            WindowManager.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setContentView(R.layout.dialog_stok_kosong);



                    Button btnoke=dialog.findViewById(R.id.btnok);
                    btnoke.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(DetailProduk.this,Home.class);

                            startActivity(intent);
                            finish();
                            //  finish();



                        }
                    });
                    dialog.show();
                }else{
                    if (json != ""){
                        Intent intent = new Intent(v.getContext(), OpsiAntar.class);
//                    intent.putExtra("GambarToko", mIntent.getStringExtra("GambarToko"));
//                    intent.putExtra("Owner", mIntent.getStringExtra("Owner"));
                        intent.putExtra("Produk", mIntent.getStringExtra("Produk"));
                        intent.putExtra("Kategori", mIntent.getStringExtra("Kategori"));
                        intent.putExtra("Rp", mIntent.getIntExtra("Rp",0));
                        intent.putExtra("Stok", mIntent.getIntExtra("Stok",0));
                        intent.putExtra("id", mIntent.getIntExtra("id",0));
                        intent.putExtra("KodeProduk", mIntent.getStringExtra("KodeProduk"));
                        intent.putExtra("GambarProduk", mIntent.getStringExtra("GambarProduk"));
                        intent.putExtra("kode",getRandomString(12));
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
            }
        });



    }

    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
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

    public void back(View view) {
        finish();
    }
}