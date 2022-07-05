package com.suhailahnfsella.fudum;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOut extends AppCompatActivity {
    Dialog dialog;
    public static CheckOut ma;
    EditText etNamaPenerima, etAlamatPenerima, etJumlahProduk;
    LoginModel loginModel;
    SharedPreferences sharedPreferences,sharedPreferences1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        TextView tvNamaToko, tvJudulProduk, tvKategoriProduk, tvRp, tvKodeProduk;
        ImageView imgToko, imgProduk1;
        ApiInterface mApiInterface;
        ma=this;

        getSupportActionBar().setTitle("Pemesanan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientku));
        }

        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        Intent mIntent = getIntent();

        tvNamaToko = (TextView) findViewById(R.id.tvNamaToko);
        tvJudulProduk = (TextView) findViewById(R.id.tvNamaUser);
        tvKategoriProduk = (TextView) findViewById(R.id.tvNamaPjg);
        tvRp = (TextView) findViewById(R.id.tvRp);
        tvKodeProduk = (TextView) findViewById(R.id.tvKodeProduk);
        imgProduk1 = (ImageView) findViewById(R.id.imgProfil);
        imgToko = (ImageView) findViewById(R.id.btnInsertImgToko);

        tvNamaToko.setText(mIntent.getStringExtra("Owner"));
        tvJudulProduk.setText(mIntent.getStringExtra("Produk"));
        tvKategoriProduk.setText(mIntent.getStringExtra("Kategori"));
        tvRp.setText(String.valueOf(mIntent.getIntExtra("Rp",0)));
        tvKodeProduk.setText(mIntent.getStringExtra("KodeProduk"));
        Glide.with(CheckOut.this)
                .load("" + mIntent.getStringExtra("GambarProduk"))
                .apply(new RequestOptions().override(0, 200))
                .into(imgProduk1);
        Glide.with(CheckOut.this)
                .load("" + mIntent.getStringExtra("GambarToko"))
                .apply(new RequestOptions().override(0, 200))
                .into(imgToko);

        etNamaPenerima = (EditText) findViewById(R.id.etNamaPenerima);
        etAlamatPenerima = (EditText) findViewById(R.id.etAlamatPenerima);
        etJumlahProduk = (EditText) findViewById(R.id.etJumlahProduk);

        Button btAlert = findViewById(R.id.btnPeriksaDetail);
        btAlert.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (etNamaPenerima.length() < 1) {
                    etNamaPenerima.setError("Nama Penerima harus diisi!");
                } else if (!etNamaPenerima.getText().toString().matches("^[a-z,A-Z,'\\s]+$")) {
                    etNamaPenerima.setError("Isi dengan nama yang benar!");
                } else if (etAlamatPenerima.length() < 1){
                    etAlamatPenerima.setError("Alamat harus diisi!");
                } else if (etJumlahProduk.length() < 1){
                    etJumlahProduk.setError("Jumlah Produk harus ada!");
                } else {
                    openDialog();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void openDialog(){
        dialog = new Dialog(this);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog_detail);

        // EditText nya
        etNamaPenerima = findViewById(R.id.etNamaPenerima);
        etAlamatPenerima = findViewById(R.id.etAlamatPenerima);
        etJumlahProduk = findViewById(R.id.etJumlahProduk);

        //TextView nya
        TextView tvpenerim, tvalama, tvjumla, tvkod, tvtotal;

        tvpenerim = dialog.findViewById(R.id.tvpenerimadialog);
        tvalama = dialog.findViewById(R.id.tvalamat);
        tvjumla = dialog.findViewById(R.id.tvjumlah);
        tvkod = dialog.findViewById(R.id.tvkode);
        tvtotal = dialog.findViewById(R.id.tvtotal);

        //
        Intent intent = getIntent();
        Integer a = intent.getIntExtra("Rp",0);
        Integer b = Integer.parseInt(etJumlahProduk.getText().toString());

        Integer c = a * b;

        //Set TextViewnya
        tvpenerim.setText(etNamaPenerima.getText().toString());
        tvalama.setText(etAlamatPenerima.getText().toString());
        tvjumla.setText(etJumlahProduk.getText().toString());
        tvkod.setText(intent.getStringExtra("KodeProduk"));
        tvtotal.setText(c.toString());

        Button btnok = dialog.findViewById(R.id.btnok);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
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

    public void btnemailtoko(View view) {
        Intent intent = getIntent();
        String email = intent.getStringExtra("Email");

        String[] recipient = email.split(",");

        String message = "Nama Penerima : "+etNamaPenerima.getText().toString();
        String message2 = "Alamat Penerima : "+etAlamatPenerima.getText().toString();
        String message3 = "Jumlah Pesanan : "+etJumlahProduk.getText().toString();
        String message4 = "Kode Produk : "+intent.getStringExtra("KodeProduk");

        String jumlah = etJumlahProduk.getText().toString();

        Integer a = intent.getIntExtra("Rp",0);
        Integer b = Integer.parseInt(jumlah);

        Integer c = a * b;

        String message5 = "Total Harga : "+c;

        if (etNamaPenerima.length() < 1) {
            etNamaPenerima.setError("Nama Penerima harus diisi!");
        } else if (!etNamaPenerima.getText().toString().matches("^[a-z,A-Z,'\\s]+$")) {
            etNamaPenerima.setError("Isi dengan nama yang benar!");
        } else if (etAlamatPenerima.length() < 1){
            etAlamatPenerima.setError("Alamat harus diisi!");
        } else if (etJumlahProduk.length() < 1){
            etJumlahProduk.setError("Jumlah Produk harus ada!");
        } else {
            hg();

            Intent intent1 = new Intent(Intent.ACTION_SENDTO);
//            Intent hg = new Intent(CheckOut.this,HistoryOrderan.class);
            intent1.setData(Uri.parse("mailto:"));
            intent1.putExtra(Intent.EXTRA_EMAIL,recipient);
            intent1.putExtra(Intent.EXTRA_SUBJECT, "PESANAN FUDUM");
            intent1.putExtra(Intent.EXTRA_TEXT,message+"\n\n"+message2+"\n\n"+message3+"\n\n"+message4+"\n\n"+message5);

            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent1,"Chose an email"));
        }
    }

    private void hg() {
        Intent gf = getIntent();
        String gh = gf.getStringExtra("KodeProduk");
        Integer fl = gf.getIntExtra("IdToko",0);

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("login","");

            if (json != "") {
                loginModel = gson.fromJson(json, LoginModel.class);
                Integer jf = loginModel.getId();

                RequestBody namapenerima = RequestBody.create(MediaType.parse("text/plain"),etNamaPenerima.getText().toString());
                RequestBody alamatpenerima  = RequestBody.create(MediaType.parse("text/plain"),etAlamatPenerima.getText().toString());
                RequestBody jumlahpesanan = RequestBody.create(MediaType.parse("text/plain"),etJumlahProduk.getText().toString().trim());
                RequestBody kodeproduk = RequestBody.create(MediaType.parse("text/plain"),gh.trim());
                RequestBody idtoko = RequestBody.create(MediaType.parse("text/plain"),fl.toString());
                RequestBody iduser = RequestBody.create(MediaType.parse("text/plain"),jf.toString());
                RequestBody via = RequestBody.create(MediaType.parse("text/plain"), "DIPESAN MELALUI EMAIL");


                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<GetDataHistory> call = apiInterface.createH(iduser,idtoko,namapenerima,alamatpenerima,kodeproduk,jumlahpesanan,via);
                call.enqueue(new Callback<GetDataHistory>() {
                    @Override
                    public void onResponse(Call<GetDataHistory> call, Response<GetDataHistory> response) {
//                        Toast.makeText(CheckOut.this, "berhasil", Toast.LENGTH_SHORT).show();
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

    public void btnWa(View view) {
        boolean installed = appInstalledOrNot("com.whatsapp");

        Intent intent = getIntent();
        String noWa = intent.getStringExtra("Whatsapp");

        String message = "Nama Penerima : "+etNamaPenerima.getText().toString();
        String message2 = "Alamat Penerima : "+etAlamatPenerima.getText().toString();
        String message3 = "Jumlah Pesanan : "+etJumlahProduk.getText().toString();
        String message4 = "Kode Produk : "+intent.getStringExtra("KodeProduk");

        String jumlah = etJumlahProduk.getText().toString();

        Integer a = intent.getIntExtra("Rp",0);
        Integer b = Integer.parseInt(jumlah);

        Integer c = a * b;

        String message5 = "Total Harga : "+c;

        if (etNamaPenerima.length() < 1) {
            etNamaPenerima.setError("Nama Penerima harus diisi!");
        } else if (!etNamaPenerima.getText().toString().matches("^[a-z,A-Z,'\\s]+$")) {
            etNamaPenerima.setError("Isi dengan nama yang benar!");
        } else if (etAlamatPenerima.length() < 1){
            etAlamatPenerima.setError("Alamat harus diisi!");
        } else if (etJumlahProduk.length() < 1){
            etJumlahProduk.setError("Jumlah Produk harus ada!");
        } else {
            hw();

            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            intent1.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+62"+noWa+"&text="+"*PESANAN FUDUM*"+"\n\n"+message+"\n"+message2+"\n"+message3+"\n"+message4+"\n"+message5));
            startActivity(intent1);
        }
    }

        private void hw() {
            Intent gf = getIntent();
            String gh = gf.getStringExtra("KodeProduk");
            Integer fl = gf.getIntExtra("IdToko",0);

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("login","");

            if (json != "") {
                loginModel = gson.fromJson(json, LoginModel.class);
                Integer jf = loginModel.getId();

                RequestBody namapenerima = RequestBody.create(MediaType.parse("text/plain"),etNamaPenerima.getText().toString());
                RequestBody alamatpenerima  = RequestBody.create(MediaType.parse("text/plain"),etAlamatPenerima.getText().toString());
                RequestBody jumlahpesanan = RequestBody.create(MediaType.parse("text/plain"),etJumlahProduk.getText().toString().trim());
                RequestBody kodeproduk = RequestBody.create(MediaType.parse("text/plain"),gh.trim());
                RequestBody idtoko = RequestBody.create(MediaType.parse("text/plain"),fl.toString());
                RequestBody iduser = RequestBody.create(MediaType.parse("text/plain"),jf.toString());
                RequestBody via = RequestBody.create(MediaType.parse("text/plain"), "DIPESAN MELALUI WHATSAPP");


                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<GetDataHistory> call = apiInterface.createH(iduser,idtoko,namapenerima,alamatpenerima,kodeproduk,jumlahpesanan,via);
                call.enqueue(new Callback<GetDataHistory>() {
                    @Override
                    public void onResponse(Call<GetDataHistory> call, Response<GetDataHistory> response) {
//                        Toast.makeText(CheckOut.this, "berhasil", Toast.LENGTH_SHORT).show();
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
        }
}