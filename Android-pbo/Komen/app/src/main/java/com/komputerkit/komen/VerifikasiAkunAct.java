package com.komputerkit.komen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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

public class VerifikasiAkunAct extends AppCompatActivity {
    SharedPreferences sharedPreferences,sharedPreferences1;
    private String mediaPath;
    private String postPath;
    ImageView imageView;
    SiswaModel loginModel;
    ProgressDialog progressBar;
    Dialog dialog;
    ApiInterface mApiInterface;




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
        setContentView(R.layout.activity_verifikasi_akun);

        getSupportActionBar().setTitle("Verifikasi Akun");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(this.getResources().getColor(R.color.limedash));

        imageView = findViewById(R.id.upload);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent galleryIntent = new Intent();
                galleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_PICK);


                Intent choose = Intent.createChooser(galleryIntent, "select");
                choose.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{galleryIntent});
                startActivityForResult(choose, REQUEST_PICK_PHOTO);

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


                    Cursor cursor = getContentResolver().query(selectedImage, null, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    mediaPath = cursor.getString(columnIndex);
                    imageView.setImageURI(data.getData());
                    cursor.close();

                    postPath = mediaPath;
                }
            }
        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//            if (requestCode == REQUEST_PICK_PHOTO) {
//                if (data != null) {
//                    // Ambil Image Dari Galeri dan Foto
//                    Uri selectedImage = data.getData();
//                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                    assert cursor != null;
//                    cursor.moveToFirst();
//
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    mediaPath = cursor.getString(columnIndex);
//                    imageView.setImageURI(data.getData());
//                    cursor.close();
//
//                    postPath = mediaPath;
//                }
//            }
//        }
//    }


    private void saveImageUpload() {

        //   final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        if (mediaPath == null) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("login", "");
            dialog = new Dialog(VerifikasiAkunAct.this);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_verif_noimage);
            Button btnoke = dialog.findViewById(R.id.btnok);
            btnoke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressBar.dismiss();
                    dialog.dismiss();

                }
            });
            dialog.show();

        }else {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("login", "");
            if (json != "") {
                Toast.makeText(this, "yeahh", Toast.LENGTH_SHORT).show();
                loginModel = gson.fromJson(json, SiswaModel.class);
                //   String namatoko1 = login.getLevel();
                Intent mIntent = getIntent();
                Integer idsiswa1 = loginModel.getId();
                String NamaLengkap1 = loginModel.getNamaLengkap();
                String Email1 = loginModel.getEmail();
                String NoTelp1 = loginModel.getNoTelp();
                String Kelas1 = loginModel.getKelas();
                String Jurusan1 = loginModel.getJurusan();
                String Username1 = loginModel.getUsername();
//                String idsiswa = String.valueOf(iduser1);

                File imagefile = new File(mediaPath);


                RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                MultipartBody.Part gambar = MultipartBody.Part.createFormData("FotoKtp", imagefile.getName(), reqBody);
                RequestBody Username = RequestBody.create(MediaType.parse("text/plain"), Username1.trim());
                RequestBody idsiswa = RequestBody.create(MediaType.parse("text/plain"), idsiswa1.toString().trim());
                RequestBody Email = RequestBody.create(MediaType.parse("text/plain"), Email1.trim());
                RequestBody Kelas = RequestBody.create(MediaType.parse("text/plain"), Kelas1.trim());
                RequestBody Jurusan = RequestBody.create(MediaType.parse("text/plain"), Jurusan1.trim());
                RequestBody NoTelp = RequestBody.create(MediaType.parse("text/plain"), NoTelp1.trim());
                RequestBody NamaLengkap = RequestBody.create(MediaType.parse("text/plain"), NamaLengkap1.trim());


                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<GetVerifAkun> call = apiInterface.verifakun(NamaLengkap,idsiswa, Email, gambar, NoTelp, Kelas, Jurusan, Username);
                call.enqueue(new Callback<GetVerifAkun>() {
                    @Override
                    public void onResponse(Call<GetVerifAkun> call, Response<GetVerifAkun> response) {
                        if (response.isSuccessful()) {

                            progressBar.dismiss();
                            dialog = new Dialog(VerifikasiAkunAct.this);
                            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                                    WindowManager.LayoutParams.WRAP_CONTENT);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.setContentView(R.layout.dialog_tunggu_verif);
                            Button btnoke = dialog.findViewById(R.id.btnok);
                            btnoke.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        haha();
                                    dialog.dismiss();

                                }
                            });
                            dialog.show();
                            //monggo


                        }


                    }

                    @Override
                    public void onFailure(Call<GetVerifAkun> call, Throwable t) {
                        progressBar.dismiss();
                        dialog = new Dialog(VerifikasiAkunAct.this);
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                                WindowManager.LayoutParams.WRAP_CONTENT);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setContentView(R.layout.dialog_verif_gagal);
                        Button btnoke = dialog.findViewById(R.id.btnok);
                        btnoke.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog.dismiss();

                            }
                        });
                        dialog.show();
                    }
                });
            }
        }



    }


    private void haha (){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login", "");
        if (json != "") {

            loginModel = gson.fromJson(json, SiswaModel.class);
            mApiInterface = ApiClient.getClient().create(ApiInterface.class);
            Integer id = loginModel.getId();
            ApiInterface api1 = ApiClient.getClient().create(ApiInterface.class);
            Call<GetSiswa> del1 = api1.UpdateUser(id);
            del1.enqueue(new Callback<GetSiswa>() {
                @Override
                public void onResponse(Call<GetSiswa> call, Response<GetSiswa> response) {
                    Log.d("Retro", "onResponse"+call);
                    Intent intent = new Intent(VerifikasiAkunAct.this,Login.class);
                    startActivity(intent);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();

                    finish();
                }
                @Override
                public void onFailure(Call<GetSiswa> call, Throwable t) {
                    Log.d("Retro", "onFailure"+t.getLocalizedMessage());
                    Toast.makeText(VerifikasiAkunAct.this, "tes :"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    public void Kirim(View view) {

        progressBar = new ProgressDialog(VerifikasiAkunAct.this);
        progressBar.show();
        progressBar.setContentView(R.layout.progres_dialog);
        progressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        saveImageUpload();

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
}