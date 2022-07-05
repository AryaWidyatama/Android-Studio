package com.suhailahnfsella.fudum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LupaPwEmail extends AppCompatActivity {

    RequestQueue queue;
    ApiInterface mApiInterface;
    private FirebaseAuth mAuth;
    private String verificationId;
    private Button verifyOTPBtn, generateOTPBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_pw_email);

        getSupportActionBar().hide();

        Button btnLanjut = findViewById(R.id.etLanjut);
        EditText etTelp = findViewById(R.id.etEmail);

        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LupaPwEmailModel lpTelp = new LupaPwEmailModel();
                lpTelp.setEmail(etTelp.getText().toString());
                if (TextUtils.isEmpty(etTelp.getText().toString())){
                    etTelp.setError("Isi Email yang sudah terdaftar!");
                } else {
                    loginUser(lpTelp);
                }
            }
        });
    }

    public void loginUser(LupaPwEmailModel lupaPwEmailModel) {
        Call<GetLupaPwEmail> loginCall = ApiClient.getApi().loginEmail(lupaPwEmailModel);
        loginCall.enqueue(new Callback<GetLupaPwEmail>() {
            @Override
            public void onResponse(Call<GetLupaPwEmail> call, Response<GetLupaPwEmail> response) {
                if (response.isSuccessful()) {
                    LupaPwEmailModel getLogin = response.body().getData();
                    startActivity(new Intent(LupaPwEmail.this, LupaPwTelp.class).putExtra("data", getLogin));
                    finish();


                } else {
                    String pesan1 = "gagal";
                    Toast.makeText(LupaPwEmail.this, pesan1, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetLupaPwEmail> call, Throwable t) {
                EditText etEmail = findViewById(R.id.etEmail);
                etEmail.setError("Email tidak ditemukan!");
            }
        });
    }

}