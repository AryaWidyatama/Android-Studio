package com.komputerkit.ukomde_afa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailUbahBarang extends AppCompatActivity {
    private ImageButton imageButton;
    private Button button,btUpdate;
    ImageView imgdetail;
    ImageView gambar,imgHolder;
    EditText idkategori,kode,menu,harga,email,desripsi,id1;
    Login login;
    GetLogin getLogin;
    ProgressDialog progressBar;
    SharedPreferences sharedPreferences,sharedPreferences1;

    private String mediaPath;
    private String postPath;

    ApiMenuInterface mApiInterface;
    private static final int REQUEST_PICK_PHOTO = ServerConfig.REQUEST_PICK_PHOTO;
    private static final int REQUEST_WRITE_PERMISSION = ServerConfig.REQUEST_WRITE_PERMISSION;

    private final int ALERT_DIALOG_CLOSE = ServerConfig.ALERT_DIALOG_CLOSE;
    private final int ALERT_DIALOG_DELETE = ServerConfig.ALERT_DIALOG_DELETE;
    private static final String UPDATE_FLAG = ServerConfig.UPDATE_FLAG;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            updateImageUpload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ubah_barang);
        getSupportActionBar().hide();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

        if (json != ""){
            email = (EditText) findViewById(R.id.EmailUbah);
            login = gson.fromJson(json,Login.class);
            email.setText(login.getRelasi());
        }


        id1 = (EditText) findViewById(R.id.idmenuUbah);
        idkategori = (EditText) findViewById(R.id.idKaaategoriUbah);
        kode = (EditText) findViewById(R.id.KodeUbah);
        menu = (EditText) findViewById(R.id.MenuUbah);
        harga = (EditText) findViewById(R.id.HaaargaUbah);
        email = (EditText) findViewById(R.id.EmailUbah);
        desripsi = (EditText) findViewById(R.id.DsripsiUbah);
        imgHolder = (ImageView) findViewById(R.id.GAAMABARuBAH);
        gambar = (ImageView) findViewById(R.id.ubahGaambar);
        btUpdate = (Button) findViewById(R.id.btnout5);

//        imgdetail = (ImageView)findViewById(R.id.imgDetailUbahrcv);
//        tv1 = (EditText) findViewById(R.id.idKaaategoriUbah);
//        tv2 = (EditText) findViewById(R.id.KodeUbah);
//        tv3 = (EditText) findViewById(R.id.tvKodeBarangUbah);
//        tv4 = (EditText) findViewById(R.id.tvHargaDetailRcv);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backActivity();
            }
        });

        Intent mIntent = getIntent();
        id1.setText(Integer.toString(mIntent.getIntExtra("idmenu",0)));
       // idkategori.setText(Integer.toString(mIntent.getIntExtra("idkategori",0)));
        kode.setText(mIntent.getStringExtra("kode"));
        menu.setText(mIntent.getStringExtra("menu"));
        harga.setText(mIntent.getStringExtra("harga"));
      //  email.setText(mIntent.getStringExtra("email"));
        desripsi.setText(mIntent.getStringExtra("deskripsi"));

        Glide.with(DetailUbahBarang.this)
                .load("" + mIntent.getStringExtra("gambarbarang"))
                .apply(new RequestOptions().override(350, 550))
                .into(imgHolder);


        btUpdate = (Button) findViewById(R.id.btnout5);
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeUtama();
            }
        });


        mApiInterface = ApiClient.getClient().create(ApiMenuInterface.class);

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

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar = new ProgressDialog(DetailUbahBarang.this);
                progressBar.show();
                progressBar.setContentView(R.layout.progres_dialog);
                progressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                updateImageUpload();
            }
        });





        //imgdetail.setImageResource(getIntent().getIntExtra("gambarbarang",0));
//        tv1.setText(mIntent.getStringExtra("Nama"));
//        tv2.setText(mIntent.getStringExtra("desc"));
//        tv4.setText(mIntent.getStringExtra("Alamat"));
//        Glide.with(DetailUbahBarang.this)
//                .load("" + mIntent.getStringExtra("gambarbarang"))
//                .apply(new RequestOptions().override(350, 550))
//                .into(imgdetail);
////        tv4.setTag(tv4.getKeyListener());
////        tv4.setKeyListener(null);
////        tv4.setText(mIntent.getStringExtra("Alamat"));
//        tv3.setText(getIntent().getStringExtra("kode"));
////        tv4.setText(getIntent().getStringExtra("hargabrg"));
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

    private void updateImageUpload() {
        final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if (mediaPath== null)
        {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("login","");
            if (json != "") {
                login = gson.fromJson(json, Login.class);
                String namatoko1 = login.getLevel();
                Intent mIntent = getIntent();
                Integer id = mIntent.getIntExtra("idmenu",0);

               // RequestBody idkategori1 = RequestBody.create(MediaType.parse("text/plain"),idkategori.getText().toString().trim());
                RequestBody menu1 = RequestBody.create(MediaType.parse("text/plain"),menu.getText().toString().trim());
                RequestBody harga1 = RequestBody.create(MediaType.parse("text/plain"),harga.getText().toString().trim());
                RequestBody email1 = RequestBody.create(MediaType.parse("text/plain"),email.getText().toString().trim());
                RequestBody deskripsi1 = RequestBody.create(MediaType.parse("text/plain"),desripsi.getText().toString().trim());
                RequestBody kode1 =  RequestBody.create(MediaType.parse("text/plain"),kode.getText().toString().trim());
                RequestBody namatoko =  RequestBody.create(MediaType.parse("text/plain"),namatoko1.trim());

                ApiMenuInterface apiInterface = ApiClient.getClient().create(ApiMenuInterface.class);
                Call<GetDaataMenu> call = apiInterface.postUpdateHerosNoiMAGE(id,menu1,kode1,harga1,email1,deskripsi1,namatoko);
                call.enqueue(new Callback<GetDaataMenu>() {
                    @Override
                    public void onResponse(Call<GetDaataMenu> call, Response<GetDaataMenu> response) {
                        TambahData.ma.panggilRetrofit();
                        Toast.makeText(DetailUbahBarang.this, "berhasil", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<GetDaataMenu> call, Throwable t) {
                        Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                        //Log.d("RETRO", "ON FAILURE : " + t.getCause());
                        Toast.makeText(getApplicationContext(), "Error, image", Toast.LENGTH_LONG).show();
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
                File imagefile = new File(mediaPath);
                RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                MultipartBody.Part partImage = MultipartBody.Part.createFormData("gambar", imagefile.getName(), reqBody);

//            RequestBody id = RequestBody.create(MediaType.parse("text/plain"),id1.getText().toString().trim());
                Intent mIntent = getIntent();
                Integer id = mIntent.getIntExtra("idmenu",0);

              //  RequestBody idkategori1 = RequestBody.create(MediaType.parse("text/plain"),idkategori.getText().toString().trim());
                RequestBody menu1 = RequestBody.create(MediaType.parse("text/plain"),menu.getText().toString().trim());
                RequestBody harga1 = RequestBody.create(MediaType.parse("text/plain"),harga.getText().toString().trim());
                RequestBody email1 = RequestBody.create(MediaType.parse("text/plain"),email.getText().toString().trim());
                RequestBody deskripsi1 = RequestBody.create(MediaType.parse("text/plain"),desripsi.getText().toString().trim());
                RequestBody kode1 =  RequestBody.create(MediaType.parse("text/plain"),kode.getText().toString().trim());
                RequestBody namatoko =  RequestBody.create(MediaType.parse("text/plain"),namatoko1.trim());

                ApiMenuInterface apiInterface = ApiClient.getClient().create(ApiMenuInterface.class);
                Call<GetDaataMenu> call = apiInterface.postUpdateHeros(id,menu1,kode1,harga1,email1,deskripsi1,namatoko,partImage);
                call.enqueue(new Callback<GetDaataMenu>() {
                    @Override
                    public void onResponse(Call<GetDaataMenu> call, Response<GetDaataMenu> response) {
                        TambahData.ma.panggilRetrofit();
                        Toast.makeText(DetailUbahBarang.this, "berhasil", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<GetDaataMenu> call, Throwable t) {
                        Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                        //Log.d("RETRO", "ON FAILURE : " + t.getCause());
                        Toast.makeText(getApplicationContext(), "Error, image", Toast.LENGTH_LONG).show();
                    }
                });

            }

        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            updateImageUpload();
        }
    }







    public void backActivity(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DetailUbahBarang.this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");
        if (json != ""){
            login = gson.fromJson(json,Login.class);

            String idKategori = login.getRelasi();
            Intent intent = new Intent(DetailUbahBarang.this,KurangData.class);
            intent.putExtra("idKategori", idKategori);
            startActivity(intent);
            overridePendingTransition(R.anim.kanan, R.anim.kanan1);
            finish();
        }

    }

    public void HomeUtama(){
        Toast.makeText(this, "Ubah Data Berhasil!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,PenjualActivity.class);
        startActivity(intent);

        finish();

    }
}