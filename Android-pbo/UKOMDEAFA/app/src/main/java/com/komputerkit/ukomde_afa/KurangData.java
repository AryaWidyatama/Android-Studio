package com.komputerkit.ukomde_afa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KurangData extends AppCompatActivity {

    private ImageButton imageButton;
    ApiMenuInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static KurangData ma;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurang_data);
        getSupportActionBar().hide();


        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backActivity();
            }
        });


        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiClient.getClient().create(ApiMenuInterface.class);
        ma=this;

//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        DataPenjual[] DataPenjual = new DataPenjual[]{
//                new DataPenjual("Fresh Flower Box","- Mix fresh flower & custom box or bamboo bag or etc" +
//                        "2. Data Penjelasan paket ",R.drawable.b1,"A0006","Rp.215000"),
//                new DataPenjual("tes","tes",R.drawable.b1,"t12","Rp.215000"),
//                new DataPenjual("tes","tes",R.drawable.b1,"t12","Rp.215000"),
//                new DataPenjual("tes","tes",R.drawable.b1,"t12","Rp.215000"),
//                new DataPenjual("tes","tes",R.drawable.b1,"t12","Rp.215000"),
//                new DataPenjual("tes","tes",R.drawable.b1,"t12","Rp.215000"),
//                new DataPenjual("tes","tes",R.drawable.b1,"t12","Rp.215000"),
//                new DataPenjual("tes","tes",R.drawable.b1,"t12","Rp.215000"),
//                new DataPenjual("tes","tes",R.drawable.b1,"t12","Rp.215000"),
//        };
//
//        KurangDataAdapter mydatapenjualadapter = new KurangDataAdapter(DataPenjual,KurangData.this);
//        recyclerView.setAdapter(mydatapenjualadapter);

        panggilRetrofit();
run();


    }

    public void run(){
        ma=this;
        Intent mIntent = getIntent();
        Intent intent = new Intent();
        intent.putExtra("idKategori", mIntent.getStringExtra("idKategori"));
    }
    public void panggilRetrofit(){
        Call<GetDaataMenu> kontakCall = mApiInterface.getMenu();
        kontakCall.enqueue(new Callback<GetDaataMenu>() {
            @Override
            public void onResponse(Call<GetDaataMenu> call, Response<GetDaataMenu>
                    response) {
                List<dataMenu> KontakList = response.body().getData();
                Log.d("Retrofit Get", "Jumlah data Kontak: " +
                        String.valueOf(KontakList.size()));
                mAdapter = new KurangDataAdapter(KontakList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetDaataMenu> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }

    public void backActivity(){
        Intent intent = new Intent(this,PenjualActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.kanan, R.anim.kanan1);
        finish();


    }

    public void refresh() {
        Call<GetDaataMenu> kontakCall = mApiInterface.getMenu();
        kontakCall.enqueue(new Callback<GetDaataMenu>() {
            @Override
            public void onResponse(Call<GetDaataMenu> call, Response<GetDaataMenu>
                    response) {
                List<dataMenu> KontakList = response.body().getData();
                Log.d("Retrofit Get", "Jumlah data Kontak: " +
                        String.valueOf(KontakList.size()));
                mAdapter = new KurangDataAdapter(KontakList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetDaataMenu> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }
}