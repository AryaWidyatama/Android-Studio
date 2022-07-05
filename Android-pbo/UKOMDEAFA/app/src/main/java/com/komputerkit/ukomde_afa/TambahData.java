package com.komputerkit.ukomde_afa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

import java.math.BigInteger;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahData extends AppCompatActivity {

    private ImageButton imageButton;
    ApiMenuInterface mApiInterface;
    dataMenu dataMenu;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static TambahData ma;
    Login login;
    TextView menu1;
    String haha;

    SharedPreferences sharedPreferences,sharedPreferences1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);
        getSupportActionBar().hide();

        int datamenu = 857326029;


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

//        if (json != ""){
//            login = gson.fromJson(json,Login.class);
//
//            menu1.setText(login.getRelasi());
//            String ge = menu1.getText().toString();
//        }
//
//        if (menu1.equals(datamenu)){
//
//        }



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

        RecyclerView recyclerView = findViewById(R.id.recycleView);
//
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
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
//        DataPenjualAdapter mydatapenjualadapter = new DataPenjualAdapter(DataPenjual,TambahData.this);
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

                mAdapter = new DataPenjualAdapter(KontakList);
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

}