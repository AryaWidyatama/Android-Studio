package com.komputerkit.komen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AtributActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences,sharedPreferences1;
    TextView tvJudul,tvPenjelasan;
    SearchView searchView;
    ImageView btnBack;
    ImageView banner;
    Dialog dialog;
    LinearLayout headerjasa;
    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static Kategori ma, mi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atribut);
        getSupportActionBar().hide();

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

        tvJudul.setText("ATRIBUT");
        headerjasa.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.atribut)));
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.atribut));
        }




//        BottomNavigationView navView = findViewById(R.id.navigation);
//        navView.setItemIconTintList(null);
        load();

//        navView.setSelectedItemId(R.id.ic_home);

        Intent intent = getIntent();
        searchView = findViewById(R.id.schome);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.length() > 0) {

                    Call<GetBarang> kontakCall = mApiInterface.getQuery(s);
                    kontakCall.enqueue(new Callback<GetBarang>() {
                        @Override
                        public void onResponse(Call<GetBarang> call, Response<GetBarang>
                                response) {
                            List<Barang> ListMenu = response.body().getData();

                            Log.d("Retrofit Get", "Jumlah data Kontak: " +
                                    String.valueOf(ListMenu.size()));
                            mAdapter = new AdapterAtribut(ListMenu);
                            mRecyclerView.setAdapter(mAdapter);
                        }

                        @Override
                        public void onFailure(Call<GetBarang> call, Throwable t) {
                            dialog=new Dialog(AtributActivity.this);
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

                        Call<GetBarang> kontakCall = mApiInterface.getQuery(newText);
                        kontakCall.enqueue(new Callback<GetBarang>() {
                            @Override
                            public void onResponse(Call<GetBarang> call, Response<GetBarang>
                                    response) {
                                List<Barang> ListMenu = response.body().getData();

                                Log.d("Retrofit Get", "Jumlah data Kontak: " +
                                        String.valueOf(ListMenu.size()));
                                mAdapter = new AdapterAtribut(ListMenu);
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




        mRecyclerView = (RecyclerView) findViewById(R.id.rcvUbahStatusUser);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        panggilRetrofit();

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


    public void panggilRetrofit() {
        Call<GetBarang> kontakCall = mApiInterface.showatribut();
        kontakCall.enqueue(new Callback<GetBarang>() {
            @Override
            public void onResponse(Call<GetBarang> call, Response<GetBarang>
                    response) {
                List<Barang> ListMenu = response.body().getData();

                Log.d("Retrofit Get", "Jumlah data Kontak: " +
                        String.valueOf(ListMenu.size()));
                mAdapter = new AdapterAtribut(ListMenu);
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