package com.komputerkit.komen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.komputerkit.komen.ServerConfig.REQUEST_WRITE_PERMISSION;

public class Home extends AppCompatActivity {
    private int currentApiVersion;
    android.widget.SearchView searchView;
    SharedPreferences sharedPreferences,sharedPreferences1;
    RecyclerView recyclerView;
    ApiInterface mApiInterface;
    SiswaModel loginre;
    ProgressDialog progressBar;
    Dialog dialog;
    TextView tvNamaAkun;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static Home ma, mi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setItemIconTintList(null);

        getSupportActionBar().hide();
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);

        progressBar = new ProgressDialog(Home.this);
        progressBar.show();
        progressBar.setContentView(R.layout.progres_dialog);
        progressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);




        requestPermission();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login", "");

        searchView = findViewById(R.id.schome);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.length() > 0) {

                    Call<GetBarang> kontakCall = mApiInterface.getQueryhome(s);
                    kontakCall.enqueue(new Callback<GetBarang>() {
                        @Override
                        public void onResponse(Call<GetBarang> call, Response<GetBarang>
                                response) {
                            List<Barang> ListMenu = response.body().getData();
                            if (ListMenu == null){
                                dialog=new Dialog(Home.this);
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
                            }else{
                                Log.d("Retrofit Get", "Jumlah data Kontak: " +
                                        String.valueOf(ListMenu.size()));
                                mAdapter = new AdapterProduk(ListMenu);
                                mRecyclerView.setAdapter(mAdapter);
                            }


                        }

                        @Override
                        public void onFailure(Call<GetBarang> call, Throwable t) {

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

                    Call<GetBarang> kontakCall = mApiInterface.getQueryhome(newText);
                    kontakCall.enqueue(new Callback<GetBarang>() {
                        @Override
                        public void onResponse(Call<GetBarang> call, Response<GetBarang>
                                response) {
                            List<Barang> ListMenu = response.body().getData();

                            Log.d("Retrofit Get", "Jumlah data Kontak: " +
                                    String.valueOf(ListMenu.size()));
                            mAdapter = new AdapterProduk(ListMenu);
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

        getSupportActionBar().hide();
        tvNamaAkun = findViewById(R.id.tvNamaAkun);
        if (json != "") {
            loginre = gson.fromJson(json, SiswaModel.class);
            tvNamaAkun.setAlpha(1);
            tvNamaAkun.setText(loginre.getNamaLengkap());
        } else {
            tvNamaAkun.setAlpha(0);
        }


        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.ic_home);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:

                        return true;
                    case R.id.ic_apaitu:
                        startActivity(new Intent(getApplicationContext(), PerkenalanActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.ic_profil:
                        if (json != "") {
                            loginre = gson.fromJson(json, SiswaModel.class);
//                           if (loginre.getStatus() == 1) {
                                Intent mIntent = new Intent(Home.this, AccountV2.class);
                                overridePendingTransition(0,0);
                                startActivity(mIntent);
//                            } else if (loginre.getStatus() == 2) {
////                                Intent mIntent = new Intent(Home.this, AccountV1.class);
////                                startActivity(mIntent);
//                            }
                        } else {
                            startActivity(new Intent(Home.this, Login.class));
                            overridePendingTransition(0,0);
                        }
                        return true;
               }
                return false;
            }
        });
//
        mRecyclerView = (RecyclerView) findViewById(R.id.rcvHome);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
       mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        ma = this;

          panggilRetrofit();

    }







    public void btnMknBrt (View view){
        Integer idkategori = 1;
        Intent intent = new Intent(this, OpsiProduk.class);
        intent.putExtra("IdKategori", idkategori);
        startActivity(intent);
    }

    public void btnMknRgn (View view){
        Integer idkategori = 2;
        Intent intent = new Intent(this, AtributActivity.class);
        intent.putExtra("IdKategori", idkategori);
        startActivity(intent);
    }
//


    public void panggilRetrofit() {
        Call<GetBarang> kontakCall = mApiInterface.getBarang();
        kontakCall.enqueue(new Callback<GetBarang>() {
            @Override
            public void onResponse(Call<GetBarang> call, Response<GetBarang>
                    response) {
                List<Barang> ListMenu = response.body().getData();
                progressBar.dismiss();

                Log.d("Retrofit Get", "Jumlah data Kontak: " +
                        String.valueOf(ListMenu.size()));
                mAdapter = new AdapterProduk(ListMenu);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetBarang> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                dialog=new Dialog(Home.this);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_home_gagal);
                Button btnoke=dialog.findViewById(R.id.btnok);
                btnoke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressBar.show();
                        dialog.dismiss();
                        panggilRetrofit();



                    }
                });
                dialog.show();

            }
        });
    }

    public void jasa(View view) {

        Intent intent = new Intent(this, JasaActivity.class);
      //  intent.putExtra("IdKategori", idkategori);
        startActivity(intent);
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);

        } else {

            Toast.makeText(this, "izin belm di berikan", Toast.LENGTH_SHORT).show();
        }
    }
}