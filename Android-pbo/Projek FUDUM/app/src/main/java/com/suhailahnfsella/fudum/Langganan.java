package com.suhailahnfsella.fudum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Langganan extends AppCompatActivity {

    SharedPreferences sharedPreferences,sharedPreferences1;
    LoginModel loginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_langganan);

        getSupportActionBar().setTitle("Mulai Berlangganan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientku));
        }

        Intent intent = getIntent();
        if (intent.getExtras() != null){
            loginModel = (LoginModel) intent.getSerializableExtra("data");
           // loginModel.setIdtoko(loginModel.getId());
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

    public void btnWaFudum(View view) {
        boolean installed = appInstalledOrNot("com.whatsapp");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

        if (json != "") {
            loginModel = gson.fromJson(json, LoginModel.class);
            Integer id = loginModel.getId();
           // String username = loginModel.getUsername();

            Intent intent1 = new Intent(Intent.ACTION_VIEW);
         //   intent1.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+6283102611131"+"&text="+"*PENGAJUAN BUKA TOKO FUDUM*"+"\n\n"+"Id : "+id+"\n"+"Username : "+username));
            startActivity(intent1);
        }

    }
}