package com.komputerkit.komen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PBKAct extends AppCompatActivity {

    TextView tvJudul;
    ImageView btnBack,banner;
    LinearLayout headerjasa;
    SearchView searchView;
    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pbkact);

        getSupportActionBar().hide();


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        panggilRetrofit();

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

           searchView = findViewById(R.id.sckatjasa);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
              if (s.length() > 0){
                  Call<GetJasaModel> kontakCall = mApiInterface.pbk(s);
                  kontakCall.enqueue(new Callback<GetJasaModel>() {
                      @Override
                      public void onResponse(Call<GetJasaModel> call, Response<GetJasaModel>
                              response) {
                          List<JasaModel> ListMenu = response.body().getData();

                          Log.d("Retrofit Get", "Jumlah data Kontak: " +
                                  String.valueOf(ListMenu.size()));
                          mAdapter = new AdapterPBK(ListMenu);
                          mRecyclerView.setAdapter(mAdapter);
                      }

                      @Override
                      public void onFailure(Call<GetJasaModel> call, Throwable t) {
                          Log.e("Retrofit Get", t.toString());
                      }
                  });
              }else {
                  panggilRetrofit();
              }
                return true;

            }

        });
        tvJudul.setText("Perbankan");
        headerjasa.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.pbk)));
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.pbk));
        }
        banner.setImageResource(R.drawable.banner_pbk);
    }

    public void panggilRetrofit() {
        Call<GetJasaModel> kontakCall = mApiInterface.showpbk();
        kontakCall.enqueue(new Callback<GetJasaModel>() {
            @Override
            public void onResponse(Call<GetJasaModel> call, Response<GetJasaModel>
                    response) {
                List<JasaModel> ListMenu = response.body().getData();

                Log.d("Retrofit Get", "Jumlah data Kontak: " +
                        String.valueOf(ListMenu.size()));
                mAdapter = new AdapterPBK(ListMenu);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetJasaModel> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }
}