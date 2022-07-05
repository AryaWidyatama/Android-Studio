package com.komputerkit.ukomde_afa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InfoActivity extends AppCompatActivity {
    private ImageButton imageButton;
    private Button buttonout,btnpjl;
    TextView user,telp;
    Dialog dialog;
    Login login;
    GetLogin getLogin;
    ImageView gambar,cs;
    Toolbar q,w;
    SharedPreferences sharedPreferences,sharedPreferences1;

    private static final String SHARED_PREF_NAME = "";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getSupportActionBar().hide();
        q = findViewById(R.id.toolbar5);
        w = findViewById(R.id.toolbar12);
        cs = findViewById(R.id.cs);
        cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(InfoActivity.this);
                Gson gson = new Gson();
                String json = sharedPreferences.getString("login","");
                boolean installed = appInstalledOrNot("com.whatsapp");

                if (json != "") {
                    login = gson.fromJson(json, Login.class);
                    String Nama = login.getLevel();
                    Intent intent1 = new Intent(Intent.ACTION_VIEW);
                    intent1.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+62"+"81455043502"+"&text="+"Nama Pengguna = "+Nama));
                    startActivity(intent1);
                }
            }
        });
        q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this,PengaturanActivity.class);
                startActivity(intent);
            }
        });

        w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this,informasiapk.class);
                startActivity(intent);
            }
        });


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");



        // kalender tanggal
        final String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        int ha = 4;
        // menambah hari sesuai pda tanggal saat itu
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, ha);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyy-MM-dd");
        String output = sdf1.format(c.getTime());
        //--

        // waktu jam dan menit
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        //menambah jam sesuai jam realtime
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
        Calendar c1 = Calendar.getInstance();
        try {
            c1.setTime(sdf2.parse(currentTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c1.add(Calendar.HOUR, 1);
        SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm");
        String outputha = sdf3.format(c1.getTime());
        //--

        user = findViewById(R.id.username_info);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(date.compareTo(output)<0){
                    Toast.makeText(InfoActivity.this, "Sudah Melebnihi batas", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(InfoActivity.this, ""+output, Toast.LENGTH_SHORT).show();
            }
        });
        telp = findViewById(R.id.telp_info);
        telp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(InfoActivity.this, ""+outputha, Toast.LENGTH_SHORT).show();
            }
        });
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null){
//            String value = getIntent().getStringExtra("user");
//            String value1 = getIntent().getStringExtra("telp");
//            user.setText(value);
//            telp.setText(value1);
//        }

        sharedPreferences1 = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences.edit();
        editor1.putString("ya",null);
        editor1.commit();

        gambar = findViewById(R.id.imgcrd);

        if (json != ""){
           login = gson.fromJson(json,Login.class);
           user.setText(login.getLevel());
           telp.setText(login.getEmail());
            Glide.with(InfoActivity.this)
                    .load("" + login.getGambar())
                    .apply(new RequestOptions().override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))


                    .into(gambar);

        }

        if (user == null){

            sharedPreferences1 = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_NAME,user.toString());
            editor.commit();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setItemIconTintList(null);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backActivity();
            }
        });



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
                        startActivity(new Intent(getApplicationContext()
                                ,HomeUtamaActivity.class));
//                        Intent intent1 = new Intent(InfoActivity.this,HomeUtamaActivity.class);
//                        intent1.putExtra("userH",bundle);
//                        startActivity(intent1);

                        overridePendingTransition(0,0);

                        return true;
                    case R.id.info1:

                        return true;

                }
                return false;
            }
        });

        buttonout = (Button) findViewById(R.id.button4);
        buttonout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();



            }
        });

        btnpjl = (Button) findViewById(R.id.btnpjl);
        btnpjl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPenjual();
            }
        });




    }
    public void OutBack(){
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);


        finish();
    }

    public void openPenjual(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");
        if (json != ""){
            Intent intent = new Intent(this,PenjualActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
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

    public void openDialog(){
        dialog=new Dialog(this);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_out);


        Button btnoke=dialog.findViewById(R.id.btniya);
        btnoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(InfoActivity.this, "My Notification");
                builder.setContentTitle("Anda Telah Keluar!");
                builder.setContentText("Silahkan Login Kembali");
                builder.setSmallIcon(R.drawable.ic_baseline_login_24);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(InfoActivity.this);
                managerCompat.notify(2,builder.build());

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(InfoActivity.this,HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
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

    public void backActivity(){
        Intent intent = new Intent(this,HomeUtamaActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.kanan, R.anim.kanan1);
        finish();


    }

}