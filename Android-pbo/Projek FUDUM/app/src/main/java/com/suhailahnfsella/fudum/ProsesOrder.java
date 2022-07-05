package com.suhailahnfsella.fudum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProsesOrder extends AppCompatActivity {

    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    LoginModel loginModel;
    TokoModel tokoModel;
    SharedPreferences sharedPreferences,sharedPreferences1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proses_order);

        getSupportActionBar().setTitle("Proses Pesanan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientku));
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

        if (json != "") {
            tampilMenu();
        }
    }

    private void tampilMenu(){
        Intent intent = getIntent();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");
        loginModel = gson.fromJson(json,LoginModel.class);

        String request = intent.getStringExtra("KodeProduk");

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Menu> call = apiInterface.getMenuReq(request);
        call.enqueue(new Callback<Menu>() {
            @Override
            public void onResponse(Call<Menu> call, Response<Menu> response) {
                if (response.isSuccessful()) {

                    //     List<TokoModel> ListMenu = response.body().getData();
//                    tokoModel = ListMenu.size();
//                    Log.d("Retrofit Get", "Jumlah data Kontak: " +
//                            String.valueOf(ListMenu.size()));

                    String gs = response.body().getGambar();
                    ImageView imgProduk = findViewById(R.id.imgProduk);
                    Glide.with(ProsesOrder.this)
                            .load("" + gs)
                            .apply(new RequestOptions().override(0, 200))
                            .into(imgProduk);

                    String gf = response.body().getProduk();
                    String gr = response.body().getKategori();
                    Integer gt = response.body().getHarga();
                    Integer ga = response.body().getStok();
                    String penerima = getIntent().getStringExtra("NamaPemesan");
                    String alamat = getIntent().getStringExtra("AlamatPemesan");
                    String kode = getIntent().getStringExtra("KodeProduk");
                    Integer jumlah = getIntent().getIntExtra("JumlahProduk",0);
                    Integer total = jumlah * gt;
                    String tgl = getIntent().getStringExtra("TglOrder");
                    String via = getIntent().getStringExtra("Via");

                    TextView tvNamaProduk = findViewById(R.id.tvNamaProduk);
                    TextView tvKategori = findViewById(R.id.tvNamaPjg);
                    TextView tvHarga = findViewById(R.id.tvHargaProduk);
                    TextView tvStok = findViewById(R.id.tvJumlahStok);
                    TextView tvNamaPenerima = findViewById(R.id.tvNamaPenerima);
                    TextView tvAlamatPenerima = findViewById(R.id.tvAlamatPenerima);
                    TextView tvKodeProduk = findViewById(R.id.tvKodeProduk);
                    TextView tvJumlahProduk = findViewById(R.id.tvJumlahProduk);
                    TextView tvtotalPesan = findViewById(R.id.tvtotalPesan);
                    TextView tvTglOrder = findViewById(R.id.tvTglOrder);
                    TextView tvViaPesan = findViewById(R.id.tvViaPesan);

                    tvNamaProduk.setText(gf);
                    tvKategori.setText(gr);
                    tvHarga.setText(gt.toString());
                    tvStok.setText(ga.toString());
                    tvNamaPenerima.setText(penerima);
                    tvAlamatPenerima.setText(alamat);
                    tvKodeProduk.setText(kode);
                    tvJumlahProduk.setText(jumlah.toString());
                    tvtotalPesan.setText(total.toString());
                    tvTglOrder.setText(tgl);
                    tvViaPesan.setText(via);

                    ImageView imgBukti = findViewById(R.id.imgBukti);
                    Glide.with(ProsesOrder.this)
                            .load("" + getIntent().getStringExtra("bukti"))
                            .apply(new RequestOptions().override(0, 200))
                            .into(imgBukti);

//                    Toast.makeText(Toko.this, "data ="+gf, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProsesOrder.this, "t", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Menu> call, Throwable t) {
                String d = t.getMessage();
                Toast.makeText(ProsesOrder.this, "gagal"+d, Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void btnDiproses(View view) {
        Intent intent = getIntent();
        Integer id = intent.getIntExtra("NoPesanan",0);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetDataHistory> call = apiInterface.UpdateHistory1(id);
        call.enqueue(new Callback<GetDataHistory>() {
            @Override
            public void onResponse(Call<GetDataHistory> call, Response<GetDataHistory> response) {
                if (response.isSuccessful()) {
                    Intent nwintent = new Intent(ProsesOrder.this, HistoryPenjual.class);
                    startActivity(nwintent);
                } else {
//                    Toast.makeText(ProsesOrder.this, "Masukkan Gambar Produk", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetDataHistory> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                //Log.d("RETRO", "ON FAILURE : " + t.getCause());
                Intent nwintent = new Intent(ProsesOrder.this, HistoryPenjual.class);
                startActivity(nwintent);
            }
        });
    }

    public void btnSelesai(View view) {
        Intent intent = getIntent();
        Integer id = intent.getIntExtra("NoPesanan",0);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetDataHistory> call = apiInterface.UpdateHistory2(id);
        call.enqueue(new Callback<GetDataHistory>() {
            @Override
            public void onResponse(Call<GetDataHistory> call, Response<GetDataHistory> response) {
                if (response.isSuccessful()) {
                    Intent nwintent = new Intent(ProsesOrder.this, HistoryPenjual.class);
                    startActivity(nwintent);
                } else {
//                    Toast.makeText(ProsesOrder.this, "Masukkan Gambar Produk", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetDataHistory> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                //Log.d("RETRO", "ON FAILURE : " + t.getCause());
                Intent nwintent = new Intent(ProsesOrder.this, HistoryPenjual.class);
                startActivity(nwintent);
            }
        });
    }
}