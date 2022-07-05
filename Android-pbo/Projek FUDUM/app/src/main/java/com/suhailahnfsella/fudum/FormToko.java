package com.suhailahnfsella.fudum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormToko extends AppCompatActivity {

    LoginModel loginModel;
    SharedPreferences sharedPreferences,sharedPreferences1;
    ImageView btnInsertImgToko;
    EditText etNamaToko, etAlamatToko, etKecToko, etKabToko, etTahunToko, etSosmedToko, etNoWaToko, etEmailToko;

    private static final int REQUEST_PICK_PHOTO = 9544;
    private static final int REQUEST_WRITE_PERMISSION = ServerConfig.REQUEST_WRITE_PERMISSION;
    private static final String INSERT_FLAG = ServerConfig.INSERT_FLAG;

    private String mediaPath;
    private String postPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_toko);

        getSupportActionBar().setTitle("Formulir Buka Toko");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientku));
        }

        btnInsertImgToko = findViewById(R.id.btnInsertImgToko);

        btnInsertImgToko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("images/*");

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_PICK);


                Intent choose = Intent.createChooser(getIntent,"select");
                choose.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[] {galleryIntent});
                startActivityForResult(choose, REQUEST_PICK_PHOTO);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            insertBukaToko();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    btnInsertImgToko.setImageURI(data.getData());
                    cursor.close();

                    postPath = mediaPath;
                }
            }
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            insertBukaToko();
        }
    }

    private void insertBukaToko() {
            etNamaToko = findViewById(R.id.etNamaToko);
            etAlamatToko = findViewById(R.id.etAlamatToko);
            etKecToko = findViewById(R.id.etKecToko);
            etKabToko = findViewById(R.id.etKabToko);
            etTahunToko = findViewById(R.id.etTahunToko);
            etSosmedToko = findViewById(R.id.etSosmedToko);
            etNoWaToko = findViewById(R.id.etTelp);
            etEmailToko = findViewById(R.id.etEmailToko);

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("login","");

            if (json != "") {
                if (mediaPath == null){
                    Toast.makeText(FormToko.this, "Masukkan Foto Profil Toko", Toast.LENGTH_SHORT).show();
                } else {
                    loginModel = gson.fromJson(json,LoginModel.class);
                    Integer id = loginModel.getId();

                    File imagefile = new File(mediaPath);
                    RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                    MultipartBody.Part fototoko = MultipartBody.Part.createFormData("fototoko", imagefile.getName(), reqBody);

                    RequestBody idtoko = RequestBody.create(MediaType.parse("text/plain"),id.toString());
                    RequestBody namatoko = RequestBody.create(MediaType.parse("text/plain"),etNamaToko.getText().toString());
                    RequestBody alamattoko = RequestBody.create(MediaType.parse("text/plain"),etAlamatToko.getText().toString());
                    RequestBody kecamatan = RequestBody.create(MediaType.parse("text/plain"),etKecToko.getText().toString());
                    RequestBody kabupaten = RequestBody.create(MediaType.parse("text/plain"),etKabToko.getText().toString());
                    RequestBody tahuntoko = RequestBody.create(MediaType.parse("text/plain"),etTahunToko.getText().toString());
                    RequestBody sosmed = RequestBody.create(MediaType.parse("text/plain"),etSosmedToko.getText().toString());
                    RequestBody whatsapp = RequestBody.create(MediaType.parse("text/plain"),etNoWaToko.getText().toString());
                    RequestBody email = RequestBody.create(MediaType.parse("text/plain"),etEmailToko.getText().toString());

                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<GetToko> call = apiInterface.insertToko(idtoko, namatoko, fototoko, alamattoko, kecamatan, kabupaten, tahuntoko, sosmed, whatsapp, email);
                    call.enqueue(new Callback<GetToko>() {
                        @Override
                        public void onResponse(Call<GetToko> call, Response<GetToko> response) {
                            if (response.isSuccessful()) {
                              updateStatus();
                              Intent nwintent = new Intent(FormToko.this, Langganan.class);
                              startActivity(nwintent);
                            } else {
                                Toast.makeText(FormToko.this, "t", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetToko> call, Throwable t) {
                            Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                            //Log.d("RETRO", "ON FAILURE : " + t.getCause());
                            Toast.makeText(getApplicationContext(), "Error, image", Toast.LENGTH_LONG).show();
                        }
                    });
                }
        }
    }

    public void updateStatus(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

        loginModel = gson.fromJson(json,LoginModel.class);
        Integer id = loginModel.getId();

        Integer sts = 2;

        RequestBody status = RequestBody.create(MediaType.parse("text/plain"), sts.toString());

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetUser> call = apiInterface.updateStatus(id, status);
        call.enqueue(new Callback<GetUser>() {
            @Override
            public void onResponse(Call<GetUser> call, Response<GetUser> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(FormToko.this, "ya", Toast.LENGTH_SHORT).show();
//                    finish();
//                } else {
//                    Toast.makeText(FormToko.this, "t", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onFailure(Call<GetUser> call, Throwable t) {
//                if (loginModel.getStatus() == 0) {
//                    Intent intent = new Intent(FormToko.this, AccountV1.class);
//                    startActivity(intent);
//                } else if (loginModel.getStatus() == 1) {
//                    Intent intent = new Intent(FormToko.this, AccountV2.class);
//                    startActivity(intent);
//                } else if (loginModel.getStatus() == 2) {
//                    Intent intent = new Intent(FormToko.this, AccountV1.class);
//                    startActivity(intent);
//                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void btnDaftarToko(View view) {
        etNamaToko = findViewById(R.id.etNamaToko);
        etAlamatToko = findViewById(R.id.etAlamatToko);
        etKecToko = findViewById(R.id.etKecToko);
        etKabToko = findViewById(R.id.etKabToko);
        etTahunToko = findViewById(R.id.etTahunToko);
        etSosmedToko = findViewById(R.id.etSosmedToko);
        etNoWaToko = findViewById(R.id.etTelp);
        etEmailToko = findViewById(R.id.etEmailToko);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login", "");

        if (json != "") {
            loginModel = gson.fromJson(json, LoginModel.class);

            if (etNamaToko.length() == 0) {
                etNamaToko.setError("Nama Toko harus diisi!");
            } else if (!etNamaToko.getText().toString().matches("^[a-z,A-Z,1-9\\s]+$")) {
                etNamaToko.setError("Nama Toko tidak menggunakan simbol");
            } else if (etAlamatToko.length() == 0) {
                etAlamatToko.setError("Alamat Toko harus diisi!");
            } else if (etKecToko.length() == 0) {
                etKecToko.setError("Isi Kecamatan Toko");
            } else if (!etKecToko.getText().toString().matches("^[a-z,A-Z\\s]+$")) {
                etKecToko.setError("Isi Kecamatan dengan benar!");
            } else if (etKabToko.length() == 0) {
                etKabToko.setError("Isi Kabupaten Toko");
            } else if (!etKabToko.getText().toString().matches("^[a-z,A-Z\\s]+$")) {
                etKabToko.setError("Isi Kabupaten dengan benar!");
            } else if (etTahunToko.length() == 0) {
                etTahunToko.setError("Isi Tahun Toko didirikan");
            } else if (etSosmedToko.length() == 0) {
                etSosmedToko.setError("Jika tidak ada sosmed isi dengan tanda -");
            } else if (TextUtils.isEmpty(etNoWaToko.getText().toString()) || etNoWaToko.length() < 11) {
                etNoWaToko.setError("Masukkan nomor whatsapp yang aktif");
            } else if (etEmailToko.length() == 0) {
                etEmailToko.setError("Isi Email Toko");
            } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmailToko.getText().toString()).matches()) {
                etEmailToko.setError("Isi email dengan benar!");
            } else {
                requestPermission();
            }
            return;
        }
    }
}