package com.komputerkit.ukomde_afa;

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

public class otpLupaPassword extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String verificationId;
    EditText daftartelp;
    LoginEmail getLogin;
    Button btne;
    TextView re,re2,re3;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_otp_lupa_password);

        mAuth = FirebaseAuth.getInstance();
        daftartelp = findViewById(R.id.otpEmailFRgt);
        re2 = findViewById(R.id.countdown);
        re3 = findViewById(R.id.cobalagi);



         countdown();

        btne = findViewById(R.id.btnOtpEmailfrgt);
        btne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCode(daftartelp.getText().toString());
            }
        });

        re = findViewById(R.id.resendcode);
        re.setAlpha(0);
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelTimer();
                countdown();
                re.setAlpha(0);

                Toast.makeText(otpLupaPassword.this, "cd lagii", Toast.LENGTH_SHORT).show();

                Intent intent = getIntent();
                if (intent.getExtras() != null) {
                    getLogin = (LoginEmail) intent.getSerializableExtra("data");
                    String ha = getLogin.getRelasi();
                    String phone = "+62" + ha.trim();

                    sendVerificationCode(phone);

                }
            }
        });

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            getLogin = (LoginEmail) intent.getSerializableExtra("data");
            String ha = getLogin.getRelasi();
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
                re2.setText(sDuration);
            }
            @Override
            public void onFinish(){
                dialog=new Dialog(otpLupaPassword.this);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_otp);


                Button btnoke=dialog.findViewById(R.id.btniya);
                btnoke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
                re.setAlpha(1);
                Toast.makeText(otpLupaPassword.this, "Habis", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(otpLupaPassword.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
                            if (intent.getExtras() != null) {
                                getLogin = (LoginEmail) intent.getSerializableExtra("data");
                                Integer hu = getLogin.getId();
                                Toast.makeText(otpLupaPassword.this, "="+hu, Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(otpLupaPassword.this,InputNewPassword.class);
                                intent1.putExtra("id",hu);
                                startActivity(intent1);

                            }

                        } else {

                            Toast.makeText(otpLupaPassword.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
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

                daftartelp.setText(code);

                verifyCode(code);
            }
        }


        @Override
        public void onVerificationFailed(FirebaseException e) {

            Toast.makeText(otpLupaPassword.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        signInWithCredential(credential);
    }
}