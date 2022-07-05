package com.suhailahnfsella.fudum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KunjungiToko extends AppCompatActivity {

    RecyclerView recyclerView;
    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static KunjungiToko ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kunjungi_toko);
        TextView tvTahunUsaha, tvAlamat2, tvSosmed2, tvNamaToko;
        ImageView imgToko;
        View viewchat;

        getSupportActionBar().setTitle("Toko");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientku));
        }

        Intent mIntent = getIntent();

        tvTahunUsaha = (TextView) findViewById(R.id.tvTahunUsaha);
        tvAlamat2 = (TextView) findViewById(R.id.tvAlamatToko);
        tvSosmed2 = (TextView) findViewById(R.id.tvSosmedToko);
        tvNamaToko = (TextView) findViewById(R.id.tvNamaToko);
        imgToko = (ImageView) findViewById(R.id.btnInsertImgToko);
        viewchat = (View) findViewById(R.id.viewchat);
        viewchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KunjungiToko.this,RegisterChat.class);
                startActivity(intent);
            }
        });

        tvTahunUsaha.setText(String.valueOf(mIntent.getIntExtra("Tahun",0)));
        tvAlamat2.setText(mIntent.getStringExtra("Alamat"));
        tvSosmed2.setText(mIntent.getStringExtra("Sosmed"));
        tvNamaToko.setText(mIntent.getStringExtra("Owner"));
        Glide.with(KunjungiToko.this)
                .load("" + mIntent.getStringExtra("GambarToko"))
                .apply(new RequestOptions().override(0, 200))
                .into(imgToko);

        mRecyclerView = (RecyclerView) findViewById(R.id.rcvProdukToko);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        panggilRetrofit();
        jalankan();
    }

    private void jalankan() {
        ma=this;
        Intent mIntent = getIntent();
        Intent intent = new Intent();
        intent.putExtra("IdToko", mIntent.getIntExtra("IdToko",0));
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

                mAdapter = new AdapterKunjungiToko(ListMenu);
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