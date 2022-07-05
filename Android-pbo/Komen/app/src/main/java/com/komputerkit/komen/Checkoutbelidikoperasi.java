package com.komputerkit.komen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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

public class Checkoutbelidikoperasi extends AppCompatActivity {
    private Button button,btn1;
    private ImageButton imageButton;
    ImageView imgdetail;
    ProgressDialog progressBar;
    // ApiMenuInterface mApiInterface;
    String tittle,overview;
    TextView tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13;
    EditText h1,h2,tv1,tv2,tv3,jurusan;
    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";
    //dataMenu dataMenu;
    TextView up,down;
    private int idkategori;
    Dialog dialog;

    SiswaModel getLogin;
    ImageView gh;
    Login login;

    Intent mIntent = getIntent();
    int count = 1;
    int count1 = 1;
    int myNum = 0;
    ImageView btnBack,banner;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkoutbelidikoperasi);
        getSupportActionBar().setTitle("Buat Pesanan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(this.getResources().getColor(R.color.limedash));



        tv3 = (EditText) findViewById(R.id.ruangandituju);
        tv1 = (EditText) findViewById(R.id.namaanda);
        h1 = (EditText) findViewById(R.id.jumlahharga);

        h1.setFocusable(false);
        h1.setClickable(true);
        h2 = (EditText) findViewById(R.id.catatan);
        tv12 = (TextView) findViewById(R.id.jumlahharga);
        tv11 = (TextView) findViewById(R.id.totalharga);

        tv7 = (TextView) findViewById(R.id.hargahstr);
        // tv8 = (TextView) findViewById(R.id.nomordetailhstr);
        tv4 = (TextView) findViewById(R.id.idKaaategoriUbah);
        imgdetail = (ImageView) findViewById(R.id.imgDetailUbahrcv);
        Integer hua = Integer.parseInt(h1.getText().toString());

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

        if (json != "") {
            getLogin = gson.fromJson(json, SiswaModel.class);
            tv1.setText(getLogin.getNamaLengkap());


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
            Integer selectedHarga = intent.getIntExtra("Rp",0);
//            String selectedName = intent.getStringExtra("name");

//            String selectedCode = intent.getStringExtra("kode");
//            String nama_toko = intent.getStringExtra("namatoko");
//            String setNomerWa = intent.getStringExtra("harga");
//            String selectedDesc = intent.getStringExtra("desc");
//            String selectedEmail = intent.getStringExtra("email");
//            String selectedImage = intent.getStringExtra("gambar");
            Integer hu = hua*selectedHarga;
            Glide.with(Checkoutbelidikoperasi.this)
                    .load("" + intent.getStringExtra("GambarProduk"))
                    .apply(new RequestOptions().override(350, 550))
                    .into(imgdetail);
            tv4.setText(intent.getStringExtra("Produk"));
            tv7.setText(Integer.toString(intent.getIntExtra("Rp",0)));
            tv11.setText(Integer.toString(intent.getIntExtra("Rp",0)));
        }


        up = findViewById(R.id.down);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = getIntent();
                if (intent.getExtras() != null){
                    count++;
                    Integer selectedHarga = intent.getIntExtra("Rp",0);
                    Integer hu = count*selectedHarga;

                    tv11.setText(String.valueOf(hu));
                    h1.setText(Integer.toString(count));// view in the text
                    h1.setError(null);
                }


            }
        });
        down = findViewById(R.id.ctrup);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                if (intent.getExtras() != null) {
                    if (count <= count1) {

                    } else {
                        count--;
                        Integer selectedHarga = intent.getIntExtra("Rp", 0);
                        Integer hu = count * selectedHarga;

                        tv11.setText(String.valueOf(hu));
                        h1.setText(Integer.toString(count));// view in the text
                    }
                    return;

                }
            }
        });

    }


    private void updateImageUpload() {
        final String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Integer harga1 = intent.getIntExtra("Rp", 0);
            Integer Ongkir = intent.getIntExtra("Ongkir", 0);
            Integer hu = (count * harga1) + Ongkir;
            Integer hw = count;
            String df = h2.getText().toString();
            String tujuan = tv3.getText().toString();
            String desc = h2.getText().toString();
            String opsi = intent.getStringExtra("Opsi");
            String Produk = intent.getStringExtra("Produk");
            String KodeProduk = intent.getStringExtra("KodeProduk");
            String kodepesanan = intent.getStringExtra("kode");
            String setNomerWa = intent.getStringExtra("harga");
            String selectedDesc = intent.getStringExtra("desc");
            String selectedEmail = intent.getStringExtra("email");
            String selectedImage = intent.getStringExtra("GambarProduk");
            Integer hp = 1;

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("login", "");

            if (json != "") {
                getLogin = gson.fromJson(json, SiswaModel.class);
                String nama = getLogin.getNamaLengkap();
                String kelas = getLogin.getKelas();
                String jurusan = getLogin.getJurusan();
                Integer iduser1 = getLogin.getId();

                RequestBody idSiswa = RequestBody.create(MediaType.parse("text/plain"), iduser1.toString());
                RequestBody Namabarang = RequestBody.create(MediaType.parse("text/plain"), Produk.trim());
                RequestBody harga = RequestBody.create(MediaType.parse("text/plain"), harga1.toString());
                RequestBody FotoBarang = RequestBody.create(MediaType.parse("text/plain"), selectedImage.trim());
                RequestBody NamaPengguna = RequestBody.create(MediaType.parse("text/plain"), nama.trim());
                RequestBody Tujuan = RequestBody.create(MediaType.parse("text/plain"), tujuan.trim());
                RequestBody KodePesanan = RequestBody.create(MediaType.parse("text/plain"), kodepesanan.trim());
                RequestBody KelasPengguna = RequestBody.create(MediaType.parse("text/plain"), kelas.trim());
                RequestBody JurusanPengguna = RequestBody.create(MediaType.parse("text/plain"), jurusan.trim());
                RequestBody TanggalTransaksi = RequestBody.create(MediaType.parse("text/plain"), date.trim());
                RequestBody DeskripsiPesanan = RequestBody.create(MediaType.parse("text/plain"), desc.trim());
                RequestBody jumlahPesanan = RequestBody.create(MediaType.parse("text/plain"), hw.toString());
                RequestBody KodeBarang = RequestBody.create(MediaType.parse("text/plain"), KodeProduk);
                RequestBody Opsi = RequestBody.create(MediaType.parse("text/plain"), opsi.trim());
                RequestBody JumlahHarga = RequestBody.create(MediaType.parse("text/plain"), hu.toString());

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<GetPesananModel> call = apiInterface.createH(idSiswa, Namabarang, harga, FotoBarang, NamaPengguna, Tujuan, KodePesanan, KelasPengguna, JurusanPengguna, TanggalTransaksi, DeskripsiPesanan, jumlahPesanan, KodeBarang, Opsi, JumlahHarga);
                call.enqueue(new Callback<GetPesananModel>() {
                    @Override
                    public void onResponse(Call<GetPesananModel> call, Response<GetPesananModel> response) {
                        dialog = new Dialog(Checkoutbelidikoperasi.this);
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                                WindowManager.LayoutParams.WRAP_CONTENT);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setContentView(R.layout.dialog_berhasil_order);


                        Button btnoke = dialog.findViewById(R.id.btnok);
                        btnoke.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent1 = new Intent(Checkoutbelidikoperasi.this, ArCode.class);
                                intent1.putExtra("kode", intent.getStringExtra("kode"));
                                startActivity(intent1);
                                Intent intent = getIntent();
                                int stok = intent.getIntExtra("Stok", 0);
                                int stokubah = stok - count;

                                Barang registerModel = new Barang();


                                registerModel.setStok(stokubah);

                                Register(registerModel);
                                //  finish();


                            }
                        });
                        dialog.show();
                    }

                    @Override
                    public void onFailure(Call<GetPesananModel> call, Throwable t) {
                        Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                        //Log.d("RETRO", "ON FAILURE : " + t.getCause());
                        Toast.makeText(getApplicationContext(), "Error, image", Toast.LENGTH_LONG).show();
                    }
                });

            }

            Intent mIntent = getIntent();

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


    public void Beli(View view) {
        tv3 = (EditText) findViewById(R.id.ruangandituju);

        h2 = (EditText) findViewById(R.id.catatan);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

        getLogin = gson.fromJson(json, SiswaModel.class);


        progressBar = new ProgressDialog(Checkoutbelidikoperasi.this);
        progressBar.show();
        progressBar.setContentView(R.layout.progres_dialog);
        progressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        if(TextUtils.isEmpty(tv3.getText().toString())){
            tv3.setError("Harus Isi Tujuan");
            progressBar.dismiss();
        }else if(TextUtils.isEmpty(h2.getText().toString())){
            h2.setError("Harus Isi Deskripsi");
            progressBar.dismiss();
        }else if(getLogin.getStatus() == 0){
            dialog=new Dialog(Checkoutbelidikoperasi.this);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_belum_verif);
            progressBar.dismiss();


            Button btnoke=dialog.findViewById(R.id.btnok);
            btnoke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(Checkoutbelidikoperasi.this, VerifikasiAkunAct.class);

                    startActivity(intent1);
                    finish();



                }
            });
            dialog.show();

        }else{

            updateImageUpload();



        }


    }

    public void Register(Barang newPPassword){
        Intent intent = getIntent();
        if (intent.getExtras() != null) {


            Integer id = intent.getIntExtra("id",0);
            Call<GetBarang> registerCall = ApiClient.getApi().updatestok(id,newPPassword);
            registerCall.enqueue(new Callback<GetBarang>() {
                @Override
                public void onResponse(Call<GetBarang> call, Response<GetBarang> response) {
                    if (response.isSuccessful()){



                    }else{

                    }
                }

                @Override
                public void onFailure(Call<GetBarang> call, Throwable t) {

                }
            });
        }
        Intent mIntent = getIntent();


    }
}