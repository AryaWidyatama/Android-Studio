package com.komputerkit.ukomde_afa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ImageButton imageButton1;
    private Button button, btnReg, generateOTPBtn;
    EditText daftaruser, daftarpassword, daftaremail, daftaartelp, edtOTP;
    Dialog dialog;
    ProgressDialog progressBar;
    Button btnverifyCaptcha;
    String SITE_KEY = "6LdVZVIdAAAAAF6jcDa-a3sIHOCeoBjlaF77MBH_";
    String SECRET_KEY = "6LdVZVIdAAAAAGKRbEVh4xWRG4PYRiayfB3x6eze";
    RequestQueue queue;
    private FirebaseAuth mAuth;
    private String verificationId;
    ImageView imgHolder;
    private String mediaPath;
    private String postPath;

    ApiMenuInterface mApiInterface;
    private static final int REQUEST_PICK_PHOTO = 9544;
    private static final int REQUEST_WRITE_PERMISSION = ServerConfig.REQUEST_WRITE_PERMISSION;
    private static final String INSERT_FLAG = ServerConfig.INSERT_FLAG;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            saveImageUpload();
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        queue = Volley.newRequestQueue(getApplicationContext());


        generateOTPBtn = findViewById(R.id.getOtp1);

        imgHolder = (ImageView) findViewById(R.id.imgregsrc);
        daftaruser = findViewById(R.id.tvUsernameDaftar);
        daftarpassword = findViewById(R.id.tvPasswordDaftaar);
        daftaremail = findViewById(R.id.tvEmailDaaftar);
        daftaartelp = findViewById(R.id.tvtelp);

        edtOTP = findViewById(R.id.tvOtp1);

        imgHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("images/*");

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_PICK);


                Intent choose = Intent.createChooser(getIntent, "select");
                choose.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{galleryIntent});
                startActivityForResult(choose, REQUEST_PICK_PHOTO);

            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        generateOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // below line is for checking weather the user
                // has entered his mobile number or not.
                if (TextUtils.isEmpty(daftaartelp.getText().toString())) {
                    // when mobile number text field is empty
                    // displaying a toast message.
                    Toast.makeText(RegisterActivity.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                } else {
                    // if the text field is not empty we are calling our
                    // send OTP method for getting OTP from Firebase.
                    String phone = "+62" + daftaartelp.getText().toString();
                    sendVerificationCode(phone);
                }
            }
        });


        imageButton1 = (ImageButton) findViewById(R.id.imageButton);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backActivity1();
            }
        });


        btnReg = (Button) findViewById(R.id.btnout);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String pass = daftarpassword.getText().toString();
                String email = daftaremail.getText().toString();
                String user = daftaruser.getText().toString();



                if (TextUtils.isEmpty(user) || user.length() < 3 || !daftaruser.getText().toString().matches( "(?=.*[a-z])" +
                        "(?=.*[A-Z])" +
                                "(?=.*[0-9])" +
                        ".{3,}")) {
                    daftaruser.setError("Username harus a-z A-Z 0-9 Min.3 variable");
                }

                else if (TextUtils.isEmpty(pass) || pass.length() < 3 || !daftarpassword.getText().toString().matches( "(?=.*[0-9])" +
                        "(?=.*[a-z])" +
                        "(?=.*[A-Z])" +
                        "(?=.*[a-zA-Z])" +
                        "(?=.*[@#$%^&+=_])" +
                        "(?=\\S+$)" +
                        ".{8,}" +
                        "$")) {
                    daftarpassword.setError("Input Password harus a-z, A-Z, 0-9, symbol @#$%^&+=_, Min.8 variable");
                }else if (TextUtils.isEmpty(email) ) {

                    daftaremail.setError("harus di isi ");

                }else if ( !daftaremail.getText().toString().matches(
                        "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                    daftaremail.setError("harus berupa @gmail.com");
                }

                else{
                            //            Toast.makeText(RegisterActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                    String phone = "+62" + daftaartelp.getText().toString();
                    sendVerificationCode(phone);

                    Toast.makeText(RegisterActivity.this, "Tunggu Sebentar...", Toast.LENGTH_SHORT).show();

                }
                return;

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    // Ambil Image Dari Galeri dan Foto
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    imgHolder.setImageURI(data.getData());
                    cursor.close();

                    postPath = mediaPath;
                }
            }
        }
    }

    private void saveImageUpload() {

     //   final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if (mediaPath == null) {

            RequestBody emailr = RequestBody.create(MediaType.parse("text/plain"), daftaruser.getText().toString().trim());
            RequestBody passwordr = RequestBody.create(MediaType.parse("text/plain"), daftarpassword.getText().toString().trim());
            RequestBody levelr = RequestBody.create(MediaType.parse("text/plain"), daftaremail.getText().toString().trim());
            RequestBody relasir = RequestBody.create(MediaType.parse("text/plain"), daftaartelp.getText().toString().trim());


            LoginRegInterfaace apiInterface = ApiClient.getClient().create(LoginRegInterfaace.class);
            Call<GetRegister> call = apiInterface.regnogambar(emailr, passwordr, levelr, relasir);
            call.enqueue(new Callback<GetRegister>() {
                @Override
                public void onResponse(Call<GetRegister> call, Response<GetRegister> response) {
                    if (response.isSuccessful()) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(RegisterActivity.this, "My Notification");
                        builder.setContentTitle("Register Anda Berhasil!");
                        builder.setContentText("Silahkan Login Menggunakan Akun Yang Telah Anda Buat!");
                        builder.setSmallIcon(R.drawable.ic_baseline_cloud_done_24);
                        builder.setAutoCancel(true);

                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(RegisterActivity.this);
                        managerCompat.notify(3, builder.build());

                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(RegisterActivity.this, "berhasil", Toast.LENGTH_SHORT).show();
//                        PenjualActivity;

                    }

                    // lanjut register tanpa gambar


                }

                @Override
                public void onFailure(Call<GetRegister> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "noooo", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            File imagefile = new File(mediaPath);
            RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
            MultipartBody.Part gambar = MultipartBody.Part.createFormData("gambar", imagefile.getName(), reqBody);

            RequestBody emailr = RequestBody.create(MediaType.parse("text/plain"), daftaruser.getText().toString().trim());
            RequestBody passwordr = RequestBody.create(MediaType.parse("text/plain"), daftarpassword.getText().toString().trim());
            RequestBody levelr = RequestBody.create(MediaType.parse("text/plain"), daftaremail.getText().toString().trim());
            RequestBody relasir = RequestBody.create(MediaType.parse("text/plain"), daftaartelp.getText().toString().trim());


            LoginRegInterfaace apiInterface = ApiClient.getClient().create(LoginRegInterfaace.class);
            Call<GetRegister> call = apiInterface.reggambar(emailr, passwordr, levelr, relasir, gambar);
            call.enqueue(new Callback<GetRegister>() {
                @Override
                public void onResponse(Call<GetRegister> call, Response<GetRegister> response) {
                    if (response.isSuccessful()) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(RegisterActivity.this, "My Notification");
                        builder.setContentTitle("Register Anda Berhasil!");
                        builder.setContentText("Silahkan Login Menggunakan Akun Yang Telah Anda Buat!");
                        builder.setSmallIcon(R.drawable.ic_baseline_cloud_done_24);
                        builder.setAutoCancel(true);

                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(RegisterActivity.this);
                        managerCompat.notify(3, builder.build());

                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(RegisterActivity.this, "berhasil", Toast.LENGTH_SHORT).show();
//                        PenjualActivity;

                    } else {
                        Toast.makeText(RegisterActivity.this, "t", Toast.LENGTH_SHORT).show();
                    }

                    // lanjut register tanpa gambar


                }

                @Override
                public void onFailure(Call<GetRegister> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "noooo", Toast.LENGTH_SHORT).show();
                }
            });


//            postHerosCall.enqueue(new Callback<Heros>() {
//                @Override
//                public void onResponse(Call<Heros> call, Response<Heros> response) {
//                    Toast.makeText(InsertActivity.this, "erhasil", Toast.LENGTH_SHORT).show();
//                    MainActivity.ma.refresh();
//                    finish();
//                }
//
//                @Override
//                public void onFailure(Call<Heros> call, Throwable t) {
//                    Log.d("RETRO", "ON FAILURE : " + t.getMessage());
//                    //Log.d("RETRO", "ON FAILURE : " + t.getCause());
//                    Toast.makeText(getApplicationContext(), "Error, image", Toast.LENGTH_LONG).show();
//                }
//            });
        }
    }




    public void backActivity() {
        Intent intent = new Intent(this, PenjualActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.kanan, R.anim.kanan1);
        finish();


    }

    public void HomeUtama() {
        Toast.makeText(this, "Unggah Data Berhasil!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PenjualActivity.class);
        startActivity(intent);

        finish();

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
                            Toast.makeText(RegisterActivity.this, "Error found is : " + e, Toast.LENGTH_SHORT).show();
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

                                progressBar = new ProgressDialog(RegisterActivity.this);
                                progressBar.show();
                                progressBar.setContentView(R.layout.progres_dialog);
                                progressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                // if the response is successful then we are
                                // showing below toast message.
                                saveImageUpload();
                                RegisterModel registerModel= new RegisterModel();
                                registerModel.setLevel(daftaruser.getText().toString());
                                registerModel.setPassword(daftarpassword.getText().toString());
                                registerModel.setEmail(daftaremail.getText().toString());
                                registerModel.setRelasi(daftaartelp.getText().toString());

                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                Toast.makeText(RegisterActivity.this, "Daftar Berhasil !", Toast.LENGTH_SHORT).show();



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

    public void backActivity1(){

            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
        overridePendingTransition(R.anim.kanan, R.anim.kanan1);
            finish();



    }

    public void Home(){
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void HomeBalik(){
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);


        finish();
    }


    public void Register(RegisterModel registerModel){
        Call<GetRegister> registerCall = ApiClientLogin.loginRegInterfaace().register(registerModel);
        registerCall.enqueue(new Callback<GetRegister>() {
            @Override
            public void onResponse(Call<GetRegister> call, Response<GetRegister> response) {
                if (response.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "bug", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    finish();

                }else{
                    String  pesan1 = "Harus Diisi!";
                    Toast.makeText(RegisterActivity.this, pesan1, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetRegister> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Harus DiIsi", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //kene
    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            verifyGoogleReCAPTCHA();
                            // if the code is correct and the task is successful
                            // we are sending our user to new activity.
//                            Intent i = new Intent(RegisterActivity.this, HomeUtamaActivity.class);

//                            startActivity(i);

                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
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
                edtOTP.setText(code);
                // kode 1 otp

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }


        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {
        // below line is used for getting getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }


}