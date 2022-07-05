package com.komputerkit.ukomde_afa;

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

public class InputNewPassword extends AppCompatActivity {
    EditText username, paassword, gmail;
    private Button button;
    LoginEmail getLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_new_password);

        paassword = findViewById(R.id.etNewPassword);


        button = findViewById(R.id.btnInputNewPassword);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewPPassword registerModel= new NewPPassword();


                registerModel.setPassword(paassword.getText().toString());

                Register(registerModel);


            }
        });
    }

    public void Register(NewPPassword newPPassword){
        Intent intent = getIntent();
        if (intent.getExtras() != null) {

//            gmail.setText(getLogin.getEmail());mobil
//            paassword.setText(getLogin.getPassword());
            Integer id = intent.getIntExtra("id",0);
            Call<GetNewPassword> registerCall = ApiClientLogin.loginRegInterfaace().NewPassword(id,newPPassword);
            registerCall.enqueue(new Callback<GetNewPassword>() {
                @Override
                public void onResponse(Call<GetNewPassword> call, Response<GetNewPassword> response) {
                    if (response.isSuccessful()){
                        startActivity(new Intent(InputNewPassword.this,LoginActivity.class));
                        finish();

                    }else{
                        String  pesan1 = "Harus Diisi!";
                        Toast.makeText(InputNewPassword.this, pesan1, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GetNewPassword> call, Throwable t) {
                    Toast.makeText(InputNewPassword.this, "Harus DiIsi", Toast.LENGTH_SHORT).show();
                }
            });
        }
        Intent mIntent = getIntent();


    }
}