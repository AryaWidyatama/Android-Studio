package com.suhailahnfsella.fudum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LupaPwPassword extends AppCompatActivity {

    LupaPwEmailModel lupaPwEmailModel1;
    Button btnUbahPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_pw_password);

        getSupportActionBar().hide();

        EditText etPw = findViewById(R.id.etPwBr);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            lupaPwEmailModel1 = (LupaPwEmailModel) intent.getSerializableExtra("data");
        }

        btnUbahPw = findViewById(R.id.btnUbahPw);
        btnUbahPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LupaPwPasswordModel lupaPwPasswordModel1= new LupaPwPasswordModel();
                lupaPwPasswordModel1.setPassword(etPw.getText().toString());

                register(lupaPwPasswordModel1);
            }
        });
    }

    public void register(LupaPwPasswordModel lupaPwPasswordModel){
//        Integer id = lupaPwEmailModel1.getId();
        Intent intent = getIntent();
        if (intent.getExtras() != null) {

//            gmail.setText(getLogin.getEmail());mobil
//            paassword.setText(getLogin.getPassword());
            Integer id = intent.getIntExtra("id", 0);
            Call<GetLupaPwPassword> registerCall = ApiClient.getApi().NewPassword(id, lupaPwPasswordModel);
            registerCall.enqueue(new Callback<GetLupaPwPassword>() {
                @Override
                public void onResponse(Call<GetLupaPwPassword> call, Response<GetLupaPwPassword> response) {
                    if (response.isSuccessful()) {
                        startActivity(new Intent(LupaPwPassword.this, Login.class));
                        finish();

                    } else {
                        String pesan1 = "Harus Diisi!";
                        Toast.makeText(LupaPwPassword.this, pesan1, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GetLupaPwPassword> call, Throwable t) {
                    Toast.makeText(LupaPwPassword.this, "Harus DiIsi", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}