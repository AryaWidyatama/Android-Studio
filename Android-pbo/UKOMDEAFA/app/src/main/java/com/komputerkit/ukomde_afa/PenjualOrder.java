package com.komputerkit.ukomde_afa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenjualOrder extends AppCompatActivity {
    private Button button,btn1;
    ImageView imgdetail;
    ApiMenuInterface mApiInterface;
    String tittle,overview;
    TextView tv1,tv2,tv3,tv4;
    dataMenu dataMenu;
    private int idkategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjual_order);
        getSupportActionBar().hide();

        Intent mIntent = getIntent();
        imgdetail = (ImageView) findViewById(R.id.imgDetailUbahrcv);
        tv1 = (TextView) findViewById(R.id.idKaaategoriUbah);
        tv2 = (TextView) findViewById(R.id.KodeUbah);
        tv3 = (TextView) findViewById(R.id.tvKodeBarangUbah);
        tv4 = (TextView) findViewById(R.id.tvHargaDetailRcv);




//        imgdetail.setImageResource(getIntent().getIntExtra("gambarbarang", 0));
//        tv1.setText(mIntent.getStringExtra("Id"));

        tv1.setText(String.valueOf(mIntent.getStringExtra("menu")));
       tv2.setText(mIntent.getStringExtra("harga"));
        tv4.setText(mIntent.getStringExtra("deskripsi"));
        Glide.with(PenjualOrder.this)
                .load("" + mIntent.getStringExtra("gambarbarang"))
                .apply(new RequestOptions().override(350, 550))
                .into(imgdetail);

        tv3.setText(mIntent.getStringExtra("kode"));

//        tv3.setText(getIntent().getStringExtra("ubahkodebarang"));
//        tv4.setText(getIntent().getStringExtra("harga"));

//        if (tv2.equals("")) {
//
//            tv2.setText(mIntent.getStringExtra("idkategori"));
//            idkategori = Integer.parseInt(tv2.getText().toString());
//
//        }


//



        button = (Button) findViewById(R.id.btnout4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mApiInterface = ApiClient.getClient().create(ApiMenuInterface.class);
                String npm = tv2.getText().toString();

                Integer id = getIntent().getIntExtra("idmenu",0);
                ApiMenuInterface api = ApiClient.getClient().create(ApiMenuInterface.class);
                Call<GetDataHistory> del = api.getUPH1(id);
                del.enqueue(new Callback<GetDataHistory>() {
                    @Override
                    public void onResponse(Call<GetDataHistory> call, Response<GetDataHistory> response) {
                        Log.d("Retro", "onResponse"+call);

//                        Toast.makeText(DetailHapusBarang.this, "Succes", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(DetailHapusBarang.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent gotampil = new Intent(PenjualOrder.this, PenjualActivity.class);

                        startActivity(gotampil);
                        finish();

                    }

                    @Override
                    public void onFailure(Call<GetDataHistory> call, Throwable t) {
//                        pd.hide();
                        Log.d("Retro", "onFailure"+t.getLocalizedMessage());

                        Toast.makeText(PenjualOrder.this, "tes :"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }


                });




            }
        });

        btn1 = (Button) findViewById(R.id.btnhsubah2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mApiInterface = ApiClient.getClient().create(ApiMenuInterface.class);
                String npm = tv2.getText().toString();

                Integer id = getIntent().getIntExtra("idmenu",0);
                ApiMenuInterface api = ApiClient.getClient().create(ApiMenuInterface.class);
                Call<GetDataHistory> del = api.getUPH2(id);
                del.enqueue(new Callback<GetDataHistory>() {
                    @Override
                    public void onResponse(Call<GetDataHistory> call, Response<GetDataHistory> response) {
                        Log.d("Retro", "onResponse"+call);

//                        Toast.makeText(DetailHapusBarang.this, "Succes", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(DetailHapusBarang.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent gotampil = new Intent(PenjualOrder.this, PenjualActivity.class);

                        startActivity(gotampil);
                        finish();

                    }

                    @Override
                    public void onFailure(Call<GetDataHistory> call, Throwable t) {
//                        pd.hide();
                        Log.d("Retro", "onFailure"+t.getLocalizedMessage());

                        Toast.makeText(PenjualOrder.this, "tes :"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }


                });

//                if (idkategori.getText().length().trim().isEmpty()==false){
//                    Call<PostPutDel> deleteKontak = mApiInterface.deleteKontak(tv2.getText().charAt(id));
//                    deleteKontak.enqueue(new Callback<PostPutDel>() {
//                        @Override
//                        public void onResponse(Call<PostPutDel> call, Response<PostPutDel> response) {
//                            KurangData.ma.refresh();
//                            finish();
//                        }
//
//                        @Override
//                        public void onFailure(Call<PostPutDel> call, Throwable t) {
//                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
//                        }
//                    });
//                }else{
//                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
//                }
//            }





            }
        });


    }
}