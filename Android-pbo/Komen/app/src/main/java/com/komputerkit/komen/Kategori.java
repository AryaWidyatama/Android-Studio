package com.komputerkit.komen;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Kategori extends AppCompatActivity {

//    LoginModel loginModel;
    SharedPreferences sharedPreferences,sharedPreferences1;
    TextView tvJudul,tvPenjelasan;
    SearchView searchView;
    ImageView btnBack;
    Dialog dialog;
    ImageView banner;
    LinearLayout headerjasa;
    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static Kategori ma, mi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makanan_berat);

//        BottomNavigationView navView = findViewById(R.id.navigation);
//        navView.setItemIconTintList(null);
        load();

        tvJudul = findViewById(R.id.tvJudul);
        headerjasa = findViewById(R.id.headerjasa);
        banner = findViewById(R.id.banner);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tvJudul.setText("ALAT TULIS");
        headerjasa.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.ATK)));
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.ATK));
        }


//        navView.setSelectedItemId(R.id.ic_home);

        Intent intent = getIntent();

        searchView = findViewById(R.id.schome);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.length() > 0) {

                    Call<GetBarang> kontakCall = mApiInterface.getQuery1(s);
                    kontakCall.enqueue(new Callback<GetBarang>() {
                        @Override
                        public void onResponse(Call<GetBarang> call, Response<GetBarang>
                                response) {
                            List<Barang> ListMenu = response.body().getData();

                            Log.d("Retrofit Get", "Jumlah data Kontak: " +
                                    String.valueOf(ListMenu.size()));
                            mAdapter = new AdapterKategori(ListMenu);
                            mRecyclerView.setAdapter(mAdapter);
                        }

                        @Override
                        public void onFailure(Call<GetBarang> call, Throwable t) {
                            dialog=new Dialog(Kategori.this);
                            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                                    WindowManager.LayoutParams.WRAP_CONTENT);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.setContentView(R.layout.dialog_home_gagal);
                            Button btnoke=dialog.findViewById(R.id.btnok);
                            btnoke.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    dialog.dismiss();
                                    panggilRetrofit();



                                }
                            });
                            dialog.show();
                        }
                    });

                }else {
                    panggilRetrofit();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {

                    Call<GetBarang> kontakCall = mApiInterface.getQuery1(newText);
                    kontakCall.enqueue(new Callback<GetBarang>() {
                        @Override
                        public void onResponse(Call<GetBarang> call, Response<GetBarang>
                                response) {
                            List<Barang> ListMenu = response.body().getData();

                            Log.d("Retrofit Get", "Jumlah data Kontak: " +
                                    String.valueOf(ListMenu.size()));
                            mAdapter = new AdapterKategori(ListMenu);
                            mRecyclerView.setAdapter(mAdapter);
                        }

                        @Override
                        public void onFailure(Call<GetBarang> call, Throwable t) {
                            Log.e("Retrofit Get", t.toString());
                        }
                    });

                }else {
                    panggilRetrofit();
                }

                return true;
            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

//        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.ic_home:
//                            startActivity(new Intent(getApplicationContext()
//                            ,Home.class));
//                            overridePendingTransition(0,0);
//                        return true;
//                    case R.id.ic_profil:
//                        if (json != ""){
//                            loginModel = gson.fromJson(json,LoginModel.class);
//                            if (loginModel.getStatus() == 0) {
//                                Intent mIntent = new Intent(Kategori.this,AccountV1.class);
//                                startActivity(mIntent);
//                            }
//                            else if (loginModel.getStatus() == 1){
//                                Intent mIntent = new Intent(Kategori.this,AccountV2.class);
//                                startActivity(mIntent);
//                            }
//                            else if (loginModel.getStatus() == 2){
//                                Intent mIntent = new Intent(Kategori.this,AccountV1.class);
//                                startActivity(mIntent);
//                            }
//                        } else {
//                            Intent mIntent = new Intent(Kategori.this,Login.class);
//                            startActivity(mIntent);
//                        }
//                        return true;
//                }
//                return false;
//            }
//        });

//        int value = intent.getIntExtra("IdKategori",0);
//
//        if (value == 1){
//            tvJudul.setText("ALAT TULIS");
//            tvPenjelasan.setText("Disini kalian bisa mencari alat tulis yang dijual dalam Koperasi Smenda");
//        } else if (value == 2) {
//            tvJudul.setText("ATRIBUT SEKOLAH");
//            tvPenjelasan.setText("Pada halaman ini kamu bisa mencari Atribut sekolah yang kamu butuhkan");
//        }
//        } else if (value == 3){
//            getSupportActionBar().setTitle("Kategori Minuman");
//        } else if (value == 4){
//            getSupportActionBar().setTitle("Kategori Herbal");
//        } else if (value == 5){
//            getSupportActionBar().setTitle("Kategori Lainnya");
//        }

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        ActionBar actionBar = getSupportActionBar();
//            if (actionBar !=null){
//             actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientku));
//            }

        mRecyclerView = (RecyclerView) findViewById(R.id.rcvUbahStatusUser);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        panggilRetrofit();
        jalankan();
    }
    public void load(){
        tvPenjelasan = findViewById(R.id.tvPenjelasan);
        tvJudul = findViewById(R.id.tvJudul);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void jalankan() {
        ma=this;
        Intent mIntent = getIntent();
        Intent intent = new Intent();
        intent.putExtra("IdKategori", mIntent.getIntExtra("IdKategori",0));
    }

    public void panggilRetrofit() {
        Call<GetBarang> kontakCall = mApiInterface.showatk();
        kontakCall.enqueue(new Callback<GetBarang>() {
            @Override
            public void onResponse(Call<GetBarang> call, Response<GetBarang>
                    response) {
                List<Barang> ListMenu = response.body().getData();

                Log.d("Retrofit Get", "Jumlah data Kontak: " +
                        String.valueOf(ListMenu.size()));
                mAdapter = new AdapterKategori(ListMenu);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetBarang> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
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
}