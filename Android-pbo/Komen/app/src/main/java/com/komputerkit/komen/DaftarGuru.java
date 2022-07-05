package com.komputerkit.komen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarGuru extends AppCompatActivity {
    LinearLayout btnDaftar, btnOtp;
    String SITE_KEY = "6LdwRzMgAAAAAPRLm-QXmcw00y8KD3dyIkcro9oq";
    String SECRET_KEY = "6LdwRzMgAAAAAPWI3ZKL7Taxmm7IVzibtjRUHbuq";
    RequestQueue queue;
    EditText etNamaLkp, etUsernameBr, etPasswordBr, etKonfirmPw, etEmail, etTelp, etOtp,emailbr;
    ApiInterface mApiInterface;
    //private FirebaseAuth mAuth;
    AppCompatSpinner spinnerkelas,spinnerjurusan;
    private String verificationId;
    private Button verifyOTPBtn;
    Dialog dialog;
    private LinearLayout generateOTPBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_guru);



        getSupportActionBar().hide();
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);


        //  mAuth = FirebaseAuth.getInstance();

        btnDaftar = findViewById(R.id.btnHapus);
        // btnOtp = findViewById(R.id.btnOtp);
        etNamaLkp = findViewById(R.id.etNamaLkp);
        etUsernameBr = findViewById(R.id.etUsernameBr);
        etEmail = findViewById(R.id.etTelpLupaPw);
        etTelp = findViewById(R.id.etTelp);
        emailbr = findViewById(R.id.etEmailBr);
        // etOtp = findViewById(R.id.etOtp);
        etPasswordBr = findViewById(R.id.etPasswordBr);
        etKonfirmPw = findViewById(R.id.etKonfirmPw);
        // generateOTPBtn = findViewById(R.id.btnOtp);

        String pw = etPasswordBr.getText().toString();

        queue = Volley.newRequestQueue(getApplicationContext());
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (TextUtils.isEmpty(etNamaLkp.getText().toString()) || !etNamaLkp.getText().toString().matches("^[a-zA-Z_ ]*$")){
                    etNamaLkp.setError("Masukkan nama dengan benar!");
                } else if (TextUtils.isEmpty(etUsernameBr.getText().toString()) || etUsernameBr.length() > 15 ){
                    etUsernameBr.setError("Isi nama pengguna maksimal 15 Karakter atau coba nama pengguna yang lain!");
                }  else if (etPasswordBr.length() < 8 && !etPasswordBr.getText().toString().matches("(?=.*[0-9])" +
                        "(?=.*[a-z])" +
                        "(?=.*[A-Z])" +
                        "(?=.*[a-zA-Z])" +
                        "(?=\\S+$)" +
                        ".{8,}" +
                        "$")){
                    etPasswordBr.setError("Password minimal 8 karakter, ( huruf ( Kapital&normal ) & angka ");
                } else if (TextUtils.isEmpty(etKonfirmPw.getText().toString())){
                    etKonfirmPw.setError("Isi konfirmasi password!");
                } else if (!etKonfirmPw.getText().toString().equals(etPasswordBr.getText().toString())){
                    etKonfirmPw.setError("Tidak sama dengan password");
                } else if (TextUtils.isEmpty(etTelp.getText().toString()) || etTelp.length() < 11){
                    etTelp.setError("Masukkan Nomor Telepon yang bisa dihubungi!");
                }else if ( !emailbr.getText().toString().matches(
                        "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                    emailbr.setError("harus berupa @gmail.com");
                } else
                {
                    //     verifyCode(etOtp.getText().toString());
                    //  verifyGoogleReCAPTCHA();
//                    SiswaModel daftarModel = new SiswaModel();

                    AkunAwal daftarModel = new AkunAwal();

                    daftarModel.setUsername(etUsernameBr.getText().toString());

                    daftarModel.setEmail(emailbr.getText().toString());

                    daftarModel.setNamalengkap(etNamaLkp.getText().toString());


                    daftarUsers(daftarModel);



                }
            }
        });

    }

    public void daftarUsers(AkunAwal daftarModel){
        Call<GetAkunAwal> getDaftarCall = ApiClient.getApi().akunawal(daftarModel);
        getDaftarCall.enqueue(new Callback<GetAkunAwal>() {
            @Override
            public void onResponse(Call<GetAkunAwal> call, Response<GetAkunAwal> response) {
                if (response.isSuccessful()){

                    Intent intent = new Intent(DaftarGuru.this,DaftarLanjutanGuru.class);
                    intent.putExtra("NoTelp",etTelp.getText().toString());

                    intent.putExtra("Username",etUsernameBr.getText().toString());

                    intent.putExtra("Kelas","Guru");
                    intent.putExtra("Jurusan","Guru");
                    intent.putExtra("Email",emailbr.getText().toString());

                    intent.putExtra("NamaLengkap",etNamaLkp.getText().toString());
                    intent.putExtra("Password",etPasswordBr.getText().toString());
                    startActivity(intent);

                }else {
                    dialog=new Dialog(DaftarGuru.this);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                            WindowManager.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setContentView(R.layout.dialog_gagal_daftar);



                    Button btnoke=dialog.findViewById(R.id.btnok);
                    btnoke.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();



                        }
                    });
                    dialog.show();
                }
            }

            @Override
            public void onFailure(Call<GetAkunAwal> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(DaftarGuru.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }


    public void Masuk(View view) {
        Intent intent = new Intent(DaftarGuru.this, Login.class);
        startActivity(intent);
    }
}