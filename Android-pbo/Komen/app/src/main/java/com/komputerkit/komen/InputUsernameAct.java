package com.komputerkit.komen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputUsernameAct extends AppCompatActivity {
    EditText username;
    SiswaModel siswaModel;
    Dialog dialog;
    ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_username);
        username = findViewById(R.id.etUsername);
        getSupportActionBar().hide();
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);

    }

    public void Selanjutnya(View view) {
        progressBar = new ProgressDialog(InputUsernameAct.this);
        progressBar.show();
        progressBar.setContentView(R.layout.progres_dialog);
        progressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        SiswaModel login = new SiswaModel();
        login.setUsername(username.getText().toString());


        LoginUser(login);
    }

    public void LoginUser(SiswaModel loginEmail) {
        Call<GetSiswa> loginCall = ApiClient.getApi().loginEmail(loginEmail);
        loginCall.enqueue(new Callback<GetSiswa>() {
            @Override
            public void onResponse(Call<GetSiswa> call, Response<GetSiswa> response) {
                if (response.isSuccessful()) {
                    SiswaModel getLogin = response.body().getData();
                    startActivity(new Intent(InputUsernameAct.this, InputOtpLupaPw.class).putExtra("data", getLogin));

                    finish();


                } else {
                    String pesan1 = "gagal";
                    Toast.makeText(InputUsernameAct.this, pesan1, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetSiswa> call, Throwable t) {
                progressBar.dismiss();
                dialog=new Dialog(InputUsernameAct.this);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_belum_verif);
                progressBar.dismiss();

                Toast.makeText(InputUsernameAct.this, "berhasil", Toast.LENGTH_SHORT).show();
                Button btnoke=dialog.findViewById(R.id.btnok);
                btnoke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                     dialog.dismiss();

                    }
                });
                dialog.show();

            }
        });
    }
}