package com.komputerkit.ukomde_afa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class data_seacrh extends AppCompatActivity {
    android.widget.SearchView searchView;
    GridView gridView;
    GridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_seacrh);
        getSupportActionBar().hide();
        getAAllData();

        searchView = findViewById(R.id.src_daat);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class

                );
                startActivity(intent);
                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length()>0){
                    ApiMenuInterface apiInterface = ApiClient.getClient().create(ApiMenuInterface.class);
                    Call<GetDaataMenu> call = apiInterface.getQuery(newText);
                    call.enqueue(new Callback<GetDaataMenu>() {
                        @Override
                        public void onResponse(Call<GetDaataMenu> call, retrofit2.Response<GetDaataMenu> response) {
                            {

                                if(response.isSuccessful()){

//                    String message = "Request successful .. "+response.body().getMessage();
//                    Toast.makeText(HomeUtamaActivity.this,message,Toast.LENGTH_LONG).show();

                                    List<dataMenu> KontakList = response.body().getData();
                                    Log.d("Retrofit Get", "Jumlah data Kontak: " +
                                            String.valueOf(KontakList.size()));
//                    Toast.makeText(HomeUtamaActivity.this, ""+KontakList, Toast.LENGTH_SHORT).show();
//                    dataMenuList = response.body().getData();

                                    gridView = findViewById(R.id.grid_view);
                                    searchView = findViewById(R.id.src_daat);


                                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            Intent intent = new Intent(data_seacrh.this,ClickedItem.class);
                                            intent.putExtra("desc",KontakList.get(i).getDeskripsi());
                                            intent.putExtra("email",KontakList.get(i).getEmail());
                                            intent.putExtra("name",KontakList.get(i).getMenu());
                                            intent.putExtra("gambar",KontakList.get(i).getGambar());
                                            intent.putExtra("kode",KontakList.get(i).getKode());
                                            intent.putExtra("harga",KontakList.get(i).getHarga());
                                            startActivity(intent);
                                        }
                                    });

                                    GridAdapter customAdapter = new GridAdapter(data_seacrh.this,KontakList);
                                    gridView.setAdapter(customAdapter);


                                }else{
                                    String message = "An error occurred try again later ..";
                                    Toast.makeText(data_seacrh.this,message,Toast.LENGTH_LONG).show();
                                }


                            }
                        }

                        @Override
                        public void onFailure(Call<GetDaataMenu> call, Throwable t) {

                        }
                    });
                }else{
                    getAAllData();
                }
                return true;
            }
        });

    }

    public void getAAllData(){
        Call<GetDaataMenu> imagesResponse = ApiClient.getInterface().getMenu2();

        imagesResponse.enqueue(new Callback<GetDaataMenu>() {
            @Override
            public void onResponse(Call<GetDaataMenu> call, Response<GetDaataMenu> response) {

                if(response.isSuccessful()){

//                    String message = "Request successful .. "+response.body().getMessage();
//                    Toast.makeText(HomeUtamaActivity.this,message,Toast.LENGTH_LONG).show();

                    List<dataMenu> KontakList = response.body().getData();
                    Log.d("Retrofit Get", "Jumlah data Kontak: " +
                            String.valueOf(KontakList.size()));
//                    Toast.makeText(HomeUtamaActivity.this, ""+KontakList, Toast.LENGTH_SHORT).show();
//                    dataMenuList = response.body().getData();

                    gridView = findViewById(R.id.grid_view);



                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(data_seacrh.this,ClickedItem.class);
                            intent.putExtra("desc",KontakList.get(i).getDeskripsi());
                            intent.putExtra("email",KontakList.get(i).getEmail());
                            intent.putExtra("name",KontakList.get(i).getMenu());
                            intent.putExtra("gambar",KontakList.get(i).getGambar());
                            intent.putExtra("kode",KontakList.get(i).getKode());
                            intent.putExtra("harga",KontakList.get(i).getHarga());

//                String selectedName = flowerName[i];
//                int selectedImage = flowerImages[i];
//                String setNomerWa = nomorWa[i];
//                String selectedCode = kodeBarang[i];
//                String selectedHarga = harga[i];
//                String selectedDesc = Deskripsi[i];
//                String selectedEmail = EmailPenjual[i];
//                startActivity(new Intent(HomeUtamaActivity.this,ClickedItem.class));\
                            startActivity(intent);
                        }
                    });

                    GridAdapter customAdapter = new GridAdapter(data_seacrh.this,KontakList);
                    gridView.setAdapter(customAdapter);


                }else{
                    String message = "An error occurred try again later ..";
                    Toast.makeText(data_seacrh.this,message,Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<GetDaataMenu> call, Throwable t) {

                String message = t.getMessage();
                Toast.makeText(data_seacrh.this,message,Toast.LENGTH_LONG).show();

            }
        });
    }


}