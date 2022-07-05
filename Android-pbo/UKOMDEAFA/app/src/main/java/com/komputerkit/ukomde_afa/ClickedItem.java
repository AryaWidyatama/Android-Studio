package com.komputerkit.ukomde_afa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
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

import static java.lang.Short.SIZE;

public class ClickedItem extends AppCompatActivity {

    ImageView imageView,b2;
    TextView textView,tvCode,tvHarga,tvDesc,tvEmail,tvNomor,user1,namatoko;
    private ImageButton imageButton;
    Login getLogin;
    Button b1;
    ProgressDialog progressBar;

    SharedPreferences sharedPreferences;
    ApiMenuInterface mApiInterface;
    private static final String SHARED_PREF_NAME = "";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_item);
        getSupportActionBar().hide();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");



        imageView = findViewById(R.id.gridimage);
        textView = findViewById(R.id.item_name);
        tvCode = findViewById(R.id.tvCode);
        tvHarga = findViewById(R.id.Harga);
        tvDesc = findViewById(R.id.Descrip);
        tvEmail = findViewById(R.id.tvPasswordDaftaar);
        tvNomor = findViewById(R.id.tvNomor);
        namatoko = findViewById(R.id.namatoko);
        user1 = findViewById(R.id.usernameClick);
        b2 = findViewById(R.id.watanya);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                if (intent.getExtras() != null) {
                    boolean installed = appInstalledOrNot("com.whatsapp");
                    if (json != "" && installed) {
                        getLogin = gson.fromJson(json, Login.class);

                        String selectedEmail = intent.getStringExtra("email");
                        String selectedName = intent.getStringExtra("name");
                        String kode = intent.getStringExtra("kode");

                        String Username = getLogin.getLevel();
                        Intent intent1 = new Intent(Intent.ACTION_VIEW);
//                        intent1.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + "+62" + selectedEmail + "&text=" + "Nama Pengguna = " + Username + "\n" + "- Judul Barang : "+ selectedName  + "\n" + "- Kode Barang : "+ kode + "\n" + "- Apakah barang ini masih tersedia? "));
//                        startActivity(intent1);


                        Toast.makeText(ClickedItem.this, "Berhasil", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ClickedItem.this, "Belum Instal Wa", Toast.LENGTH_SHORT).show();
//                        Intent mIntent = new Intent(ClickedItem.this, HomeActivity.class);
//                        startActivity(mIntent);

                        Intent mIntent = new Intent(ClickedItem.this, RegisterChat.class);
                        startActivity(mIntent);
                    }
                }
            }
        });

        if (json != ""){
            getLogin = gson.fromJson(json,Login.class);

            String Username = getLogin.getLevel();

            user1.setText(json);
        }


        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backActivity();
            }
        });

        Intent intent = getIntent();

        if (intent.getExtras() != null){
            String selectedName = intent.getStringExtra("name");
            String selectedCode = intent.getStringExtra("kode");
            String selectedHarga = intent.getStringExtra("harga");
            String setNomerWa = intent.getStringExtra("nomor");
            String nama_toko = intent.getStringExtra("namatoko");
            String selectedDesc = intent.getStringExtra("desc");
            String selectedEmail = intent.getStringExtra("email");
            String selectedImage = intent.getStringExtra("gambar");

            tvCode.setText(selectedCode);
            textView.setText(selectedName);
//            imageView.setImageResource(selectedImage);
            Glide.with(this)
                    .load(selectedImage)
                   .apply(new RequestOptions().override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL))
                    .into(imageView);
            tvHarga.setText(selectedHarga);
            tvDesc.setText(selectedDesc);
            tvEmail.setText(selectedEmail);
            tvNomor.setText(setNomerWa);
            namatoko.setText(nama_toko);

            Button b1;
            b1 = findViewById(R.id.btnWa);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    progressBar = new ProgressDialog(ClickedItem.this);
//                    progressBar.show();
//                    progressBar.setContentView(R.layout.progres_dialog);
//                    progressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                    String nomor = tvNomor.getText().toString();
//                    String deskripsi = tvDesc.getText().toString();
//                    String barang = textView.getText().toString();
//                    String harga = tvHarga.getText().toString();
//                    String code = tvCode.getText().toString();
//                    boolean installed = appInstalledOrNot("com.whatsapp");
                    Intent intent1 = new Intent(ClickedItem.this,Keterangan_Jumlah.class);
                    intent1.putExtra("harga",selectedHarga);
                    intent1.putExtra("name",selectedName);
                    intent1.putExtra("gambar",selectedImage);
                    intent1.putExtra("email",selectedEmail);
                    intent1.putExtra("kode",selectedCode);
                    intent1.putExtra("namatoko",nama_toko);

                    startActivity(intent1);
                   // updateImageUpload();

//                    if (installed){
//                        if (json != ""){
//                            getLogin = gson.fromJson(json,Login.class);
//
//                            String Username = getLogin.getLevel();
//                            Intent intent1 = new Intent(Intent.ACTION_VIEW);
//                            intent1.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+62881036507731"+nomor+"&text="+"Nama Pengguna = "+Username+"- Judul Barang : "+barang+"\n"+"- Deskripsi : "+deskripsi+"\n"+"- Kode Barang : "+code+"\n"+"- Harga : "+harga));
//                            startActivity(intent1);
//
//                        }
//
//                    }else {
//
//                        Toast.makeText(ClickedItem.this, "belum instal", Toast.LENGTH_SHORT).show();
//
//                    }

//                    if (json!=""){
//                        updateImageUpload();
//                    }

//                    if (json!="" && installed){
//                        getLogin = gson.fromJson(json,Login.class);
//
//                        updateImageUpload();
//                        String Username = getLogin.getLevel();
//                        Intent intent1 = new Intent(Intent.ACTION_VIEW);
//                        intent1.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+62"+selectedEmail+"&text="+"Nama Pengguna = "+Username+"\n"+"- Judul Barang : "+barang+"\n"+"- Deskripsi : "+deskripsi+"\n"+"- Kode Barang : "+code+"\n"+"- Harga : "+harga));
//                        startActivity(intent1);
//                        Toast.makeText(ClickedItem.this, "Berhasil", Toast.LENGTH_SHORT).show();
//                    }else {
//                        Toast.makeText(ClickedItem.this, "Belum Instal Wa", Toast.LENGTH_SHORT).show();
//                        Intent mIntent = new Intent(ClickedItem.this,HomeActivity.class);
//                        startActivity(mIntent);
//                    }
                }
            });
        }
    }

    private boolean appInstalledOrNot(String url){
        PackageManager packageManager = getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e){ ;
            app_installed = false;
        }
        return app_installed;
    }

    public void backActivity(){
        Intent intent = new Intent(this,HomeUtamaActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.kanan, R.anim.kanan1);



    }

//    private void updateImageUpload() {
//        Intent intent = getIntent();
//        if (intent.getExtras() != null) {
//            String selectedName = intent.getStringExtra("name");
//            String selectedCode = intent.getStringExtra("kode");
//            String nama_toko = intent.getStringExtra("namatoko");
//            String setNomerWa = intent.getStringExtra("harga");
//            String selectedDesc = intent.getStringExtra("desc");
//            String selectedEmail = intent.getStringExtra("email");
//            String selectedImage = intent.getStringExtra("gambar");
//            Integer hp = 1;
//
//            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//            Gson gson = new Gson();
//            String json = sharedPreferences.getString("login","");
//
//            if (json != "") {
//                getLogin = gson.fromJson(json, Login.class);
//                String nama = getLogin.getLevel();
//                String ye = getLogin.getApiToken();
//                Integer iduser1 = getLogin.getId();
//                RequestBody iduser = RequestBody.create(MediaType.parse("text/plain"),iduser1.toString());
//                RequestBody idkategori1 = RequestBody.create(MediaType.parse("text/plain"),nama);
//                RequestBody menu1 = RequestBody.create(MediaType.parse("text/plain"),selectedCode.trim());
//                RequestBody deskripsi1 = RequestBody.create(MediaType.parse("text/plain"),selectedName.trim());
//                RequestBody harga1 = RequestBody.create(MediaType.parse("text/plain"),selectedEmail.trim());
//                RequestBody kodetr = RequestBody.create(MediaType.parse("text/plain"),hp.toString());
//                RequestBody harga = RequestBody.create(MediaType.parse("text/plain"),setNomerWa.trim());
//                RequestBody namatoko = RequestBody.create(MediaType.parse("text/plain"),nama_toko.trim());
//                RequestBody kode1 =  RequestBody.create(MediaType.parse("text/plain"),ye);
//                RequestBody gambary =  RequestBody.create(MediaType.parse("text/plain"),selectedImage.trim());
//
//                ApiMenuInterface apiInterface = ApiClient.getClient().create(ApiMenuInterface.class);
//                Call<GetDataHistory> call = apiInterface.createH(iduser,idkategori1,menu1,kode1,harga1,deskripsi1,kodetr,harga,namatoko,gambary);
//                call.enqueue(new Callback<GetDataHistory>() {
//                    @Override
//                    public void onResponse(Call<GetDataHistory> call, Response<GetDataHistory> response) {
//
//                        Toast.makeText(ClickedItem.this, "berhasil", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//
//                    @Override
//                    public void onFailure(Call<GetDataHistory> call, Throwable t) {
//                        Log.d("RETRO", "ON FAILURE : " + t.getMessage());
//                        //Log.d("RETRO", "ON FAILURE : " + t.getCause());
//                        Toast.makeText(getApplicationContext(), "Error, image", Toast.LENGTH_LONG).show();
//                    }
//                });
//
//            }
//
//            Intent mIntent = getIntent();
//
//        }
//
//
//        }


}