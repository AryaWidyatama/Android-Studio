package com.suhailahnfsella.fudum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Toko extends AppCompatActivity {

    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    LoginModel loginModel;
    TokoModel tokoModel;
    SharedPreferences sharedPreferences,sharedPreferences1;
    ImageView imgchat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko);
        int hj;
        BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setItemIconTintList(null);

        getSupportActionBar().setTitle("Toko Saya");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientku));
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

        if (json != "") {
         tampiltoko();
        }

        navView.setSelectedItemId(R.id.ic_home);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        startActivity(new Intent(getApplicationContext()
                                ,Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.ic_profil:
                        startActivity(new Intent(getApplicationContext()
                                ,AccountV1.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        imgchat = (ImageView) findViewById(R.id.imageView10);
        imgchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Toko.this,LoginChat.class);
                startActivity(intent);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.rcvProdukToko);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        panggilRetrofit();
    //    tampiltoko();
    }

    private void tampiltoko(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");
        loginModel = gson.fromJson(json,LoginModel.class);

        Integer id = loginModel.getId();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TokoModel> call = apiInterface.getTokoUser(id);
        call.enqueue(new Callback<TokoModel>() {
            @Override
            public void onResponse(Call<TokoModel> call, Response<TokoModel> response) {
                if (response.isSuccessful()) {

               //     List<TokoModel> ListMenu = response.body().getData();
//                    tokoModel = ListMenu.size();
//                    Log.d("Retrofit Get", "Jumlah data Kontak: " +
//                            String.valueOf(ListMenu.size()));

                    String gf = response.body().getNamatoko();
                    String gr = response.body().getFototoko();
                    String gt = response.body().getTahuntoko();
                    String ga = response.body().getAlamattoko();
                    String gs = response.body().getSosmed();

                    ImageView imgToko = findViewById(R.id.btnInsertImgToko);
                    TextView tvNamaToko = findViewById(R.id.tvNamaToko);
                    TextView tvTahunUsaha = findViewById(R.id.tvTahunUsaha);
                    TextView tvAlamatToko = findViewById(R.id.tvAlamatToko);
                    TextView tvSosmedToko = findViewById(R.id.tvSosmedToko);

                    Glide.with(Toko.this)
                            .load("" + gr)
                            .apply(new RequestOptions().override(0, 200))
                            .into(imgToko);
                    tvNamaToko.setText(gf);
                    tvTahunUsaha.setText(gt);
                    tvAlamatToko.setText(ga);
                    tvSosmedToko.setText(gs);
//                    Toast.makeText(Toko.this, "data ="+gf, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Toko.this, "t", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TokoModel> call, Throwable t) {
                String d = t.getMessage();
                Toast.makeText(Toko.this, "gagal"+d, Toast.LENGTH_SHORT).show();
                                    Log.d("Retrofit Get", "Jumlah data Kontak: " +
                            String.valueOf(d));
//                if (loginModel.getStatus() == 0) {
//                    Intent intent = new Intent(Toko.this, AccountV1.class);
//                    startActivity(intent);
//                } else if (loginModel.getStatus() == 1) {
//                    Intent intent = new Intent(Toko.this, AccountV2.class);
//                    startActivity(intent);
//                }
            }
        });
    }



    private void panggilRetrofit() {
        Call<GetMenu> kontakCall = mApiInterface.getMenu();
        kontakCall.enqueue(new Callback<GetMenu>() {
            @Override
            public void onResponse(Call<GetMenu> call, Response<GetMenu>
                    response) {
                List<Menu> ListMenu = response.body().getData();
                Collections.sort(ListMenu, new Comparator<Menu>() {
                    @Override
                    public int compare(Menu lhs, Menu rhs) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return lhs.getIdmenu() > rhs.getIdmenu() ? -1 : (lhs.getIdmenu() < rhs.getIdmenu() ) ? 1 : 0;
                    }
                });
                Log.d("Retrofit Get", "Jumlah data Kontak: " +
                        String.valueOf(ListMenu.size()));
                mAdapter = new AdapterProdukToko(ListMenu);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetMenu> call, Throwable t) {
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

    public void btnTambahProduk(View view) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");
        loginModel = gson.fromJson(json,LoginModel.class);

        Integer id = loginModel.getId();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TokoModel> call = apiInterface.getTokoUser(id);
        call.enqueue(new Callback<TokoModel>() {
            @Override
            public void onResponse(Call<TokoModel> call, Response<TokoModel> response) {
                if (response.isSuccessful()) {
                    String gf = response.body().getNamatoko();
                    String gr = response.body().getFototoko();
                    String gt = response.body().getTahuntoko();
                    String ga = response.body().getAlamattoko();
                    String gs = response.body().getSosmed();
                    String kec = response.body().getKecamatan();
                    String kab = response.body().getKabupaten();
                    String sosmed = response.body().getSosmed();
                    String wa = response.body().getWhatsapp();
                    String ml = response.body().getEmail();

                    Intent intent = new Intent(Toko.this, InsertProduk.class);
                    intent.putExtra("namatoko", gf);
                    intent.putExtra("fototoko", gr);
                    intent.putExtra("tahuntoko", gt);
                    intent.putExtra("alamattoko", ga);
                    intent.putExtra("sosmedtoko", gs);
                    intent.putExtra("kec", kec);
                    intent.putExtra("kab", kab);
                    intent.putExtra("sosmed", sosmed);
                    intent.putExtra("wa", wa);
                    intent.putExtra("ml", ml);
                    startActivity(intent);
                } else {
                    Toast.makeText(Toko.this, "t", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TokoModel> call, Throwable t) {
                String d = t.getMessage();
                Toast.makeText(Toko.this, "gagal"+d, Toast.LENGTH_SHORT).show();
                Log.d("Retrofit Get", "Jumlah data Kontak: " +
                        String.valueOf(d));
            }
        });
    }

    public void btnHistoryOrder(View view) {
        Intent intent = new Intent(this, HistoryPenjual.class);
        startActivity(intent);
    }
}