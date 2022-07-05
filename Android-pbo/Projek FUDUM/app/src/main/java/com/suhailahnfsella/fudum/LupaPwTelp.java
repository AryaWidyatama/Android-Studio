package com.suhailahnfsella.fudum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class LupaPwTelp extends AppCompatActivity {

    EditText etOtp3;
    private FirebaseAuth mAuth;
    private String verificationId;//mohon agak menjauh dari kursor laptop
    TextView tvTimer;
    LupaPwEmailModel getLogin;
    Button btnKirimUlangOtp, btnLanjut2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_pw_telp);

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        countdown();

        etOtp3 = findViewById(R.id.etOtp3);
        tvTimer = findViewById(R.id.tvTimer);
        btnKirimUlangOtp = findViewById(R.id.btnKirimUlangOtp);
        btnLanjut2 = findViewById(R.id.btnLanjut2);

        btnLanjut2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode(etOtp3.getText().toString());
            }
        });

        btnKirimUlangOtp.setAlpha(0);
        btnKirimUlangOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTimer();
                countdown();
                btnKirimUlangOtp.setAlpha(0);

                Toast.makeText(LupaPwTelp.this, "cd lagii", Toast.LENGTH_SHORT).show();

                Intent intent = getIntent();
                if (intent.getExtras() != null) {
                    getLogin = (LupaPwEmailModel) intent.getSerializableExtra("data");
                    String ha = getLogin.getTelp();
                    String phone = "+62" + ha.trim();

                    sendVerificationCode(phone);

                }
            }
        });

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            getLogin = (LupaPwEmailModel) intent.getSerializableExtra("data");
            String ha = getLogin.getTelp();
            String phone = "+62" + ha.trim();

            sendVerificationCode(phone);

        }

    }

    public void countdown(){

        CountDownTimer haha = null;
        long duration = TimeUnit.MINUTES.toMillis(1);
        haha  =   new CountDownTimer(duration,1000){
            @Override
            public void onTick(long l){
                String sDuration = String.format(Locale.ENGLISH,"%02d : %02d"
                        ,TimeUnit.MILLISECONDS.toMinutes(l)
                        ,TimeUnit.MILLISECONDS.toSeconds(l) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
                tvTimer.setText(sDuration);
            }
            @Override
            public void onFinish(){



                btnKirimUlangOtp.setAlpha(1);
                Toast.makeText(LupaPwTelp.this, "Habis", Toast.LENGTH_SHORT).show();
            }
        };haha.start();
    }


    public void cancelTimer() {
        CountDownTimer haha = null;
        if(haha!=null)
            haha.cancel();
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LupaPwTelp.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
                            if (intent.getExtras() != null) {
                                getLogin = (LupaPwEmailModel) intent.getSerializableExtra("data");
                                Integer hu = getLogin.getId();
                                Toast.makeText(LupaPwTelp.this, "="+hu, Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(LupaPwTelp.this,LupaPwPassword.class);
                                intent1.putExtra("id",hu);
                                startActivity(intent1);

                            }

                        } else {

                            Toast.makeText(LupaPwTelp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String number) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBack)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            final String code = phoneAuthCredential.getSmsCode();

            if (code != null) {

                etOtp3.setText(code);

                verifyCode(code);
            }
        }


        @Override
        public void onVerificationFailed(FirebaseException e) {

            Toast.makeText(LupaPwTelp.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        signInWithCredential(credential);
    }
}