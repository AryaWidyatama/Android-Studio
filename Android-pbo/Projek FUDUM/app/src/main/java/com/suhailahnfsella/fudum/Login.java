package com.suhailahnfsella.fudum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    public static Login ma;
    SharedPreferences sharedPreferences,sharedPreferences1;

    TextView tvLupaPw;
    TextView btnDaftarPg;
    LinearLayout btnLogin;
    EditText etUsername, etPassword;
    LoginModel loginre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

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
                    LoginModel loginModel = new LoginModel();
                    loginModel.setEmail(etUsername.getText().toString());
                    loginModel.setPassword(etPassword.getText().toString());

                    loginUser(loginModel);

                }
            }
        });

        btnDaftarPg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Daftar.class);
                startActivity(intent);
            }
        });

        tvLupaPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, LupaPwEmail.class);
                startActivity(intent);
            }
        });
    }

    public void loginUser(LoginModel loginModel){
        Call<GetLogin> loginCall = ApiClient.getApi().loginUser(loginModel);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        loginCall.enqueue(new Callback<GetLogin>() {
            @Override
            public void onResponse(Call<GetLogin> call, Response<GetLogin> response) {
                if (response.isSuccessful()){
                    GetLogin getLogin = response.body();
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
            public void onFailure(Call<GetLogin> call, Throwable t) {
                String message = "Username atau Password salah!";
                Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loginAdmin(View view) {
        Intent intent = new Intent(this, LoginAdmin.class);
        startActivity(intent);
    }
}