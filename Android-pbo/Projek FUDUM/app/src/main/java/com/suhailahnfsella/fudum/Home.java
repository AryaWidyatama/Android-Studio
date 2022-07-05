package com.suhailahnfsella.fudum;

import static com.suhailahnfsella.fudum.ServerConfig.REQUEST_WRITE_PERMISSION;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    android.widget.SearchView searchView;
    SharedPreferences sharedPreferences,sharedPreferences1;
    RecyclerView recyclerView;
    ApiInterface mApiInterface;
    LoginModel loginre;
    TextView tvNamaAkun;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static Home ma, mi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);
//        BottomNavigationView navView = findViewById(R.id.navigation);
//        navView.setItemIconTintList(null);
            CardView cvJasa = findViewById(R.id.cvJasa);
            cvJasa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Home.this, JasaActivity.class));
                }
            });

            requestPermission();

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("login", "");

            searchView = findViewById(R.id.schome);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            Home.this);

                    // set title dialog
                    alertDialogBuilder.setTitle("Produk Tidak Ditemukan");

                    // set pesan dari dialog
                    alertDialogBuilder
                            .setMessage("Coba dengan kata kunci lain")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    // do something
                                }
                            });

                    // membuat alert dialog dari builder
                    AlertDialog alert = alertDialogBuilder.create();

                    // menampilkan alert dialog
                    alert.show();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText.length() > 0) {
                        Call<GetMenu> kontakCall = mApiInterface.getQuery(newText);
                        kontakCall.enqueue(new Callback<GetMenu>() {
                            @Override
                            public void onResponse(Call<GetMenu> call, Response<GetMenu>
                                    response) {
                                List<Menu> ListMenu = response.body().getData();
                                Collections.sort(ListMenu, new Comparator<Menu>() {
                                    @Override
                                    public int compare(Menu lhs, Menu rhs) {
                                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                                        return lhs.getIdmenu() > rhs.getIdmenu() ? -1 : (lhs.getIdmenu() < rhs.getIdmenu()) ? 1 : 0;
                                    }
                                });
                                Log.d("Retrofit Get", "Jumlah data Kontak: " +
                                        String.valueOf(ListMenu.size()));
                                mAdapter = new AdapterProduk(ListMenu);
                                mRecyclerView.setAdapter(mAdapter);
                            }

                            @Override
                            public void onFailure(Call<GetMenu> call, Throwable t) {
                                Log.e("Retrofit Get", t.toString());
                            }
                        });
                    } else {
                        panggilRetrofit();
                    }
                    return true;
                }
            });

            getSupportActionBar().hide();
            tvNamaAkun = findViewById(R.id.tvNamaAkun);
            if (json != "") {
                loginre = gson.fromJson(json, LoginModel.class);
                tvNamaAkun.setAlpha(1);
                tvNamaAkun.setText(loginre.getEmail());
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
                            Intent nana = new Intent(Home.this, PerkenalanActivity.class);
                            startActivity(nana);
                            return true;
                        case R.id.ic_profil:
                            if (json != "") {
                                loginre = gson.fromJson(json, LoginModel.class);
                                if (loginre.getStatus() == 0) {
                                    Intent mIntent = new Intent(Home.this, AccountV1.class);
                                    startActivity(mIntent);
                                } else if (loginre.getStatus() == 1) {
                                    Intent mIntent = new Intent(Home.this, AccountV2.class);
                                    startActivity(mIntent);
                                } else if (loginre.getStatus() == 2) {
                                    Intent mIntent = new Intent(Home.this, AccountV1.class);
                                    startActivity(mIntent);
                                }
                            } else {
                                Intent mIntent = new Intent(Home.this, Login.class);
                                startActivity(mIntent);
                            }
                            return true;
                    }
                    return false;
                }
            });

            mRecyclerView = (RecyclerView) findViewById(R.id.rcvHome);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mApiInterface = ApiClient.getClient().create(ApiInterface.class);
            ma = this;

            panggilRetrofit();

        }

        private void requestPermission () {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            Toast.makeText(this, "isi sudah di berikan", Toast.LENGTH_SHORT).show();
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);

            } else {
                Toast.makeText(this, "izin belm di berikan", Toast.LENGTH_SHORT).show();
            }
        }


        public void btnMknBrt (View view){
            Integer idkategori = 1;
            Intent intent = new Intent(this, Kategori.class);
            intent.putExtra("IdKategori", idkategori);
            startActivity(intent);
        }

        public void btnMknRgn (View view){
            Integer idkategori = 2;
            Intent intent = new Intent(this, Kategori.class);
            intent.putExtra("IdKategori", idkategori);
            startActivity(intent);
        }

        public void btnMnm (View view){
            Integer idkategori = 3;
            Intent intent = new Intent(this, Kategori.class);
            intent.putExtra("IdKategori", idkategori);
            startActivity(intent);
        }

        public void btnHrbl (View view){
            Integer idkategori = 4;
            Intent intent = new Intent(this, Kategori.class);
            intent.putExtra("IdKategori", idkategori);
            startActivity(intent);
        }

        public void btnLain (View view){
            Integer idkategori = 5;
            Intent intent = new Intent(this, Kategori.class);
            intent.putExtra("IdKategori", idkategori);
            startActivity(intent);
        }

        public void panggilRetrofit () {
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
                            return lhs.getIdmenu() > rhs.getIdmenu() ? -1 : (lhs.getIdmenu() < rhs.getIdmenu()) ? 1 : 0;
                        }
                    });
                    Log.d("Retrofit Get", "Jumlah data Kontak: " +
                            String.valueOf(ListMenu.size()));
                    mAdapter = new AdapterProduk(ListMenu);
                    mRecyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onFailure(Call<GetMenu> call, Throwable t) {
                    Log.e("ini listView", t.toString());
                }
            });
        }

        //public void isiData(){
        //    produkList = new ArrayList<Produk>();
        //    siswaList.add(new Produk())
        //}
}
