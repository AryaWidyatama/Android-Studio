package com.komputerkit.komen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahProfil extends AppCompatActivity {

    ImageView btnUbahFotoP1,tvUbah1;
    ProgressDialog progressBar;
    TextView tvNamaUser;
    EditText etUbahNamaP, etUbahNoP, etUbahEmailP, etUbahPwP;
    Button btnSimpan, btnLogout;
    SiswaModel loginModel;
    Dialog dialog;
    AppCompatSpinner spinnerkelas,spinnerjurusan;
    SharedPreferences sharedPreferences,sharedPreferences1;
    private String mediaPath;
    private String postPath;

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
        setContentView(R.layout.activity_ubah_profil);

        spinnerkelas = findViewById(R.id.spKelas);
        spinnerjurusan = findViewById(R.id.spJurusan);

       getSupportActionBar().hide();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.limedash)));
        }

        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(this.getResources().getColor(R.color.limedash));








        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

        loginModel = gson.fromJson(json,SiswaModel.class);

        btnUbahFotoP1 = findViewById(R.id.btnUbahFotoP);
        tvNamaUser = findViewById(R.id.tvNamaUser);
        tvUbah1 = findViewById(R.id.tvUbah);

        etUbahNamaP = findViewById(R.id.etUbahNamaP);
        etUbahNoP = findViewById(R.id.etUbahNoP);
        etUbahEmailP = findViewById(R.id.etUbahEmailP);
        etUbahPwP = findViewById(R.id.etUbahPwP);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(UbahProfil.this);
//                Gson gson = new Gson();
//                String json = sharedPreferences.getString("login","");
                progressBar = new ProgressDialog(UbahProfil.this);
                progressBar.show();
                progressBar.setContentView(R.layout.progres_dialog);
                progressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                loginModel = gson.fromJson(json,SiswaModel.class);
//
//                if (loginModel.getKelas().equals("Guru")){
//                    saveImageUpload1();
//                }else {
//                    saveImageUpload();
//                }

                saveImageUpload();



            }
        });
        btnLogout = findViewById(R.id.btnLogout);


        String Kelas = loginModel.getKelas();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.daftar_Kelas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerkelas.setAdapter(adapter);
        if (Kelas != null) {
            int spinnerPosition = adapter.getPosition(Kelas);
            spinnerkelas.setSelection(spinnerPosition);
        }

        String Jurusan = loginModel.getJurusan();
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.daftar_jurusan, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerjurusan.setAdapter(adapter1);
        if (Jurusan != null) {
            int spinnerPosition = adapter1.getPosition(Jurusan);
            spinnerjurusan.setSelection(spinnerPosition);
        }



        tvNamaUser.setText(loginModel.getNamaLengkap());
        etUbahNamaP.setText(loginModel.getUsername());
        etUbahNoP.setText(loginModel.getNoTelp());
        etUbahEmailP.setText(loginModel.getEmail());
        etUbahPwP.setText(loginModel.getPassword());
//        spinnerkelas.setTag(loginModel.getKelas());
//        spinnerjurusan.setTag(loginModel.getJurusan());
        Glide.with(UbahProfil.this)
                .load("" + loginModel.getFotoSiswa())
                .apply(new RequestOptions().override(0, 200))
                .into(btnUbahFotoP1);


        btnUbahFotoP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent galleryIntent = new Intent();
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

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    btnUbahFotoP1.setImageURI(data.getData());
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
                loginModel = gson.fromJson(json, SiswaModel.class);
//                String namatoko1 = loginModel.getLevel();
                Intent mIntent = getIntent();
                Integer id = loginModel.getId();
                String nama = loginModel.getNamaLengkap();
                RequestBody NamaLengkap = RequestBody.create(MediaType.parse("text/plain"), nama.trim());
                RequestBody Username = RequestBody.create(MediaType.parse("text/plain"), etUbahNamaP.getText().toString().trim());
                RequestBody Email = RequestBody.create(MediaType.parse("text/plain"), etUbahEmailP.getText().toString().trim());
                RequestBody Kelas = RequestBody.create(MediaType.parse("text/plain"), spinnerkelas.getSelectedItem().toString().trim());
                RequestBody Jurusan = RequestBody.create(MediaType.parse("text/plain"), spinnerjurusan.getSelectedItem().toString().trim());
                RequestBody NoTelp = RequestBody.create(MediaType.parse("text/plain"), etUbahNoP.getText().toString().trim());
                RequestBody Password = RequestBody.create(MediaType.parse("text/plain"), etUbahPwP.getText().toString().trim());


                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<GetSiswa> call = apiInterface.addMahasiswanoimage(id,NamaLengkap, Username, Email, Kelas, Jurusan,NoTelp,Password);
                call.enqueue(new Callback<GetSiswa>() {
                    @Override
                    public void onResponse(Call<GetSiswa> call, Response<GetSiswa> response) {
                        if (response.isSuccessful()) {
                            NewDataPesan registerModel= new NewDataPesan();


                            registerModel.setKelasPengguna(spinnerkelas.getSelectedItem().toString());
                            registerModel.setJurusanPengguna(spinnerjurusan.getSelectedItem().toString());

                            Register(registerModel);

                            Intent intent = new Intent(UbahProfil.this, Login.class);
                            startActivity(intent);
                            progressBar.dismiss();

                            finish();
//                        PenjualActivity;

                        }



                    }

                    @Override
                    public void onFailure(Call<GetSiswa> call, Throwable t) {
                        progressBar.dismiss();
                        Toast.makeText(UbahProfil.this, "Harus ada data yang diubah!", Toast.LENGTH_SHORT).show();
                    }
                });
            }


        }
        else {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("login","");
            if (json != "") {
                loginModel = gson.fromJson(json, SiswaModel.class);
             //   String namatoko1 = login.getLevel();
                Intent mIntent = getIntent();
                Integer id = loginModel.getId();
                String nama = loginModel.getNamaLengkap();

                File imagefile = new File(mediaPath);
                RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                MultipartBody.Part gambar = MultipartBody.Part.createFormData("FotoSiswa", imagefile.getName(), reqBody);
                RequestBody NamaLengkap = RequestBody.create(MediaType.parse("text/plain"), nama.trim());
                RequestBody Username = RequestBody.create(MediaType.parse("text/plain"), etUbahNamaP.getText().toString().trim());
                RequestBody Email = RequestBody.create(MediaType.parse("text/plain"), etUbahEmailP.getText().toString().trim());
                RequestBody Kelas = RequestBody.create(MediaType.parse("text/plain"), spinnerkelas.getSelectedItem().toString().trim());
                RequestBody Jurusan = RequestBody.create(MediaType.parse("text/plain"), spinnerjurusan.getSelectedItem().toString().trim());
                RequestBody NoTelp = RequestBody.create(MediaType.parse("text/plain"), etUbahNoP.getText().toString().trim());
                RequestBody Password = RequestBody.create(MediaType.parse("text/plain"), etUbahPwP.getText().toString().trim());


                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<GetSiswa> call = apiInterface.addMahasiswa(id,NamaLengkap, Username, Email, Kelas, Jurusan,NoTelp,Password,gambar);
                call.enqueue(new Callback<GetSiswa>() {
                    @Override
                    public void onResponse(Call<GetSiswa> call, Response<GetSiswa> response) {
                        if (response.isSuccessful()) {
                            NewDataPesan registerModel= new NewDataPesan();

progressBar.dismiss();
                            registerModel.setKelasPengguna(spinnerkelas.getSelectedItem().toString());
                            registerModel.setJurusanPengguna(spinnerjurusan.getSelectedItem().toString());

                            Register(registerModel);

                            Intent intent = new Intent(UbahProfil.this, Login.class);
                            startActivity(intent);

                            finish();

                        }



                    }

                    @Override
                    public void onFailure(Call<GetSiswa> call, Throwable t) {
                        Toast.makeText(UbahProfil.this, "Harus ada data yang diubah!", Toast.LENGTH_SHORT).show();
                        progressBar.dismiss();
                    }
                });
            }

        }

    }


    private void saveImageUpload1() {

        //   final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if (mediaPath == null) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("login","");
            if (json != "") {
                loginModel = gson.fromJson(json, SiswaModel.class);
//                String namatoko1 = loginModel.getLevel();
                Intent mIntent = getIntent();
                Integer id = loginModel.getId();
                String nama = loginModel.getNamaLengkap();
                RequestBody NamaLengkap = RequestBody.create(MediaType.parse("text/plain"), nama.trim());
                RequestBody Username = RequestBody.create(MediaType.parse("text/plain"), etUbahNamaP.getText().toString().trim());
                RequestBody Email = RequestBody.create(MediaType.parse("text/plain"), etUbahEmailP.getText().toString().trim());
                RequestBody Kelas = RequestBody.create(MediaType.parse("text/plain"), spinnerkelas.getSelectedItem().toString().trim());
                RequestBody Jurusan = RequestBody.create(MediaType.parse("text/plain"), spinnerjurusan.getSelectedItem().toString().trim());
                RequestBody NoTelp = RequestBody.create(MediaType.parse("text/plain"), etUbahNoP.getText().toString().trim());
                RequestBody Password = RequestBody.create(MediaType.parse("text/plain"), etUbahPwP.getText().toString().trim());


                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<GetSiswa> call = apiInterface.updategurunoimage(id,NamaLengkap, Username, Email, Kelas, Jurusan,NoTelp,Password);
                call.enqueue(new Callback<GetSiswa>() {
                    @Override
                    public void onResponse(Call<GetSiswa> call, Response<GetSiswa> response) {
                        if (response.isSuccessful()) {
                            NewDataPesan registerModel= new NewDataPesan();


                            registerModel.setKelasPengguna(spinnerkelas.getSelectedItem().toString());
                            registerModel.setJurusanPengguna(spinnerjurusan.getSelectedItem().toString());

                            Register(registerModel);

                            Intent intent = new Intent(UbahProfil.this, Login.class);
                            startActivity(intent);
                            progressBar.dismiss();

                            finish();
//                        PenjualActivity;

                        }



                    }

                    @Override
                    public void onFailure(Call<GetSiswa> call, Throwable t) {
                        progressBar.dismiss();
                        Toast.makeText(UbahProfil.this, "Harus ada data yang diubah!", Toast.LENGTH_SHORT).show();
                    }
                });
            }


        }
        else {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("login","");
            if (json != "") {
                loginModel = gson.fromJson(json, SiswaModel.class);
                //   String namatoko1 = login.getLevel();
                Intent mIntent = getIntent();
                Integer id = loginModel.getId();
                String nama = loginModel.getNamaLengkap();

                File imagefile = new File(mediaPath);
                RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                MultipartBody.Part gambar = MultipartBody.Part.createFormData("FotoSiswa", imagefile.getName(), reqBody);
                RequestBody NamaLengkap = RequestBody.create(MediaType.parse("text/plain"), nama.trim());
                RequestBody Username = RequestBody.create(MediaType.parse("text/plain"), etUbahNamaP.getText().toString().trim());
                RequestBody Email = RequestBody.create(MediaType.parse("text/plain"), etUbahEmailP.getText().toString().trim());
                RequestBody Kelas = RequestBody.create(MediaType.parse("text/plain"), spinnerkelas.getSelectedItem().toString().trim());
                RequestBody Jurusan = RequestBody.create(MediaType.parse("text/plain"), spinnerjurusan.getSelectedItem().toString().trim());
                RequestBody NoTelp = RequestBody.create(MediaType.parse("text/plain"), etUbahNoP.getText().toString().trim());
                RequestBody Password = RequestBody.create(MediaType.parse("text/plain"), etUbahPwP.getText().toString().trim());


                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<GetSiswa> call = apiInterface.updateguru(id,NamaLengkap, Username, Email, Kelas, Jurusan,NoTelp,Password,gambar);
                call.enqueue(new Callback<GetSiswa>() {
                    @Override
                    public void onResponse(Call<GetSiswa> call, Response<GetSiswa> response) {
                        if (response.isSuccessful()) {
                            NewDataPesan registerModel= new NewDataPesan();

                            progressBar.dismiss();
                            registerModel.setKelasPengguna(spinnerkelas.getSelectedItem().toString());
                            registerModel.setJurusanPengguna(spinnerjurusan.getSelectedItem().toString());

                            Register(registerModel);

                            Intent intent = new Intent(UbahProfil.this, Login.class);
                            startActivity(intent);

                            finish();

                        }



                    }

                    @Override
                    public void onFailure(Call<GetSiswa> call, Throwable t) {
                        Toast.makeText(UbahProfil.this, "Harus ada data yang diubah!", Toast.LENGTH_SHORT).show();
                        progressBar.dismiss();
                    }
                });
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

    public void keluar(View view) {
        dialog=new Dialog(UbahProfil.this);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_logout);



        Button btnoke=dialog.findViewById(R.id.btnok);
        btnoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(UbahProfil.this,Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                //  finish();



            }
        });

        Button btntdk=dialog.findViewById(R.id.btntdk);
        btntdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //  finish();



            }
        });
        dialog.show();

    }

    public void Register(NewDataPesan newPPassword){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");
        if (json != "") {
            loginModel = gson.fromJson(json, SiswaModel.class);
            //   String namatoko1 = login.getLevel();
            Intent mIntent = getIntent();
            Integer id = loginModel.getId();



            Call<GetNewDataPengguna> registerCall = ApiClient.getApi().NewDataPesan(id, newPPassword);
            registerCall.enqueue(new Callback<GetNewDataPengguna>() {
                @Override
                public void onResponse(Call<GetNewDataPengguna> call, Response<GetNewDataPengguna> response) {
                    if (response.isSuccessful()) {


                    } else {
                        String pesan1 = "Harus Diisi!";
                        Toast.makeText(UbahProfil.this, pesan1, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GetNewDataPengguna> call, Throwable t) {
                    Toast.makeText(UbahProfil.this, "Harus DiIsi", Toast.LENGTH_SHORT).show();
                }
            });

        }


    }
}