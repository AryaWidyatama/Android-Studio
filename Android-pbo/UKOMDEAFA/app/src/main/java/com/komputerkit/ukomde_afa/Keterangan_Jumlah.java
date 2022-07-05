package com.komputerkit.ukomde_afa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Keterangan_Jumlah extends AppCompatActivity {
    private Button button,btn1;
    private ImageButton imageButton;
    ImageView imgdetail;
    ApiMenuInterface mApiInterface;
    String tittle,overview;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13;
    EditText h1,h2;
    dataMenu dataMenu;
    TextView up,down;
    private int idkategori;

    Login getLogin;
    ImageView gh;
    Login login;
    Dialog dialog;
    Intent mIntent = getIntent();
    int count = 1;
    int count1 = 1;
    int myNum = 0;


    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keterangan_jumlah);
        getSupportActionBar().hide();

        tv3 = (TextView) findViewById(R.id.tvnamatokorcv);
        tv1 = (TextView) findViewById(R.id.idKaaategoriUbah);
        h1 = (EditText) findViewById(R.id.jumlahharga);
        h2 = (EditText) findViewById(R.id.catatan);
        tv12 = (TextView) findViewById(R.id.jumlahharga);
        tv11 = (TextView) findViewById(R.id.totalharga);
        tv2 = (TextView) findViewById(R.id.KodeUbah);
        tv7 = (TextView) findViewById(R.id.hargahstr);
        tv8 = (TextView) findViewById(R.id.nomordetailhstr);
        tv4 = (TextView) findViewById(R.id.tvHargaDetailRcv);
        imgdetail = (ImageView) findViewById(R.id.imgDetailUbahrcv);
        Integer hua = Integer.parseInt(h1.getText().toString());

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

        if (json != "") {
            getLogin = gson.fromJson(json, Login.class);
            tv2.setText(getLogin.getLevel());
            tv4.setText(getLogin.getApiToken());
            tv8.setText(getLogin.getRelasi());
        }

        Intent intent4 = getIntent();
        if (intent4.getExtras() != null) {
            try {
                Integer selectedHarga = Integer.valueOf(intent4.getStringExtra("harga"));
                myNum = Integer.parseInt(h1.getText().toString());
                Integer hu = myNum*selectedHarga;
                tv11.setText(String.valueOf(hu));
            } catch (NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }
        }

        Intent intent = getIntent();
        if (intent.getExtras() != null){
            Integer selectedHarga = Integer.valueOf(intent.getStringExtra("harga"));
            String selectedName = intent.getStringExtra("name");
            String selectedCode = intent.getStringExtra("kode");
            String nama_toko = intent.getStringExtra("namatoko");
            String setNomerWa = intent.getStringExtra("harga");
            String selectedDesc = intent.getStringExtra("desc");
            String selectedEmail = intent.getStringExtra("email");
            String selectedImage = intent.getStringExtra("gambar");
            Integer hu = hua*selectedHarga;
            Glide.with(Keterangan_Jumlah.this)
                    .load("" + intent.getStringExtra("gambar"))
                    .apply(new RequestOptions().override(350, 550))
                    .into(imgdetail);
            tv1.setText(String.valueOf(intent.getStringExtra("name")));
          //  tv11.setText(String.valueOf(hu));
            tv7.setText(intent.getStringExtra("harga"));
            tv3.setText(intent.getStringExtra("namatoko"));
        }


        up = findViewById(R.id.ctrup);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = getIntent();
                if (intent.getExtras() != null){
                    count++;
                    Integer selectedHarga = Integer.valueOf(intent.getStringExtra("harga"));
                    Integer hu = count*selectedHarga;

                    tv11.setText(String.valueOf(hu));
                    h1.setText(Integer.toString(count));// view in the text
                    h1.setError(null);
                }


            }
        });
        down = findViewById(R.id.down);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                if (intent.getExtras() != null){
                    if (count<=count1){
                       h1.setError("tidak boleh kurang dari 1");
                    }else{
                        count--;
                        Integer selectedHarga = Integer.valueOf(intent.getStringExtra("harga"));
                        Integer hu = count*selectedHarga;

                        tv11.setText(String.valueOf(hu));
                        h1.setText(Integer.toString(count));// view in the text
                    }
                    return;

                }
            }
        });



        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backActivity();
            }
        });

        Integer hg = Integer.valueOf(h1.getText().toString());
        button = (Button) findViewById(R.id.btnoke);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog=new Dialog(Keterangan_Jumlah.this);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_checkout);

                Button btnoke=dialog.findViewById(R.id.btniya);
                btnoke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean installed = appInstalledOrNot("com.whatsapp");

                        if (json!="" && installed){
                            Integer selectedHarga = Integer.valueOf(intent.getStringExtra("harga"));
                            Integer hu = count*selectedHarga;
                            Integer hw = count;

                            String df = h2.getText().toString();
                            String selectedName = intent.getStringExtra("name");
                            String selectedCode = intent.getStringExtra("kode");
                            String nama_toko = intent.getStringExtra("namatoko");
                            String setNomerWa = intent.getStringExtra("harga");
                            String selectedDesc = intent.getStringExtra("desc");
                            String selectedEmail = intent.getStringExtra("email");
                            String selectedImage = intent.getStringExtra("gambar");
                            Integer hp = 1;

                            getLogin = gson.fromJson(json,Login.class);

                            updateImageUpload();
                            String Username = getLogin.getLevel();
                            Intent intent1 = new Intent(Intent.ACTION_VIEW);
                            intent1.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+62"+selectedEmail+"&text="+"Nama Pengguna = "+Username+"\n"+"- Judul Barang : "+selectedName+"\n"+"- Kode Barang : "+selectedCode+"\n"+"- Harga : "+setNomerWa+"\n"+"- Total : "+
                                    hu+"\n"+"- Keterangan : "+df));
                            startActivity(intent1);
                            Toast.makeText(Keterangan_Jumlah.this, "Berhasil", Toast.LENGTH_SHORT).show();
                        }else {
                           // updateImageUpload();
                            Toast.makeText(Keterangan_Jumlah.this, "Belum Instal Wa", Toast.LENGTH_SHORT).show();
                            Intent mIntent = new Intent(Keterangan_Jumlah.this,HomeActivity.class);
                            startActivity(mIntent);
                        }



                    }
                });
                Button btntidak=dialog.findViewById(R.id.btntidak);
                btntidak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });
                dialog.show();

                    }
                });




//                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                Gson gson = new Gson();
//                String json = sharedPreferences.getString("login", "");
//                if (json != "") {
//                    login = gson.fromJson(json, Login.class);
//
//                    int idKategori = login.getId();
//                    Intent intent = new Intent(getApplicationContext(), HIstoryFragment.class);
//                    intent.putExtra("idKategori", idKategori);
//                    overridePendingTransition(0, 0);
//                    startActivity(intent);
//                }
//                if (intent.getExtras() != null){
//                    Integer selectedHarga = Integer.valueOf(intent.getStringExtra("harga"));
//                    Integer hu = count*selectedHarga;
//                    Toast.makeText(Keterangan_Jumlah.this, ""+hu, Toast.LENGTH_SHORT).show();
//                }





    }

    public void backActivity(){
        finish();

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




    private void updateImageUpload() {
        final String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Integer selectedHarga = Integer.valueOf(intent.getStringExtra("harga"));
            Integer hu = count*selectedHarga;
            Integer hw = count;
            String df = h2.getText().toString();
            String selectedName = intent.getStringExtra("name");
            String selectedCode = intent.getStringExtra("kode");
            String nama_toko = intent.getStringExtra("namatoko");
            String setNomerWa = intent.getStringExtra("harga");
            String selectedDesc = intent.getStringExtra("desc");
            String selectedEmail = intent.getStringExtra("email");
            String selectedImage = intent.getStringExtra("gambar");
            Integer hp = 1;

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("login","");

            if (json != "") {
                getLogin = gson.fromJson(json, Login.class);
                String nama = getLogin.getLevel();
                String ye = getLogin.getApiToken();
                Integer iduser1 = getLogin.getId();
                RequestBody iduser = RequestBody.create(MediaType.parse("text/plain"),iduser1.toString());
                RequestBody idkategori1 = RequestBody.create(MediaType.parse("text/plain"),nama);
                RequestBody menu1 = RequestBody.create(MediaType.parse("text/plain"),selectedCode.trim());
                RequestBody deskripsi1 = RequestBody.create(MediaType.parse("text/plain"),selectedName.trim());
                RequestBody harga1 = RequestBody.create(MediaType.parse("text/plain"),selectedEmail.trim());
                RequestBody kodetr = RequestBody.create(MediaType.parse("text/plain"),hp.toString());
                RequestBody harga = RequestBody.create(MediaType.parse("text/plain"),setNomerWa.trim());
                RequestBody namatoko = RequestBody.create(MediaType.parse("text/plain"),nama_toko.trim());
                RequestBody total = RequestBody.create(MediaType.parse("text/plain"),hu.toString());
                RequestBody jumlah = RequestBody.create(MediaType.parse("text/plain"),hw.toString());
                RequestBody keterangan = RequestBody.create(MediaType.parse("text/plain"),df.trim());
                RequestBody tanggal = RequestBody.create(MediaType.parse("text/plain"),date.trim());
                RequestBody kode1 =  RequestBody.create(MediaType.parse("text/plain"),ye);
                RequestBody gambary =  RequestBody.create(MediaType.parse("text/plain"),selectedImage.trim());

                ApiMenuInterface apiInterface = ApiClient.getClient().create(ApiMenuInterface.class);
                Call<GetDataHistory> call = apiInterface.createH(iduser,idkategori1,menu1,kode1,harga1,deskripsi1,kodetr,harga,namatoko,total,jumlah,keterangan,tanggal,gambary);
                call.enqueue(new Callback<GetDataHistory>() {
                    @Override
                    public void onResponse(Call<GetDataHistory> call, Response<GetDataHistory> response) {

                        Toast.makeText(Keterangan_Jumlah.this, "berhasil", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<GetDataHistory> call, Throwable t) {
                        Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                        //Log.d("RETRO", "ON FAILURE : " + t.getCause());
                        Toast.makeText(getApplicationContext(), "Error, image", Toast.LENGTH_LONG).show();
                    }
                });

            }

            Intent mIntent = getIntent();

        }


    }



}