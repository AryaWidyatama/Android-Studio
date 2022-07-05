package com.komputerkit.komen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputNewPWAct extends AppCompatActivity {
    EditText pw;
    Dialog dialog;

    ProgressDialog progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_new_pwact);
        pw = findViewById(R.id.etUbahSandi);
        getSupportActionBar().hide();
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);

    }

    public void Selanjutnya(View view) {
        progressBar = new ProgressDialog(InputNewPWAct.this);
        progressBar.show();
        progressBar.setContentView(R.layout.progres_dialog);
        progressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        NewPw registerModel= new NewPw();


        registerModel.setPassword(pw.getText().toString());

        Register(registerModel);

    }

    public void Register(NewPw newPPassword){
        Intent intent = getIntent();
        if (intent.getExtras() != null) {


            Integer id = intent.getIntExtra("id",0);
            Call<GetNewPw> registerCall = ApiClient.getApi().NewPassword(id,newPPassword);
            registerCall.enqueue(new Callback<GetNewPw>() {
                @Override
                public void onResponse(Call<GetNewPw> call, Response<GetNewPw> response) {
                    if (response.isSuccessful()){
                        startActivity(new Intent(InputNewPWAct.this,Login.class));
                        finish();

                    }else{
                        String  pesan1 = "Harus Diisi!";
                        Toast.makeText(InputNewPWAct.this, pesan1, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GetNewPw> call, Throwable t) {
                    Toast.makeText(InputNewPWAct.this, "Harus DiIsi", Toast.LENGTH_SHORT).show();
                }
            });
        }
        Intent mIntent = getIntent();


    }
}