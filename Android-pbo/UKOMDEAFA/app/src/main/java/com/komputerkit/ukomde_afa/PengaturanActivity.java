package com.komputerkit.ukomde_afa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengaturanActivity extends AppCompatActivity {
    private ImageButton imageButton1;
    private Button button, btnReg, generateOTPBtn;
    EditText daftaruser, daftarpassword, daftaremail, daftaartelp, edtOTP;
    Login login;
    SharedPreferences sharedPreferences,sharedPreferences1;

    ImageView imgHolder;
    private String mediaPath;
    private String postPath;

    ApiMenuInterface mApiInterface;
    private static final int REQUEST_PICK_PHOTO = 9544;
    private static final int REQUEST_WRITE_PERMISSION = ServerConfig.REQUEST_WRITE_PERMISSION;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            saveImageUpload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);
        getSupportActionBar().hide();


        imgHolder = (ImageView) findViewById(R.id.imgcrd);
        daftaruser = findViewById(R.id.tvUsernameDaftar);
        daftarpassword = findViewById(R.id.tvPasswordDaftaar);
        daftaremail = findViewById(R.id.etEmail);
        daftaartelp = findViewById(R.id.tvtelp);

        edtOTP = findViewById(R.id.etAlamat);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

        if (json != ""){
            login = gson.fromJson(json,Login.class);
            daftaruser.setText(login.getLevel());
            daftarpassword.setText(login.getPassword());
            daftaartelp.setText(login.getRelasi());
            daftaremail.setText(login.getEmail());
            Glide.with(PengaturanActivity.this)
                    .load("" + login.getGambar())
                    .apply(new RequestOptions().override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))


                    .into(imgHolder);

        }

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

        imageButton1 = (ImageButton) findViewById(R.id.imageButton);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backActivity1();

            }
        });


        btnReg = (Button) findViewById(R.id.fd);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveImageUpload();

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
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("login","");
            if (json != "") {
                login = gson.fromJson(json, Login.class);
                String namatoko1 = login.getLevel();
                Intent mIntent = getIntent();
                Integer id = login.getId();
                RequestBody emailr = RequestBody.create(MediaType.parse("text/plain"), daftaruser.getText().toString().trim());
                RequestBody passwordr = RequestBody.create(MediaType.parse("text/plain"), daftarpassword.getText().toString().trim());
                RequestBody levelr = RequestBody.create(MediaType.parse("text/plain"), daftaremail.getText().toString().trim());
                RequestBody relasir = RequestBody.create(MediaType.parse("text/plain"), daftaartelp.getText().toString().trim());


                LoginRegInterfaace apiInterface = ApiClient.getClient().create(LoginRegInterfaace.class);
                Call<GetRegister> call = apiInterface.ubahregnogambar(id, emailr, passwordr, levelr, relasir);
                call.enqueue(new Callback<GetRegister>() {
                    @Override
                    public void onResponse(Call<GetRegister> call, Response<GetRegister> response) {
                        if (response.isSuccessful()) {


                            Intent intent = new Intent(PengaturanActivity.this, LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(PengaturanActivity.this, "berhasil", Toast.LENGTH_SHORT).show();
//                        PenjualActivity;

                        }



                    }

                    @Override
                    public void onFailure(Call<GetRegister> call, Throwable t) {
                        Toast.makeText(PengaturanActivity.this, "noooo", Toast.LENGTH_SHORT).show();
                    }
                });
            }


        }
        else {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("login","");
            if (json != "") {
                login = gson.fromJson(json, Login.class);
                String namatoko1 = login.getLevel();
                Intent mIntent = getIntent();
                Integer id = login.getId();

                File imagefile = new File(mediaPath);
                RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                MultipartBody.Part gambar = MultipartBody.Part.createFormData("gambar", imagefile.getName(), reqBody);

                RequestBody emailr = RequestBody.create(MediaType.parse("text/plain"), daftaruser.getText().toString().trim());
                RequestBody passwordr = RequestBody.create(MediaType.parse("text/plain"), daftarpassword.getText().toString().trim());
                RequestBody levelr = RequestBody.create(MediaType.parse("text/plain"), daftaremail.getText().toString().trim());
                RequestBody relasir = RequestBody.create(MediaType.parse("text/plain"), daftaartelp.getText().toString().trim());


                LoginRegInterfaace apiInterface = ApiClient.getClient().create(LoginRegInterfaace.class);
                Call<GetRegister> call = apiInterface.ubahreg(id, emailr, passwordr, levelr, relasir, gambar);
                call.enqueue(new Callback<GetRegister>() {
                    @Override
                    public void onResponse(Call<GetRegister> call, Response<GetRegister> response) {
                        if (response.isSuccessful()) {
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(PengaturanActivity.this, "My Notification");
                            builder.setContentTitle("Ubah Profil Anda Berhasil!");
                            builder.setContentText("Silahkan Login Menggunakan Akun Yang Telah Anda Rubah!");
                            builder.setSmallIcon(R.drawable.ic_baseline_cloud_done_24);
                            builder.setAutoCancel(true);

                            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(PengaturanActivity.this);
                            managerCompat.notify(3, builder.build());

                            Intent intent = new Intent(PengaturanActivity.this, LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(PengaturanActivity.this, "berhasil", Toast.LENGTH_SHORT).show();
//                        PenjualActivity;

                        } else {
                            Toast.makeText(PengaturanActivity.this, "t", Toast.LENGTH_SHORT).show();
                        }

                        // lanjut register tanpa gambar


                    }

                    @Override
                    public void onFailure(Call<GetRegister> call, Throwable t) {
                        Toast.makeText(PengaturanActivity.this, "noooo", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        }

    }

    public void backActivity1() {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.kanan, R.anim.kanan1);
        finish();


    }



}