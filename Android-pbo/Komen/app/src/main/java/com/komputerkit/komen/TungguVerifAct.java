package com.komputerkit.komen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;

public class TungguVerifAct extends AppCompatActivity {
    SharedPreferences sharedPreferences,sharedPreferences1;
    SiswaModel loginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tunggu_verif);
        getSupportActionBar().setTitle("Verifikasi Akun");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(this.getResources().getColor(R.color.limedash));


    }

    public void tombol(View view) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");
        loginModel = gson.fromJson(json,SiswaModel.class);
        String nama = loginModel.getUsername();

        Intent intent1 = new Intent(Intent.ACTION_VIEW);
        intent1.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+62"+"83829133834"+"&text="+"Apakah data saya sudah di verifikasi?"+"\n"+"Username saya = "+nama));
        startActivity(intent1);
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