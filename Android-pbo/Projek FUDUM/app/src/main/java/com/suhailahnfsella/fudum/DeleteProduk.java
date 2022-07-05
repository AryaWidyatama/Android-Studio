package com.suhailahnfsella.fudum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteProduk extends AppCompatActivity {

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
        setContentView(R.layout.activity_delete_produk);

        getSupportActionBar().hide();
    }


    public void btnHapus(View view) {
        Intent intent = getIntent();
        Integer id = intent.getIntExtra("IdMenu",0);
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<GetMenu> del = api.DeleteProduk(id);
        del.enqueue(new Callback<GetMenu>() {
            @Override
            public void onResponse(Call<GetMenu> call, Response<GetMenu> response) {
                Log.d("Retro", "onResponse"+call);

//                        Toast.makeText(DetailHapusBarang.this, "Succes", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(DetailHapusBarang.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DeleteProduk.this);
                Gson gson = new Gson();
                String json = sharedPreferences.getString("login","");
                if (json != ""){
                    Intent intent = new Intent(DeleteProduk.this,Toko.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<GetMenu> call, Throwable t) {
//                        pd.hide();
                Log.d("Retro", "onFailure"+t.getLocalizedMessage());

                Toast.makeText(DeleteProduk.this, "tes :"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}