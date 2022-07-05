package com.suhailahnfsella.fudum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Daftar extends AppCompatActivity {
    List<DaftarModel> mDaftarModel;

    LinearLayout btnDaftar, btnOtp;
    String SITE_KEY = "6LfCr1IdAAAAAHlgeFbESLXCwYragYsI_H8INd9u";
    String SECRET_KEY = "6LfCr1IdAAAAALmRA1Nh0FFGnPMq9ve5N1yPgtqH";
    RequestQueue queue;
    EditText etNamaLkp, etUsernameBr, etPasswordBr, etKonfirmPw, etEmail, etTelp, etOtp;
    ApiInterface mApiInterface;
    private FirebaseAuth mAuth;
    private String verificationId;
    private Button verifyOTPBtn;
    private LinearLayout generateOTPBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        btnDaftar = findViewById(R.id.btnHapus);
        btnOtp = findViewById(R.id.btnOtp);
        etNamaLkp = findViewById(R.id.etNamaLkp);
        etUsernameBr = findViewById(R.id.etUsernameBr);
        etEmail = findViewById(R.id.etTelpLupaPw);
        etTelp = findViewById(R.id.etTelp);
        etOtp = findViewById(R.id.etOtp);
        etPasswordBr = findViewById(R.id.etPasswordBr);
        etKonfirmPw = findViewById(R.id.etKonfirmPw);
        generateOTPBtn = findViewById(R.id.btnOtp);

        String pw = etPasswordBr.getText().toString();

        queue = Volley.newRequestQueue(getApplicationContext());
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!etNamaLkp.getText().toString().matches("^[a-z,A-Z\\s]+$")){
                    etNamaLkp.setError("Masukkan nama dengan benar!");
                } else if (TextUtils.isEmpty(etUsernameBr.getText().toString()) || etUsernameBr.length() > 15 ){
                    etUsernameBr.setError("Isi nama pengguna maksimal 15 Karakter atau coba nama pengguna yang lain!");
                } else if (etEmail.length() == 0){
                    Toast.makeText(Daftar.this, "Email harus diisi!", Toast.LENGTH_LONG).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()){
                    etEmail.setError("Masukkan Email dengan benar!");
                } else if (pw.length() < 8 && !etPasswordBr.getText().toString().matches("^.*(?=.*[a-zA-Z])(?=.*[0-9]).*$")){
                    etPasswordBr.setError("Password minimal 8 karakter (huruf & angka)");
                } else if (TextUtils.isEmpty(etKonfirmPw.getText().toString())){
                    etKonfirmPw.setError("Isi konfirmasi password!");
                } else if (!etKonfirmPw.getText().toString().equals(etPasswordBr.getText().toString())){
                    etKonfirmPw.setError("Tidak sama dengan password");
                } else if (TextUtils.isEmpty(etTelp.getText().toString()) || etTelp.length() < 11){
                    etTelp.setError("Masukkan Nomor Telepon yang bisa dihubungi!");
                } else if (TextUtils.isEmpty(etOtp.getText().toString())) {
                    Toast.makeText(Daftar.this, "Masukkan Kode OTP!", Toast.LENGTH_SHORT).show();
                } else
                {
                    verifyCode(etOtp.getText().toString());
                    verifyGoogleReCAPTCHA();

                }
            }
        });

        generateOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // below line is for checking weather the user
                // has entered his mobile number or not.
                if (TextUtils.isEmpty(etTelp.getText().toString()) && etTelp.length() < 11) {
                    // when mobile number text field is empty
                    // displaying a toast message.
                    Toast.makeText(Daftar.this, "Masukkan Nomor dulu!", Toast.LENGTH_SHORT).show();
                } else {
                    // if the text field is not empty we are calling our
                    // send OTP method for getting OTP from Firebase.
                    String phone = "+62" + etTelp.getText().toString();
                    sendVerificationCode(phone);
                }
            }
        });
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // if the code is correct and the task is successful
                            // we are sending our user to new activity.
//                            Intent i = new Intent(Daftar.this, Login.class);
//                            startActivity(i);
//                            finish();
                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(Daftar.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
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
                        .setCallbacks(mCallBack)           // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // callback method is called on Phone auth provider.
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationId = s;
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                etOtp.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(Daftar.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        // below line is used for getting getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }

    private void verifyGoogleReCAPTCHA() {

        // below line is use for getting our safety
        // net client and verify with reCAPTCHA
        SafetyNet.getClient(this).verifyWithRecaptcha(SITE_KEY)
                // after getting our client we have
                // to add on success listener.
                .addOnSuccessListener(this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                    @Override
                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                        // in below line we are checking the response token.
                        if (!response.getTokenResult().isEmpty()) {
                            // if the response token is not empty then we
                            // are calling our verification method.
                            handleVerification(response.getTokenResult());
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // this method is called when we get any error.
                        if (e instanceof ApiException) {
                            ApiException apiException = (ApiException) e;
                            // below line is use to display an error message which we get.
                            Log.d("TAG", "Error message: " +
                                    CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));
                        } else {
                            // below line is use to display a toast message for any error.
                            Toast.makeText(Daftar.this, "Error found is : " + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    protected void handleVerification(final String responseToken) {
        // inside handle verification method we are
        // verifying our user with response token.
        // url to sen our site key and secret key
        // to below url using POST method.
        String url = "https://www.google.com/recaptcha/api/siteverify";

        // in this we are making a string request and
        // using a post method to pass the data.
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // inside on response method we are checking if the
                        // response is successful or not.
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("success")) {
                                // if the response is successful then we are
                                // showing below toast message.
                                DaftarModel daftarModel = new DaftarModel();
                                daftarModel.setUsername(etUsernameBr.getText().toString());
                                daftarModel.setEmail(etEmail.getText().toString());
                                daftarModel.setTelp(etTelp.getText().toString());
                                daftarModel.setNamapanjang(etNamaLkp.getText().toString());
                                daftarModel.setPassword(etPasswordBr.getText().toString());

                                daftarUsers(daftarModel);
                                Toast.makeText(Daftar.this, "User verified with reCAPTCHA", Toast.LENGTH_SHORT).show();
                            } else {
                                // if the response if failure we are displaying
                                // a below toast message.
                                Toast.makeText(getApplicationContext(), String.valueOf(jsonObject.getString("error-codes")), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception ex) {
                            // if we get any exception then we are
                            // displaying an error message in logcat.
                            Log.d("TAG", "JSON exception: " + ex.getMessage());
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // inside error response we are displaying
                        // a log message in our logcat.
                        Log.d("TAG", "Error message: " + error.getMessage());
                    }
                }) {
            // below is the getParamns method in which we will
            // be passing our response token and secret key to the above url.
            @Override
            protected Map<String, String> getParams() {
                // we are passing data using hashmap
                // key and value pair.
                Map<String, String> params = new HashMap<>();
                params.put("secret", SECRET_KEY);
                params.put("response", responseToken);
                return params;
            }
        };
        // below line of code is use to set retry
        // policy if the api fails in one try.
        request.setRetryPolicy(new DefaultRetryPolicy(
                // we are setting time for retry is 5 seconds.
                50000,

                // below line is to perform maximum retries.
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // at last we are adding our request to queue.
        queue.add(request);
    }

    public void daftarUsers(DaftarModel daftarModel){
        Call<GetDaftar> getDaftarCall = ApiClient.getApi().daftarUser(daftarModel);
        getDaftarCall.enqueue(new Callback<GetDaftar>() {
            @Override
            public void onResponse(Call<GetDaftar> call, Response<GetDaftar> response) {
                if (response.isSuccessful()){
                    Intent intent = new Intent(Daftar.this,Login.class);
                    startActivity(intent);
                    finish();
                }else {
                    etUsernameBr = findViewById(R.id.etUsernameBr);
                    etEmail = findViewById(R.id.etTelpLupaPw);
                    etTelp = findViewById(R.id.etTelp);
                    etUsernameBr.setError("Gunakan Nama Pengguna yang belum terdaftar sebelumnya!");
                    etEmail.setError("Gunakan Email yang belum terdaftar sebelumnya!");
                    etTelp.setError("Gunakan No Telp yang belum terdaftar sebelumnya!");
                }
            }

            @Override
            public void onFailure(Call<GetDaftar> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(Daftar.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}