package com.komputerkit.ukomde_afa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputEmailForgot extends AppCompatActivity {
    EditText username, paassword, gmail;
    private Button button;
    private FirebaseAuth mAuth;
    EditText daftartelp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_input_email_forgot);
        mAuth = FirebaseAuth.getInstance();

        gmail = findViewById(R.id.etLoginEmailForgot);
        button = findViewById(R.id.btnLoginEmailForgot);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginEmail login = new LoginEmail();
                login.setEmail(gmail.getText().toString());


                LoginUser(login);
            }
        });
    }

    public void LoginUser(LoginEmail loginEmail) {
        Call<GetLoginEmail> loginCall = ApiClientLogin.loginRegInterfaace().loginEmail(loginEmail);
        loginCall.enqueue(new Callback<GetLoginEmail>() {
            @Override
            public void onResponse(Call<GetLoginEmail> call, Response<GetLoginEmail> response) {
                if (response.isSuccessful()) {
                    LoginEmail getLogin = response.body().getData();
                    startActivity(new Intent(InputEmailForgot.this, otpLupaPassword.class).putExtra("data", getLogin));

                    finish();


                } else {
                    String pesan1 = "gagal";
                    Toast.makeText(InputEmailForgot.this, pesan1, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetLoginEmail> call, Throwable t) {
                String pesan = "Login Gagal";
                Toast.makeText(InputEmailForgot.this, pesan, Toast.LENGTH_SHORT).show();

            }
        });
    }



    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                                  // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}