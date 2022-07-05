package com.komputerkit.ukomde_afa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.komputerkit.ukomde_afa.ServerConfig.REQUEST_WRITE_PERMISSION;


public class HomeUtamaActivity extends AppCompatActivity {
    TextView user,telp,y;

    GridView gridView;
    Login getLogin;
    Login getLogin1;
    ImageView imgUsr;
    Login login;
    SharedPreferences sharedPreferences,sharedPreferences1;
    Button button;
    private List<dataMenu> dataMenuList = new ArrayList<>();

    String[] nomorWa = {"881036507731","881036507731","881036507731","881036507731","881036507731","881036507731","881036507731","881036507731","881036507731","881036507731"};
    String[] flowerName = {"Fresh Flower Box","Dry Flower Box","Paket Bouqet 3","Chocolate bouqet","Paket Bouqet 5","Paket Bouqet 6","Paket Bouqet 7","Paket Bouqet 8","Paket Bouqet 9","Paket Bouqet 10","Paket Bouqet 11","Paket Bouqet 12","Paket Bouqet 13"};
    String[] harga = {"Rp.187000","Rp.195000","Rp.25000","Rp.150000","Rp.24000","Rp.24000","Rp.24000","Rp.24000","Rp.24000","Rp.40000","Rp.56000","Rp12050","Rp.675888"};
    String[] Deskripsi = {"- Mix fresh flower & custom box or bamboo bag or etc" +
            "2. Data Penjelasan paket ","- Mix dry flower & custom 4 white fresh flower","Ferero bouqet + babybreath ( bisa diganti jenis coklat lain )","Ferero bouqet + babybreath ( bisa diganti jenis coklat lain )","Deskrip12","Deskrip1","Deskrip1","Deskrip134","Deskrip1","Deskrip1"};
     String[] kodeBarang = {"MM0006","MM0002","gahsf56","B003","gahsf56","gahsf56","gahsf56","gahsf56","gahsf56","asjdgsao","764bduhs","sdvasud","ajsdg45"};
     String[] EmailPenjual = {"deafaofficial@gmail.com","deafaofficial@gmail.com","deafaofficial@gmail.com","deafaofficial@gmail.com","deafaofficial@gmail.com","deafaofficial@gmail.com","deafaofficial@gmail.com","deafaofficial@gmail.com","deafaofficial@gmail.com","deafaofficial@gmail.com","deafaofficial@gmail.com","deafaofficial@gmail.com"};
     int[] flowerImages = {R.drawable.b1,R.drawable.b2,R.drawable.b3,R.drawable.b4,R.drawable.b5,R.drawable.b6,R.drawable.b7,R.drawable.b8,R.drawable.b9,R.drawable.b10,R.drawable.b11,R.drawable.b12,R.drawable.b13};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_utama);
        getSupportActionBar().hide();
        getAAllData();
        imgUsr = findViewById(R.id.imgcrd);
        requestPermission();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");

        user = findViewById(R.id.username);
        telp = findViewById(R.id.TelpUser);

        if (json != ""){
            getLogin = gson.fromJson(json,Login.class);



        }



        if (json == "" ){
            Toast.makeText(this, "tg", Toast.LENGTH_SHORT).show();
//            imgUsr.setImageResource(R.drawable.grandson);
        }


        y = findViewById(R.id.src);
        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeUtamaActivity.this,data_seacrh.class);
                startActivity(intent);
            }
        });
//        Intent intent = getIntent();
//        if (intent.getExtras() != null){
//            getLogin = (Login) intent.getSerializableExtra("data");
//            user.setText(getLogin.getEmail());
//            telp.setText(getLogin.getRelasi());
//
//        }

//        Intent intent1 = getIntent();
//        if (intent1 != null) {
//            String ha = getIntent().getStringExtra("userH");
//            user.setText(ha);
//
//        }





//        GridAdapter adapter = new GridAdapter(HomeUtamaActivity.this,dataMenuList);
//        gridView.setAdapter(adapter);





        BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setItemIconTintList(null);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setSelectedItemId(R.id.home1);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.pesan:
                        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        Gson gson = new Gson();
                        String json = sharedPreferences.getString("login","");
                        if (json != ""){
                            login = gson.fromJson(json,Login.class);
                            int idKategori = login.getId();
                            Intent intent = new Intent(getApplicationContext(),HIstoryFragment.class);
                            intent.putExtra("idKategori", idKategori);
                            overridePendingTransition(0,0);
                            startActivity(intent);

                            return true;
                        }else{
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);

                            overridePendingTransition(0,0);
                            startActivity(intent);
                        }
//                        startActivity(new Intent(getApplicationContext()
//                                ,History.class));
//                        overridePendingTransition(0,0);

                    case R.id.home1:


                        return true;
                    case R.id.info1:
                        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        Gson gson1 = new Gson();
                        String json1 = sharedPreferences.getString("login","");
//                        String value = intent.getStringExtra("data");
//                        if (getLogin != null){
//                            Intent intent1 = new Intent(HomeUtamaActivity.this,InfoActivity.class);
//                            intent1.putExtra("user",getLogin.getEmail());
//                            intent1.putExtra("telp",getLogin.getRelasi());
//                            startActivity(intent1);
//                            overridePendingTransition(0,0);
//                        }else{
                        if (json1 != ""){



                            Intent intent = new Intent(getApplicationContext(),InfoActivity.class);

                            overridePendingTransition(0,0);
                            startActivity(intent);
                            return true;
                        }else{
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);

                            overridePendingTransition(0,0);
                            startActivity(intent);
                        }

                     //   }

                        return true;

                }
                return false;
            }
        });

    }//break

        private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);

        } else {

            Toast.makeText(this, "izin belm di berikan", Toast.LENGTH_SHORT).show();
        }
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
                            Intent intent = new Intent(HomeUtamaActivity.this,ClickedItem.class);
                            intent.putExtra("desc",KontakList.get(i).getDeskripsi());
                            intent.putExtra("email",KontakList.get(i).getEmail());
                            intent.putExtra("name",KontakList.get(i).getMenu());
                            intent.putExtra("gambar",KontakList.get(i).getGambar());
                            intent.putExtra("kode",KontakList.get(i).getKode());
                            intent.putExtra("harga",KontakList.get(i).getHarga());
                            intent.putExtra("namatoko",KontakList.get(i).getNamaToko());

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

                    GridAdapter customAdapter = new GridAdapter(HomeUtamaActivity.this,KontakList);
                    gridView.setAdapter(customAdapter);


                }else{
                    String message = "An error occurred try again later ..";
                    Toast.makeText(HomeUtamaActivity.this,message,Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<GetDaataMenu> call, Throwable t) {

                String message = t.getMessage();
                Toast.makeText(HomeUtamaActivity.this,message,Toast.LENGTH_LONG).show();

            }
        });
    }
}