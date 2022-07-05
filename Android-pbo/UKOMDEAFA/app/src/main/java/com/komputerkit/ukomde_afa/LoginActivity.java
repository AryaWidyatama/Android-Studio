package com.komputerkit.ukomde_afa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ImageButton imageButton;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+";
    EditText username, paassword, gmail;
    private Button button;
    TextView btnforgo;
    private FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;
    Login loginre;
    Dialog dialog;

    private static final String SHARED_PREF_NAME = "";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private String verificationId;
    ProgressDialog progressBar;
    private EditText edtPhone, edtOTP;
    private Button verifyOTPBtn, generateOTPBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        edtPhone = findViewById(R.id.etTelp);
        edtOTP = findViewById(R.id.etOtp);
        generateOTPBtn = findViewById(R.id.getOtp);


        username = findViewById(R.id.tvUsername);
        paassword = findViewById(R.id.tvPassword);


        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backActivity();
            }
        });

        btnforgo = findViewById(R.id.btnforgot);
        btnforgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(LoginActivity.this,InputEmailForgot.class);
            startActivity(intent);
            }
        });




        verifyOTPBtn = (Button) findViewById(R.id.button4);
        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailToText = username.getText().toString();



//                editor.putString(KEY_EMAIL,username.getText().toString());
//                editor.apply();


                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(paassword.getText().toString())) {
                    String pesan = "All";
                    Toast.makeText(LoginActivity.this, pesan, Toast.LENGTH_SHORT).show();
                } else {
                    Login login = new Login();
                    login.setEmail(username.getText().toString());
                    login.setPassword(paassword.getText().toString());
//                    login.setAlamat(paassword.getText().toString());

                    progressBar = new ProgressDialog(LoginActivity.this);
                    progressBar.show();
                    progressBar.setContentView(R.layout.progres_dialog);
                    progressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


                  LoginUser(login);


                }
//                HomeUtama();
            }
        });


    }

    public void backActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.kanan, R.anim.kanan1);
        finish();


    }

    public void HomeUtama() {
        Intent intent = new Intent(this, HomeUtamaActivity.class);
        startActivity(intent);

        finish();

    }






    public void LoginUser(Login login) {
        Call<GetLogin> loginCall = ApiClientLogin.loginRegInterfaace().loginReg(login);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        loginCall.enqueue(new Callback<GetLogin>() {
            @Override
            public void onResponse(Call<GetLogin> call, Response<GetLogin> response) {
                if (response.isSuccessful()) {

                    GetLogin getLogin = response.body();
                    loginre = getLogin.getData();
                    Gson gson = new Gson();
                    String json = gson.toJson(loginre);
                    editor.putString("login",json);
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this,HomeUtamaActivity.class);
                    startActivity(intent);
                    finish();


                } else {
                    String pesan1 = "gagal";
                    Toast.makeText(LoginActivity.this, pesan1, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetLogin> call, Throwable t) {
                dialog=new Dialog(LoginActivity.this);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_login);


                Button btnoke=dialog.findViewById(R.id.btniya);
                btnoke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        progressBar.dismiss();
                    }
                });

                dialog.show();
                String pesan = "Login Gagal";
                Toast.makeText(LoginActivity.this, pesan, Toast.LENGTH_SHORT).show();

            }
        });
    }

    }

