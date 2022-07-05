package com.suhailahnfsella.fudum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KonfirmasiUbahStatus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_ubah_status);

        getSupportActionBar().setTitle("Tindak Lanjuti User");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientku));
        }
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

    private boolean appInstalledOrNot(String url){
        PackageManager packageManager = getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e){ ;
            app_installed = false;
        }
        return app_installed;
    }

    public void btnWa(View view) {
        boolean installed = appInstalledOrNot("com.whatsapp");

        Intent intent = getIntent();
        String noWa = intent.getStringExtra("Whatsapp");

            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            intent1.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+62"+noWa));
            startActivity(intent1);
    }

    public void btnSetujui(View view) {
        Intent intent = getIntent();
        Integer id = intent.getIntExtra("IdUser",0);

        Integer sts = 1;

        RequestBody status = RequestBody.create(MediaType.parse("text/plain"), sts.toString());

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetUser> call = apiInterface.updateStatus(id, status);
        call.enqueue(new Callback<GetUser>() {
            @Override
            public void onResponse(Call<GetUser> call, Response<GetUser> response) {
                Intent intent = new Intent(KonfirmasiUbahStatus.this, UbahStatusUser.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<GetUser> call, Throwable t) {
                Intent intent = new Intent(KonfirmasiUbahStatus.this, UbahStatusUser.class);
                startActivity(intent);
            }
        });
    }
}