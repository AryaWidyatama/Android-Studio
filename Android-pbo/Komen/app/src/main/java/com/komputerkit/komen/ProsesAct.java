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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProsesAct extends AppCompatActivity {
    public static ProsesAct ma;
    SiswaModel loginModel;
    SharedPreferences sharedPreferences,sharedPreferences1;
    ApiInterface mApiInterface;
    SiswaModel loginre;
    Dialog dialog;
    ProgressDialog progressBar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proses);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");
        ma=this;

        getSupportActionBar().setTitle("Proses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(this.getResources().getColor(R.color.limedash));


        progressBar = new ProgressDialog(ProsesAct.this);
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

    // ubah status menjadi proses,terima.selesai
    // atur adapter equals di setiap adapter
    // progressbarr
    // refresh barr

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
        Call<GetPesananModel> kontakCall = mApiInterface.getProsesuser(id);
        kontakCall.enqueue(new Callback<GetPesananModel>() {
            @Override
            public void onResponse(Call<GetPesananModel> call, Response<GetPesananModel>
                    response) {
                progressBar.dismiss();
                List<PesananModel> ListMenu = response.body().getData();

                Log.d("Retrofit Get", "Jumlah data Kontak: " +
                        String.valueOf(ListMenu.size()));
                mAdapter = new AdapterProses(ListMenu);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetPesananModel> call, Throwable t) {
                progressBar.dismiss();
                dialog=new Dialog(ProsesAct.this);
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