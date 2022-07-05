package com.komputerkit.teskerja;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText username,password;
    SiswaModel loginre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.etusername);
        password = findViewById(R.id.etkode);

    }

    public void Masuk(View view) {
        username = findViewById(R.id.etusername);
        password = findViewById(R.id.etkode);

        SiswaModel loginModel = new SiswaModel();
        loginModel.setUsername(username.getText().toString());
        loginModel.setPassword(password.getText().toString());

        loginUser(loginModel);
    }

    public void loginUser(SiswaModel loginModel){
        Call<GetSiswa> loginCall = ApiClient.getApi().loginUser(loginModel);

        loginCall.enqueue(new Callback<GetSiswa>() {
            @Override
            public void onResponse(Call<GetSiswa> call, Response<GetSiswa> response) {
                if (response.isSuccessful()){

                    Intent intent = new Intent(MainActivity.this,HomeUtama.class);

                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(MainActivity.this, "Login Gagal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetSiswa> call, Throwable t) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Gagal Login")
                        .setMessage("Invalid Credentials")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                              dialog.dismiss();
                            }
                        })

                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                String message = "Cek jaringan anda";
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}