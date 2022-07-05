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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuktiTransfer extends AppCompatActivity {

    ImageView imageView8;
    TextView tvBuktiTrf;

    LoginModel loginModel;
    SharedPreferences sharedPreferences,sharedPreferences1;

    private static final int REQUEST_PICK_PHOTO = 9544;
    private static final int REQUEST_WRITE_PERMISSION = ServerConfig.REQUEST_WRITE_PERMISSION;
    private static final String INSERT_FLAG = ServerConfig.INSERT_FLAG;

    private String mediaPath;
    private String postPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bukti_transfer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Bukti Pembayaran");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientku));
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

        Intent intent = getIntent();
        tvBuktiTrf = findViewById(R.id.tvBuktiTrf);
        imageView8 = findViewById(R.id.imageView8);

        if (intent.getStringExtra("bukti").isEmpty()){
            tvBuktiTrf.setText("Masukkan Bukti Pembayaran");
            imageView8.setOnClickListener(new View.OnClickListener() {
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
        } else{
            tvBuktiTrf.setText("Bukti Pembayaran Anda");
            if (json != "") {
                loginModel = gson.fromJson(json, LoginModel.class);
                Glide.with(BuktiTransfer.this)
                        .load("" + intent.getStringExtra("bukti"))
                        .apply(new RequestOptions().override(0, 200))
                        .into(imageView8);
            }

        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            insertTambahProduk();
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
                    imageView8.setImageURI(data.getData());
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
            insertTambahProduk();
        }
    }

    private void insertTambahProduk() {
        Intent mIntent = getIntent();

        Integer id = mIntent.getIntExtra("NoPesanan",0);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login", "");

        if (json != "") {
            if (mediaPath == null) {
                Toast.makeText(BuktiTransfer.this, "Masukkan Foto Produk", Toast.LENGTH_SHORT).show();
            } else {
                    File imagefile = new File(mediaPath);
                    RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                    MultipartBody.Part bukti = MultipartBody.Part.createFormData("gambar", imagefile.getName(), reqBody);
                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<GetDataHistory> call = apiInterface.UpdateHistory3(id, bukti);
                    call.enqueue(new Callback<GetDataHistory>() {
                        @Override
                        public void onResponse(Call<GetDataHistory> call, Response<GetDataHistory> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(BuktiTransfer.this, "ya", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(BuktiTransfer.this, "t", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetDataHistory> call, Throwable t) {
                            Intent nwintent = new Intent(BuktiTransfer.this, HistoryOrderan.class);
                            startActivity(nwintent);
                        }
                    });
            }
        }
    }

    public void btnOke(View view) {
        Intent intent = getIntent();
        if (intent.getStringExtra("bukti").isEmpty()){
            requestPermission();
        }else {
            Intent nwintent = new Intent(BuktiTransfer.this, HistoryOrderan.class);
            startActivity(nwintent);
        }
    }
}