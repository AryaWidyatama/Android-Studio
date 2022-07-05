package com.komputerkit.ukomde_afa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderPenjual extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    ApiMenuInterface mApiInterface;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences sharedPreferences,sharedPreferences1;
    public static OrderPenjual ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_order_penjual);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiClient.getClient().create(ApiMenuInterface.class);
        ma=this;

        run();
        panggilRetrofit();
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

                mAdapter = new HistoryPenjualAdapter(KontakList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetDataHistory> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }
}