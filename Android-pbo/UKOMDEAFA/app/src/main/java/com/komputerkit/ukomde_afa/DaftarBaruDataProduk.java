package com.komputerkit.ukomde_afa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarBaruDataProduk extends AppCompatActivity {
    EditText idkategori,kode,menu,harga,email,desripsi;
    private ImageButton imageButton;
    ImageView gambar,imgHolder;
    private Button button;
    ProgressDialog progressBar;
    private String mediaPath;
    private String postPath;
    Login login;
    Dialog dialog;
    GetLogin getLogin;
    SharedPreferences sharedPreferences,sharedPreferences1;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_baru_data_produk);
        getSupportActionBar().hide();



        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

        if (json != ""){
            desripsi = (EditText) findViewById(R.id.DsripsiUbah);
            login = gson.fromJson(json,Login.class);
            desripsi.setText(login.getRelasi());
        }

        idkategori = (EditText) findViewById(R.id.idKaaategoriUbah);
        kode = (EditText) findViewById(R.id.KodeUbah);
        menu = (EditText) findViewById(R.id.MenuUbah);
        harga = (EditText) findViewById(R.id.HaaargaUbah);
        email = (EditText) findViewById(R.id.EmailUbah);
        desripsi = (EditText) findViewById(R.id.DsripsiUbah);
        imgHolder = (ImageView) findViewById(R.id.GAAMABARuBAH);
        gambar = (ImageView) findViewById(R.id.ubahGaambar);
        button = (Button) findViewById(R.id.btnout5);

        mApiInterface = ApiClient.getClient().create(ApiMenuInterface.class);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backActivity();
            }
        });


        gambar.setOnClickListener(new View.OnClickListener() {
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar = new ProgressDialog(DaftarBaruDataProduk.this);
                progressBar.show();
                progressBar.setContentView(R.layout.progres_dialog);
                progressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


                requestPermission();
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

    private void saveImageUpload(){
        String nim1 = idkategori.getText().toString().trim();
        String nama1 = kode.getText().toString().trim();
        final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if (mediaPath== null)
        {
            Toast.makeText(getApplicationContext(), "Pilih gambar dulu, baru simpan ...!", Toast.LENGTH_LONG).show();
            dialog=new Dialog(DaftarBaruDataProduk.this);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_daftar_menu);


            Button btnoke=dialog.findViewById(R.id.btniya);
            btnoke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    progressBar.dismiss();
                    dialog.dismiss();


                }
            });


            dialog.show();
        }
        else {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("login","");
            if (json != "") {
                login = gson.fromJson(json, Login.class);
                String namatoko1 = login.getLevel();


                //bitmap
                Bitmap fullSizeBItmap = BitmapFactory.decodeFile(mediaPath);

               Bitmap reduceBitmap = ImageResizer.reduceBitmapSize(fullSizeBItmap, 737280);

              File reducedFile =  getBitmapFile(reduceBitmap);




                File imagefile = new File(mediaPath);
                RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                MultipartBody.Part partImage = MultipartBody.Part.createFormData("gambar", imagefile.getName(), reqBody);

              //  RequestBody idkategori1 = RequestBody.create(MediaType.parse("text/plain"),idkategori.getText().toString().trim());
                RequestBody menu1 = RequestBody.create(MediaType.parse("text/plain"),menu.getText().toString().trim());
                RequestBody harga1 = RequestBody.create(MediaType.parse("text/plain"),harga.getText().toString().trim());
                RequestBody email1 = RequestBody.create(MediaType.parse("text/plain"),email.getText().toString().trim());
                RequestBody deskripsi1 = RequestBody.create(MediaType.parse("text/plain"),desripsi.getText().toString().trim());
                RequestBody kode1 =  RequestBody.create(MediaType.parse("text/plain"),kode.getText().toString().trim());
                RequestBody namatoko =  RequestBody.create(MediaType.parse("text/plain"),namatoko1.trim());


                ApiMenuInterface apiInterface = ApiClient.getClient().create(ApiMenuInterface.class);
                Call<GetDaataMenu> call = apiInterface.addMahasiswa(menu1,harga1,email1,deskripsi1,kode1,namatoko,partImage);
                call.enqueue(new Callback<GetDaataMenu>() {
                    @Override
                    public void onResponse(Call<GetDaataMenu> call, Response<GetDaataMenu> response) {
                        if (response.isSuccessful()) {

                            Toast.makeText(DaftarBaruDataProduk.this, "ya", Toast.LENGTH_SHORT).show();
//                        PenjualActivity;
                            finish();
                        }else{
                            Toast.makeText(DaftarBaruDataProduk.this, "t", Toast.LENGTH_SHORT).show();
                            progressBar.dismiss();
                            dialog=new Dialog(DaftarBaruDataProduk.this);
                            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                                    WindowManager.LayoutParams.WRAP_CONTENT);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.setContentView(R.layout.dialog_daftar_menu_eror);


                            Button btnoke=dialog.findViewById(R.id.btniya);
                            btnoke.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    progressBar.dismiss();
                                    dialog.dismiss();


                                }
                            });


                            dialog.show();
                        }



                    }

                    @Override
                    public void onFailure(Call<GetDaataMenu> call, Throwable t) {
                        Toast.makeText(DaftarBaruDataProduk.this, "noooo", Toast.LENGTH_SHORT).show();
                        progressBar.dismiss();
                        dialog=new Dialog(DaftarBaruDataProduk.this);
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                                WindowManager.LayoutParams.WRAP_CONTENT);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setContentView(R.layout.dialog_daftar_menu_eror);


                        Button btnoke=dialog.findViewById(R.id.btniya);
                        btnoke.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                progressBar.dismiss();
                                dialog.dismiss();


                            }
                        });


                        dialog.show();
                    }
                });


            }



        }
    }

    private File getBitmapFile(Bitmap reduceBitmap) {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "reduced_file");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        reduceBitmap.compress(Bitmap.CompressFormat.JPEG,0,bos);
        byte[] bitmapdata = bos.toByteArray();

        try{
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return file;

        }catch (Exception e){
            e.printStackTrace();
        }
        return file;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            saveImageUpload();
        }
    }





    public void backActivity(){
        Intent intent = new Intent(this,PenjualActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.kanan, R.anim.kanan1);
        finish();


    }

    public void HomeUtama(){
        Toast.makeText(this, "Unggah Data Berhasil!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,PenjualActivity.class);
        startActivity(intent);

        finish();

    }
}