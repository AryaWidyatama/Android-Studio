package com.komputerkit.komen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class InputOtpLupaPw extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String verificationId;
    EditText daftartelp;
    SiswaModel getLogin;
    Button btne;
    LinearLayout re;
    TextView re2,re3;
    Dialog dialog;

    ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_otp_lupa_pw);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();

        daftartelp = findViewById(R.id.etUsername);
        re2 = findViewById(R.id.countdown);
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);





        countdown();



        re = findViewById(R.id.btnOtp);
        re.setAlpha(1);
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelTimer();
                countdown();
                re.setAlpha(1);



                Intent intent = getIntent();
                if (intent.getExtras() != null) {
                    getLogin = (SiswaModel) intent.getSerializableExtra("data");
                    String ha = getLogin.getNoTelp();
                    String phone = "+62" + ha.trim();

                    sendVerificationCode(phone);

                }
            }
        });

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            getLogin = (SiswaModel) intent.getSerializableExtra("data");
            String ha = getLogin.getNoTelp();
            String phone = "+62" + ha.trim();
            sendVerificationCode(phone);
        }
    }

    public void UbahPw(View view) {

        verifyCode(daftartelp.getText().toString());
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
                dialog=new Dialog(InputOtpLupaPw.this);
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

                            Intent intent = getIntent();
                            if (intent.getExtras() != null) {
                                getLogin = (SiswaModel) intent.getSerializableExtra("data");
                                Integer hu = getLogin.getId();

                                Intent intent1 = new Intent(InputOtpLupaPw.this,InputNewPWAct.class);
                                intent1.putExtra("id",hu);
                                startActivity(intent1);

                            }

                        } else {

                            Toast.makeText(InputOtpLupaPw.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
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

            Toast.makeText(InputOtpLupaPw.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        signInWithCredential(credential);
    }
}