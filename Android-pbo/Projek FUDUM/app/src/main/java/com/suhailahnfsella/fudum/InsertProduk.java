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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertProduk extends AppCompatActivity {

    LoginModel loginModel;
    SharedPreferences sharedPreferences,sharedPreferences1;

    private String[] Kategori = {"Makanan Berat","Makanan Ringan","Minuman","Herbal",
            "Lainnya"};

    ImageView imgTambahProduk;
    EditText etNamaProduk, etDeskripsiProduk, etKodeBarang, etStokProduk, etHargaProduk;
    Spinner spinner2;

    private static final int REQUEST_PICK_PHOTO = 9544;
    private static final int REQUEST_WRITE_PERMISSION = ServerConfig.REQUEST_WRITE_PERMISSION;
    private static final String INSERT_FLAG = ServerConfig.INSERT_FLAG;

    private String mediaPath;
    private String postPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_produk);

        getSupportActionBar().setTitle("Tambah Produk");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientku));
        }

        imgTambahProduk = findViewById(R.id.imgUpProduk);

        imgTambahProduk.setOnClickListener(new View.OnClickListener() {
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

        final Spinner List = findViewById(R.id.spinner2);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,Kategori);

        //Memasukan Adapter pada Spinner
        List.setAdapter(adapter);
        List.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int i, long l) {
                //
            }
            @Override
            public void onNothingSelected(AdapterView adapterView) {
                //
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
                    imgTambahProduk.setImageURI(data.getData());
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

    private void insertTambahProduk(){
        Intent mIntent = getIntent();

        etNamaProduk = findViewById(R.id.etNamaProduk);
        spinner2 = findViewById(R.id.spinner2);
        etDeskripsiProduk = findViewById(R.id.etDeskripsiProduk);
        etKodeBarang = findViewById(R.id.etKodeBarang);
        etStokProduk = findViewById(R.id.etStokProduk);
        etHargaProduk = findViewById(R.id.etHargaProduk);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

        if (json != "") {
            if (mediaPath == null) {
                Toast.makeText(InsertProduk.this, "Masukkan Foto Produk", Toast.LENGTH_SHORT).show();
            } else {
                loginModel = gson.fromJson(json, LoginModel.class);
             //   loginModel.setIdtoko(loginModel.getId());
                Integer id = loginModel.getId();

                File imagefile = new File(mediaPath);
                RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                MultipartBody.Part gambar = MultipartBody.Part.createFormData("gambar", imagefile.getName(), reqBody);

                if (spinner2.getSelectedItem().toString().matches("Makanan Berat")) {
                    Integer idktgr = 1;
                    RequestBody idkategori = RequestBody.create(MediaType.parse("text/plain"), idktgr.toString());
                    RequestBody idtoko = RequestBody.create(MediaType.parse("text/plain"), id.toString());
                    RequestBody kategori = RequestBody.create(MediaType.parse("text/plain"), spinner2.getSelectedItem().toString());
                    RequestBody produk = RequestBody.create(MediaType.parse("text/plain"), etNamaProduk.getText().toString());
                    RequestBody kodeproduk = RequestBody.create(MediaType.parse("text/plain"), etKodeBarang.getText().toString());
                    RequestBody stok = RequestBody.create(MediaType.parse("text/plain"), etStokProduk.getText().toString());
                    RequestBody deskripsi = RequestBody.create(MediaType.parse("text/plain"), etDeskripsiProduk.getText().toString());
                    RequestBody harga = RequestBody.create(MediaType.parse("text/plain"), etHargaProduk.getText().toString());
                    RequestBody namatoko = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("namatoko"));
                    RequestBody fototoko = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("fototoko"));
                    RequestBody alamattoko = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("alamattoko"));
                    RequestBody kecamatan = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("kec"));
                    RequestBody kabupaten = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("kab"));
                    RequestBody tahunusaha = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("tahuntoko"));
                    RequestBody sosmed = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("sosmed"));
                    RequestBody whatsapp = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("wa"));
                    RequestBody email = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("ml"));

                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<GetMenu> call = apiInterface.insertProduk(idkategori, idtoko, kategori, produk, kodeproduk, stok, deskripsi, gambar, harga, namatoko, fototoko, tahunusaha, alamattoko, kecamatan, kabupaten, sosmed, whatsapp, email);
                    call.enqueue(new Callback<GetMenu>() {
                        @Override
                        public void onResponse(Call<GetMenu> call, Response<GetMenu> response) {
                            if (response.isSuccessful()) {
//                                Intent nwintent = new Intent(InsertProduk.this, Langganan.class);
//                                startActivity(nwintent);
                            } else {
                                Toast.makeText(InsertProduk.this, "Masukkan Gambar Produk", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetMenu> call, Throwable t) {
                            Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                            //Log.d("RETRO", "ON FAILURE : " + t.getCause());
                            Intent nwintent = new Intent(InsertProduk.this, Toko.class);
                            startActivity(nwintent);
                        }
                    });
                } else if (spinner2.getSelectedItem().toString().matches("Makanan Ringan")){
                    Integer idktgr = 2;
                    RequestBody idkategori = RequestBody.create(MediaType.parse("text/plain"), idktgr.toString());
                    RequestBody idtoko = RequestBody.create(MediaType.parse("text/plain"), id.toString());
                    RequestBody kategori = RequestBody.create(MediaType.parse("text/plain"), spinner2.getSelectedItem().toString());
                    RequestBody produk = RequestBody.create(MediaType.parse("text/plain"), etNamaProduk.getText().toString());
                    RequestBody kodeproduk = RequestBody.create(MediaType.parse("text/plain"), etKodeBarang.getText().toString());
                    RequestBody stok = RequestBody.create(MediaType.parse("text/plain"), etStokProduk.getText().toString());
                    RequestBody deskripsi = RequestBody.create(MediaType.parse("text/plain"), etDeskripsiProduk.getText().toString());
                    RequestBody harga = RequestBody.create(MediaType.parse("text/plain"), etHargaProduk.getText().toString());
                    RequestBody namatoko = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("namatoko"));
                    RequestBody fototoko = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("fototoko"));
                    RequestBody alamattoko = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("alamattoko"));
                    RequestBody kecamatan = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("kec"));
                    RequestBody kabupaten = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("kab"));
                    RequestBody tahunusaha = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("tahuntoko"));
                    RequestBody sosmed = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("sosmed"));
                    RequestBody whatsapp = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("wa"));
                    RequestBody email = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("ml"));

                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<GetMenu> call = apiInterface.insertProduk(idkategori, idtoko, kategori, produk, kodeproduk, stok, deskripsi, gambar, harga, namatoko, fototoko, tahunusaha, alamattoko, kecamatan, kabupaten, sosmed, whatsapp, email);
                    call.enqueue(new Callback<GetMenu>() {
                        @Override
                        public void onResponse(Call<GetMenu> call, Response<GetMenu> response) {
                            if (response.isSuccessful()) {
//                                Intent nwintent = new Intent(InsertProduk.this, Langganan.class);
//                                startActivity(nwintent);
                            } else {
                                Toast.makeText(InsertProduk.this, "Masukkan Gambar Produk", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetMenu> call, Throwable t) {
                            Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                            //Log.d("RETRO", "ON FAILURE : " + t.getCause());
                            Intent nwintent = new Intent(InsertProduk.this, Toko.class);
                            startActivity(nwintent);
                        }
                    });
                } else if (spinner2.getSelectedItem().toString().matches("Minuman")){
                    Integer idktgr = 3;
                    RequestBody idkategori = RequestBody.create(MediaType.parse("text/plain"), idktgr.toString());
                    RequestBody idtoko = RequestBody.create(MediaType.parse("text/plain"), id.toString());
                    RequestBody kategori = RequestBody.create(MediaType.parse("text/plain"), spinner2.getSelectedItem().toString());
                    RequestBody produk = RequestBody.create(MediaType.parse("text/plain"), etNamaProduk.getText().toString());
                    RequestBody kodeproduk = RequestBody.create(MediaType.parse("text/plain"), etKodeBarang.getText().toString());
                    RequestBody stok = RequestBody.create(MediaType.parse("text/plain"), etStokProduk.getText().toString());
                    RequestBody deskripsi = RequestBody.create(MediaType.parse("text/plain"), etDeskripsiProduk.getText().toString());
                    RequestBody harga = RequestBody.create(MediaType.parse("text/plain"), etHargaProduk.getText().toString());
                    RequestBody namatoko = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("namatoko"));
                    RequestBody fototoko = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("fototoko"));
                    RequestBody alamattoko = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("alamattoko"));
                    RequestBody kecamatan = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("kec"));
                    RequestBody kabupaten = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("kab"));
                    RequestBody tahunusaha = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("tahuntoko"));
                    RequestBody sosmed = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("sosmed"));
                    RequestBody whatsapp = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("wa"));
                    RequestBody email = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("ml"));

                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<GetMenu> call = apiInterface.insertProduk(idkategori, idtoko, kategori, produk, kodeproduk, stok, deskripsi, gambar, harga, namatoko, fototoko, tahunusaha, alamattoko, kecamatan, kabupaten, sosmed, whatsapp, email);
                    call.enqueue(new Callback<GetMenu>() {
                        @Override
                        public void onResponse(Call<GetMenu> call, Response<GetMenu> response) {
                            if (response.isSuccessful()) {
//                                Intent nwintent = new Intent(InsertProduk.this, Langganan.class);
//                                startActivity(nwintent);
                            } else {
                                Toast.makeText(InsertProduk.this, "Masukkan Gambar Produk", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetMenu> call, Throwable t) {
                            Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                            //Log.d("RETRO", "ON FAILURE : " + t.getCause());
//                            Toast.makeText(getApplicationContext(), "Error, image", Toast.LENGTH_LONG).show();
                            Intent nwintent = new Intent(InsertProduk.this, Toko.class);
                            startActivity(nwintent);
                        }
                    });
                } else if (spinner2.getSelectedItem().toString().matches("Herbal")){
                    Integer idktgr = 4;
                    RequestBody idkategori = RequestBody.create(MediaType.parse("text/plain"), idktgr.toString());
                    RequestBody idtoko = RequestBody.create(MediaType.parse("text/plain"), id.toString());
                    RequestBody kategori = RequestBody.create(MediaType.parse("text/plain"), spinner2.getSelectedItem().toString());
                    RequestBody produk = RequestBody.create(MediaType.parse("text/plain"), etNamaProduk.getText().toString());
                    RequestBody kodeproduk = RequestBody.create(MediaType.parse("text/plain"), etKodeBarang.getText().toString());
                    RequestBody stok = RequestBody.create(MediaType.parse("text/plain"), etStokProduk.getText().toString());
                    RequestBody deskripsi = RequestBody.create(MediaType.parse("text/plain"), etDeskripsiProduk.getText().toString());
                    RequestBody harga = RequestBody.create(MediaType.parse("text/plain"), etHargaProduk.getText().toString());
                    RequestBody namatoko = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("namatoko"));
                    RequestBody fototoko = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("fototoko"));
                    RequestBody alamattoko = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("alamattoko"));
                    RequestBody kecamatan = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("kec"));
                    RequestBody kabupaten = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("kab"));
                    RequestBody tahunusaha = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("tahuntoko"));
                    RequestBody sosmed = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("sosmed"));
                    RequestBody whatsapp = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("wa"));
                    RequestBody email = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("ml"));

                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<GetMenu> call = apiInterface.insertProduk(idkategori, idtoko, kategori, produk, kodeproduk, stok, deskripsi, gambar, harga, namatoko, fototoko, tahunusaha, alamattoko, kecamatan, kabupaten, sosmed, whatsapp, email);
                    call.enqueue(new Callback<GetMenu>() {
                        @Override
                        public void onResponse(Call<GetMenu> call, Response<GetMenu> response) {
                            if (response.isSuccessful()) {
//                                Intent nwintent = new Intent(InsertProduk.this, Langganan.class);
//                                startActivity(nwintent);
                            } else {
                                Toast.makeText(InsertProduk.this, "Masukkan Gambar Produk", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetMenu> call, Throwable t) {
                            Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                            //Log.d("RETRO", "ON FAILURE : " + t.getCause());
//                            Toast.makeText(getApplicationContext(), "Error, image", Toast.LENGTH_LONG).show();
                            Intent nwintent = new Intent(InsertProduk.this, Toko.class);
                            startActivity(nwintent);
                        }
                    });
                } else if (spinner2.getSelectedItem().toString().matches("Lainnya")){
                    Integer idktgr = 5;
                    RequestBody idkategori = RequestBody.create(MediaType.parse("text/plain"), idktgr.toString());
                    RequestBody idtoko = RequestBody.create(MediaType.parse("text/plain"), id.toString());
                    RequestBody kategori = RequestBody.create(MediaType.parse("text/plain"), spinner2.getSelectedItem().toString());
                    RequestBody produk = RequestBody.create(MediaType.parse("text/plain"), etNamaProduk.getText().toString());
                    RequestBody kodeproduk = RequestBody.create(MediaType.parse("text/plain"), etKodeBarang.getText().toString());
                    RequestBody stok = RequestBody.create(MediaType.parse("text/plain"), etStokProduk.getText().toString());
                    RequestBody deskripsi = RequestBody.create(MediaType.parse("text/plain"), etDeskripsiProduk.getText().toString());
                    RequestBody harga = RequestBody.create(MediaType.parse("text/plain"), etHargaProduk.getText().toString());
                    RequestBody namatoko = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("namatoko"));
                    RequestBody fototoko = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("fototoko"));
                    RequestBody alamattoko = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("alamattoko"));
                    RequestBody kecamatan = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("kec"));
                    RequestBody kabupaten = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("kab"));
                    RequestBody tahunusaha = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("tahuntoko"));
                    RequestBody sosmed = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("sosmed"));
                    RequestBody whatsapp = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("wa"));
                    RequestBody email = RequestBody.create(MediaType.parse("text/plain"), mIntent.getStringExtra("ml"));

                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<GetMenu> call = apiInterface.insertProduk(idkategori, idtoko, kategori, produk, kodeproduk, stok, deskripsi, gambar, harga, namatoko, fototoko, tahunusaha, alamattoko, kecamatan, kabupaten, sosmed, whatsapp, email);
                    call.enqueue(new Callback<GetMenu>() {
                        @Override
                        public void onResponse(Call<GetMenu> call, Response<GetMenu> response) {
                            if (response.isSuccessful()) {
//                                Intent nwintent = new Intent(InsertProduk.this, Langganan.class);
//                                startActivity(nwintent);
                            } else {
                                Toast.makeText(InsertProduk.this, "Masukkan Gambar Produk", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetMenu> call, Throwable t) {
                            Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                            //Log.d("RETRO", "ON FAILURE : " + t.getCause());
//                            Toast.makeText(getApplicationContext(), "Error, image", Toast.LENGTH_LONG).show();
                            Intent nwintent = new Intent(InsertProduk.this, Toko.class);
                            startActivity(nwintent);
                        }
                    });
                }
            }
        }
    }

    public void btnInsertProduk(View view) {
        etNamaProduk = findViewById(R.id.etNamaProduk);
        spinner2 = findViewById(R.id.spinner2);
        etDeskripsiProduk = findViewById(R.id.etDeskripsiProduk);
        etKodeBarang = findViewById(R.id.etKodeBarang);
        etStokProduk = findViewById(R.id.etStokProduk);
        etHargaProduk = findViewById(R.id.etHargaProduk);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login", "");

        if (json != "") {
            loginModel = gson.fromJson(json, LoginModel.class);

            if (etNamaProduk.length() == 0){
                etNamaProduk.setError("Isi Nama Produk");
            } else if (etDeskripsiProduk.length() == 0){
                etDeskripsiProduk.setError("Isi Deskripsi Produk");
            } else if (etDeskripsiProduk.length() <= 29){
                etDeskripsiProduk.setError("Deskripsi minimal 30 karakter");
            } else if (etKodeBarang.length() == 0){
                etKodeBarang.setError("Isi Kode Produkmu sendiri");
            } else if (!etKodeBarang.getText().toString().matches("^[a-z,A-Z,1-9\\s]+$")){
                etKodeBarang.setError("Hanya huruf dan angka");
            }else if (etStokProduk.length() == 0){
                etStokProduk.setError("Isi Stok Produk");
            } else if (etHargaProduk.length() == 0){
                etHargaProduk.setError("Masukkan Harga Produk");
            } else {
                requestPermission();
            }
            return;
        }
    }
}