package com.komputerkit.komen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TerimaAct extends AppCompatActivity {
    public static TerimaAct ma;
    SiswaModel loginModel;
    SharedPreferences sharedPreferences,sharedPreferences1;
    ApiInterface mApiInterface;
    SiswaModel loginre;
    Dialog dialog;
    ProgressDialog progressBar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terima);
        ma=this;

        getSupportActionBar().setTitle("Terima");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(this.getResources().getColor(R.color.limedash));

        progressBar = new ProgressDialog(TerimaAct.this);
        progressBar.show();
        progressBar.setContentView(R.layout.progres_dialog);
        progressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        mRecyclerView = (RecyclerView) findViewById(R.id.rcvUbahStatusUser);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        panggilRetrofit();
        run();
    }

    public void run(){
        ma=this;
        Intent mIntent = getIntent();
        Intent intent = new Intent();

        intent.putExtra("idKategori", mIntent.getIntExtra("idKategori",0));
    }

    public void panggilRetrofit() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

        loginModel = gson.fromJson(json,SiswaModel.class);

        String id = String.valueOf(loginModel.getId());
        Call<GetPesananModel> kontakCall = mApiInterface.getTerima(id);
        kontakCall.enqueue(new Callback<GetPesananModel>() {
            @Override
            public void onResponse(Call<GetPesananModel> call, Response<GetPesananModel>
                    response) {
                progressBar.dismiss();
                List<PesananModel> ListMenu = response.body().getData();

                Log.d("Retrofit Get", "Jumlah data Kontak: " +

                        String.valueOf(ListMenu.size()));
                mAdapter = new AdapterTerima(ListMenu);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetPesananModel> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                progressBar.dismiss();
                dialog=new Dialog(TerimaAct.this);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_home_gagal);
                Button btnoke=dialog.findViewById(R.id.btnok);
                btnoke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressBar.show();
                        dialog.dismiss();
                        panggilRetrofit();



                    }
                });
                dialog.show();
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