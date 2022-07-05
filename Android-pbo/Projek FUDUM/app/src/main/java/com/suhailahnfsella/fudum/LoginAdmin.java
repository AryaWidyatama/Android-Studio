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
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginAdmin extends AppCompatActivity {
    SharedPreferences sharedPreferences,sharedPreferences1;

    Button btnLoginAdm;
    EditText etUsername, etPassword, etkodekhusus;
    LoginModel loginmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        getSupportActionBar().hide();

        btnLoginAdm = findViewById(R.id.btnLanjut2);
        etUsername = findViewById(R.id.etTelpLupaPw);
        etPassword = findViewById(R.id.etPassword);
        etkodekhusus = findViewById(R.id.etkodekhusus);

        btnLoginAdm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etUsername.getText().toString())){
                    etUsername.setError("Harus Diisi!");
                } else if (TextUtils.isEmpty(etPassword.getText().toString())){
                    etPassword.setError("Harus Diisi!");
                } else if (!etkodekhusus.getText().toString().matches("admfudum25")){
                    etkodekhusus.setError("Kode Salah!");
                } else {
                    LoginModel loginModel = new LoginModel();
               //     loginModel.setUsername(etUsername.getText().toString());
                    loginModel.setPassword(etPassword.getText().toString());

                    loginUser(loginModel);
                }
            }
        });
    }

    public void loginUser(LoginModel loginModel){
        Call<GetLogin> loginCall = ApiClient.getApi().loginAdmin(loginModel);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        loginCall.enqueue(new Callback<GetLogin>() {
            @Override
            public void onResponse(Call<GetLogin> call, Response<GetLogin> response) {
                if (response.isSuccessful()){
                    GetLogin getLogin = response.body();
                    loginmodel = getLogin.getData();
                    Gson gson = new Gson();
                    String json = gson.toJson(loginmodel);
                    editor.putString("login",json);
                    editor.commit();
                    startActivity(new Intent(LoginAdmin.this,AdminPage.class));
                    finish();
                }else {
                    Toast.makeText(LoginAdmin.this, "Login Gagal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetLogin> call, Throwable t) {
                String message = "Anda bukan admin";
                Toast.makeText(LoginAdmin.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}