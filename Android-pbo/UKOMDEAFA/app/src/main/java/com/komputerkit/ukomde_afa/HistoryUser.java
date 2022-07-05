package com.komputerkit.ukomde_afa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP;

public class HistoryUser extends AppCompatActivity {
    private Button button,btn1;
    private ImageButton imageButton;
    ImageView imgdetail;
    ApiMenuInterface mApiInterface;
    String tittle,overview;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13;
    dataMenu dataMenu;
    private int idkategori;
    ImageView gh;
    Login login;
    ProgressDialog progressBar;
    Dialog dialog;
    Intent mIntent = getIntent();

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_user);
        getSupportActionBar().hide();

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backActivity();
            }
        });

        Intent mIntent = getIntent();
        imgdetail = (ImageView) findViewById(R.id.imgDetailUbahrcv);
        tv1 = (TextView) findViewById(R.id.idKaaategoriUbah);
        tv2 = (TextView) findViewById(R.id.KodeUbah);
        tv3 = (TextView) findViewById(R.id.tvKodeBarangUbah);
        tv4 = (TextView) findViewById(R.id.tvHargaDetailRcv);
        tv5 = (TextView) findViewById(R.id.status1);
        tv6 = (TextView) findViewById(R.id.tvKodeBarangUbahUser);
        gh = (ImageView) findViewById(R.id.sampah);
        tv7 = (TextView) findViewById(R.id.hargahstr);
        tv8 = (TextView) findViewById(R.id.nomordetailhstr);
        tv9 = (TextView) findViewById(R.id.catatan);
        tv10 = (TextView) findViewById(R.id.jumlahpesanan);
        tv11 = (TextView) findViewById(R.id.createdat);
        tv12 = (TextView) findViewById(R.id.jumlahharga);

        gh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog=new Dialog(HistoryUser.this);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_haous_history);


                Button btnoke=dialog.findViewById(R.id.btniya);
                btnoke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressBar = new ProgressDialog(HistoryUser.this);
                        progressBar.show();
                        progressBar.setContentView(R.layout.progres_dialog);
                        progressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                        dialog.dismiss();
                        hapushistory();

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


        tv1.setText(String.valueOf(mIntent.getStringExtra("menu")));
        tv2.setText(mIntent.getStringExtra("harga"));
        tv4.setText(mIntent.getStringExtra("deskripsi"));
        Glide.with(HistoryUser.this)
                .load("" + mIntent.getStringExtra("gambarbarang"))
                .apply(new RequestOptions().override(350, 550))
                .into(imgdetail);

        tv5.setText(mIntent.getStringExtra("kode"));
        tv6.setText(mIntent.getStringExtra("kodebarang"));
        tv7.setText(mIntent.getStringExtra("hargamn"));
        tv8.setText(mIntent.getStringExtra("nomor"));
        tv9.setText(mIntent.getStringExtra("keterangan"));
        tv10.setText(mIntent.getStringExtra("jumlah"));
        tv11.setText(mIntent.getStringExtra("tanggal"));
        tv12.setText(mIntent.getStringExtra("total"));
        tv3.setText(mIntent.getStringExtra("nmrtrk"));

        button = (Button) findViewById(R.id.btnoke);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Gson gson = new Gson();
                String json = sharedPreferences.getString("login", "");
                if (json != "") {
                    login = gson.fromJson(json, Login.class);

                    int idKategori = login.getId();
                    Intent intent = new Intent(getApplicationContext(), HIstoryFragment.class);
                    intent.putExtra("idKategori", idKategori);
                    overridePendingTransition(0, 0);
                    startActivity(intent);

                }
                finish();
            }
        });
        }

    public void backActivity(){
      finish();

    }

    public void hapushistory() {
        Intent mIntent = getIntent();
        Integer id = getIntent().getIntExtra("idhistory", 0);
        ApiMenuInterface api = ApiClient.getClient().create(ApiMenuInterface.class);
        Call<DataHistory> del = api.hapusgistoryuser(id);
        del.enqueue(new Callback<DataHistory>() {
            @Override
            public void onResponse(Call<DataHistory> call, Response<DataHistory> response) {
                Log.d("Retro", "onResponse" + call);
                dialog.dismiss();

                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Gson gson = new Gson();
                String json = sharedPreferences.getString("login", "");
                if (json != "") {
                    login = gson.fromJson(json, Login.class);

                    int idKategori = login.getId();
                    Intent intent = new Intent(getApplicationContext(), HIstoryFragment.class);
                    intent.putExtra("idKategori", idKategori);
                    overridePendingTransition(0, 0);
                    startActivity(intent);

                }
                finish();


            }

            @Override
            public void onFailure(Call<DataHistory> call, Throwable t) {
//                        pd.hide();
                Log.d("Retro", "onFailure" + t.getLocalizedMessage());

                Toast.makeText(HistoryUser.this, "tes :" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}