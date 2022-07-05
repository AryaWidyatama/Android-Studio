package com.komputerkit.ukomde_afa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class History extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    ApiMenuInterface mApiInterface;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences sharedPreferences,sharedPreferences1;
    public static History ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().hide();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");


        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiClient.getClient().create(ApiMenuInterface.class);
        ma=this;


        BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setItemIconTintList(null);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setSelectedItemId(R.id.home1);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.pesan:

                        return true;
                    case R.id.home1:
                        startActivity(new Intent(getApplicationContext()
                                ,HomeUtamaActivity.class));
                        overridePendingTransition(0,0);

                        return true;
                    case R.id.info1:
                        startActivity(new Intent(getApplicationContext()
                                ,InfoActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

        panggilRetrofit();
        run();

    }

    public void run(){
        ma=this;
        Intent mIntent = getIntent();
        Intent intent = new Intent();
        intent.putExtra("idKategori", mIntent.getIntExtra("idKategori",0));
    }

    public void panggilRetrofit(){
        Call<GetDataHistory> kontakCall = mApiInterface.getHistory();

        kontakCall.enqueue(new Callback<GetDataHistory>() {
            @Override
            public void onResponse(Call<GetDataHistory> call, Response<GetDataHistory>
                    response) {
                List<DataHistory> KontakList = response.body().getData();
                Log.d("Retrofit Get", "Jumlah data Kontak: " +
                        String.valueOf(KontakList.size()));

                mAdapter = new HistoryAdapter(KontakList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetDataHistory> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }
}