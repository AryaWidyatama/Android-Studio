package com.komputerkit.komen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingAccount extends AppCompatActivity {

    LoginModel loginModel;
    SharedPreferences sharedPreferences,sharedPreferences1;

    ImageView btnInsertImgUser;
    EditText etUbahNama, etUbahUsername, etPwLama, etPwBaru, etKonfirmPwBaru;

    ApiInterface mApiInterface;
    private static final int REQUEST_PICK_PHOTO = 9544;
    private static final int REQUEST_WRITE_PERMISSION = ServerConfig.REQUEST_WRITE_PERMISSION;
    private static final String INSERT_FLAG = ServerConfig.INSERT_FLAG;

    private String mediaPath;
    private String postPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_account);
        btnInsertImgUser = findViewById(R.id.btnInsertImgToko);
        etUbahNama = findViewById(R.id.etUbahNama);
        etUbahUsername = findViewById(R.id.etUbahUsername);
        etPwBaru = findViewById(R.id.etPwBaru);
        etPwLama = findViewById(R.id.etPwLama);
        etKonfirmPwBaru = findViewById(R.id.etKonfirmPwBaru);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

//        if (json != "") {
//            loginModel = gson.fromJson(json, LoginModel.class);
//            Glide.with(SettingAccount.this)
//                 //   .load("" + loginModel.getFotoprofil())
//                  //  .apply(new RequestOptions().override(0, 200))
//                  //  .into(btnInsertImgUser);//sek wait
////            etUbahNama.setText(loginModel.getNamapanjang());
////            etUbahUsername.setText(loginModel.getUsername());
////            etPwLama.setText(loginModel.getPassword());
////            etPwBaru.setText(loginModel.getPassword());
//        }

        getSupportActionBar().setTitle("Pengaturan Akun");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientku));
        }

        btnInsertImgUser.setOnClickListener(new View.OnClickListener() {
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
          //  saveImageUpload();
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
                    btnInsertImgUser.setImageURI(data.getData());
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
         //   saveImageUpload();
        }
    }

//    private void saveImageUpload(){
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("login","");
//        loginModel = gson.fromJson(json,LoginModel.class);
//
//
//        if (mediaPath == null)
//        {
//                Integer id = loginModel.getId();
//
//                RequestBody namapanjang = RequestBody.create(MediaType.parse("text/plain"), etUbahNama.getText().toString());
//                RequestBody username = RequestBody.create(MediaType.parse("text/plain"), etUbahUsername.getText().toString());
//                RequestBody password = RequestBody.create(MediaType.parse("text/plain"), etPwBaru.getText().toString());
//
//                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//                Call<GetUser> call = apiInterface.addMahasiswanoimage(id, namapanjang, username, password);
//                call.enqueue(new Callback<GetUser>() {
//                    @Override
//                    public void onResponse(Call<GetUser> call, Response<GetUser> response) {
//                        if (response.isSuccessful()) {
//                            Toast.makeText(SettingAccount.this, "ya", Toast.LENGTH_SHORT).show();
//                            finish();
//                        } else {
//                            Toast.makeText(SettingAccount.this, "t", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<GetUser> call, Throwable t) {
//                        if (loginModel.getStatus() == 0) {
//                            Intent intent = new Intent(SettingAccount.this, AccountV1.class);
//                            startActivity(intent);
//                        } else if (loginModel.getStatus() == 1) {
//                            Intent intent = new Intent(SettingAccount.this, AccountV2.class);
//                            startActivity(intent);
//                        }
//                    }
//                });
//        }else{
//            etUbahNama = findViewById(R.id.etUbahNama);
//            etUbahUsername = findViewById(R.id.etUbahUsername);
//            etPwBaru = findViewById(R.id.etPwBaru);
//            etPwLama = findViewById(R.id.etPwLama);
//            etKonfirmPwBaru = findViewById(R.id.etKonfirmPwBaru);
//
//            Integer id = loginModel.getId();
//
//            File imagefile = new File(mediaPath);
//            RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
//            MultipartBody.Part fotoprofil = MultipartBody.Part.createFormData("fotoprofil", imagefile.getName(), reqBody);
//
//            RequestBody namapanjang = RequestBody.create(MediaType.parse("text/plain"), etUbahNama.getText().toString());
//            RequestBody username = RequestBody.create(MediaType.parse("text/plain"), etUbahUsername.getText().toString());
//            RequestBody password = RequestBody.create(MediaType.parse("text/plain"), etPwBaru.getText().toString());
//
//            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//            Call<GetUser> call = apiInterface.addMahasiswa(id, namapanjang, username, password, fotoprofil);
//            call.enqueue(new Callback<GetUser>() {
//                @Override
//                public void onResponse(Call<GetUser> call, Response<GetUser> response) {
//                    if (response.isSuccessful()) {
//                        Toast.makeText(SettingAccount.this, "ya", Toast.LENGTH_SHORT).show();
//                        finish();
//                    } else {
//                        Toast.makeText(SettingAccount.this, "t", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<GetUser> call, Throwable t) {
//                    if (loginModel.getStatus() == 0) {
//                        Intent intent = new Intent(SettingAccount.this, AccountV1.class);
//                        startActivity(intent);
//                    } else if (loginModel.getStatus() == 1) {
//                        Intent intent = new Intent(SettingAccount.this, AccountV2.class);
//                        startActivity(intent);
//                    }
//                }
//            });
//        }



   // }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void btnKonfirm(View view) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

        if (json != "") {
            loginModel = gson.fromJson(json, LoginModel.class);
            String pw = loginModel.getPassword();
            String pw1 = etPwLama.getText().toString();
            if(etUbahNama.length() > 0 && !etUbahNama.getText().toString().matches("^[a-z,A-Z\\s]+$")){
                etUbahNama.setError("Harus huruf saja");
            } else if(etUbahUsername.length() > 15){
                etUbahUsername.setError("Tidak boleh lebih dari 15 karakter");
            } else if(etPwBaru.length() > 0){
                if(etPwLama.length() == 0){
                    etPwLama.setError("Jika ubah password, masukkan password lama");
                } else if(!pw1.matches(pw)){
                    etPwLama.setError("Password Lama Salah");
                } else if(!etPwBaru.getText().toString().matches("^.*(?=.*[a-zA-Z])(?=.*[0-9]).*$")) {
                    etPwBaru.setError("Password minimal 8 karakter (huruf & angka)");
                } else if(etKonfirmPwBaru.length() == 0 && etKonfirmPwBaru.getText().toString() != etPwBaru.getText().toString()){
                    etKonfirmPwBaru.setError("Masukkan Konfirm Password sesuai Password baru");
                } else {
                    requestPermission();
                }
            }else {
                requestPermission();
            }
            return;
        }



    }

    public void btnLogOut(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(SettingAccount.this,Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}