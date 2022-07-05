package com.komputerkit.komen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

public class DetailJasa extends AppCompatActivity {
    TextView tvNamaProduk, tvKategoriProduk, tvHargaProduk, tvDeskripsiProduk, tvJumlahStok, tvNamaToko
            , tvKecProduk, tvKabProduk;
    ImageView imgProduk, imgToko;
    SiswaModel loginModel;
    Button btnKunjungiToko, btnBeli;
    ApiInterface mApiInterface;
    SharedPreferences sharedPreferences,sharedPreferences1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jasa);
        getSupportActionBar().hide();
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientku));
        }

        Intent mIntent = getIntent();

        tvNamaProduk = (TextView) findViewById(R.id.tvNamaProduk);
        tvKategoriProduk = (TextView) findViewById(R.id.tvNamaPjg);

        tvDeskripsiProduk = (TextView) findViewById(R.id.tvDeskripsiProduk);
        tvJumlahStok = (TextView) findViewById(R.id.tvJumlahStok);
//        tvNamaToko = (TextView) findViewById(R.id.tvNamaToko);
//        tvKecProduk = (TextView) findViewById(R.id.tvTglOrder);
//        tvKabProduk = (TextView) findViewById(R.id.tvKabProduk);
        imgProduk = (ImageView) findViewById(R.id.imgProduk);
//        imgToko = (ImageView) findViewById(R.id.btnInsertImgToko);

        tvNamaProduk.setText(mIntent.getStringExtra("jasa"));
        tvKategoriProduk.setText(mIntent.getStringExtra("namapenyedia"));

        tvDeskripsiProduk.setText(mIntent.getStringExtra("deskripsi"));
        tvJumlahStok.setText(mIntent.getStringExtra("pengalaman"));
//        tvNamaToko.setText(mIntent.getStringExtra("Owner"));
//        tvKecProduk.setText(mIntent.getStringExtra("Kec"));
//        tvKabProduk.setText(mIntent.getStringExtra("Kab"));
        Glide.with(DetailJasa.this)
                .load("" + mIntent.getStringExtra("gambarjasa"))
                .apply(new RequestOptions().override(0, 200))
                .into(imgProduk);

        btnBeli = (Button) findViewById(R.id.btnBeli);
        String hg = mIntent.getStringExtra("NoTelp");

        btnBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DetailJasa.this);
                Gson gson = new Gson();
                String json = sharedPreferences.getString("login","");
                if (json!=""){
                    loginModel = gson.fromJson(json,SiswaModel.class);
                    String username = loginModel.getUsername();


                    Intent intent1 = new Intent(Intent.ACTION_VIEW);
                    intent1.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+62"+hg+"&text="+"\n"+"- Username saya =  "+username+"Saya mau pakai jasanya dong!"+"\n"+"Bagaimana sistemnya yaa? "));
                    startActivity(intent1);
                }else {
                    loginModel = gson.fromJson(json,SiswaModel.class);
                    Intent mIntent = new Intent(DetailJasa.this,Login.class);
                    startActivity(mIntent);
                }

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


    public void back(View view) {
       finish();
    }
}