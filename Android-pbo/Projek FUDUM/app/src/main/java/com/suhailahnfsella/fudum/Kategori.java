package com.suhailahnfsella.fudum;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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
    ImageView btnBack;
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

//        navView.setSelectedItemId(R.id.ic_home);

        Intent intent = getIntent();

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

        int value = intent.getIntExtra("IdKategori",0);

        if (value == 1){
            tvJudul.setText("ALAT TULIS");
            tvPenjelasan.setText("Disini kalian bisa mencari alat tulis yang dijual dalam Koperasi Smenda");
        } else if (value == 2) {
            tvJudul.setText("ATRIBUT SEKOLAH");
            tvPenjelasan.setText("Pada halaman ini kamu bisa mencari Atribut sekolah yang kamu butuhkan");
        }
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
                mAdapter = new AdapterKategori(ListMenu);
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
}