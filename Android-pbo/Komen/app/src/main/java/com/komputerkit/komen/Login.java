package com.komputerkit.komen;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    public static Login ma;
    SharedPreferences sharedPreferences,sharedPreferences1;

    ProgressDialog progressBar;
    Dialog dialog;
    TextView tvLupaPw;
    TextView btnDaftarPg;
    LinearLayout btnLogin;
    EditText etUsername, etPassword;
    SiswaModel loginre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getSupportActionBar().hide();
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);


        btnLogin = findViewById(R.id.btnLanjut2);
        btnDaftarPg = findViewById(R.id.btnDaftarPg);
        etUsername = findViewById(R.id.etTelpLupaPw);
        etPassword = findViewById(R.id.etPassword);
        tvLupaPw = findViewById(R.id.tvLupaPw);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etUsername.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())){
                    String pesan = "Username dan Password harus diisi!";
                    Toast.makeText(Login.this, pesan, Toast.LENGTH_LONG).show();
                } else {
                    progressBar = new ProgressDialog(Login.this);
                    progressBar.show();
                    progressBar.setContentView(R.layout.progres_dialog);
                    progressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    SiswaModel loginModel = new SiswaModel();
                    loginModel.setUsername(etUsername.getText().toString());
                    loginModel.setPassword(etPassword.getText().toString());

                    loginUser(loginModel);

                }
            }
        });

        btnDaftarPg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, OpsiDaftar.class);
                startActivity(intent);
            }
        });
//
        tvLupaPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, InputUsernameAct.class);
                startActivity(intent);
            }
        });
    }

    public void loginUser(SiswaModel loginModel){
        Call<GetSiswa> loginCall = ApiClient.getApi().loginUser(loginModel);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        loginCall.enqueue(new Callback<GetSiswa>() {
            @Override
            public void onResponse(Call<GetSiswa> call, Response<GetSiswa> response) {
                if (response.isSuccessful()){
                    GetSiswa getLogin = response.body();
                    loginre = getLogin.getData();
                    Gson gson = new Gson();
                    String json = gson.toJson(loginre);
                    editor.putString("login",json);
                    editor.commit();
                    startActivity(new Intent(Login.this,Home.class));
                    finish();
               }else {
                    Toast.makeText(Login.this, "Login Gagal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetSiswa> call, Throwable t) {
                progressBar.dismiss();
                dialog=new Dialog(Login.this);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_gagal_login);
                progressBar.dismiss();


                Button btnoke=dialog.findViewById(R.id.btnok);
                btnoke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       dialog.dismiss();
                        //  finish();



                    }
                });
                dialog.show();
                String message = "Username atau Password salah!";
                Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void loginAdmin(View view) {
//        Intent intent = new Intent(this, LoginAdmin.class);
//        startActivity(intent);
//    }
}