package com.komputerkit.ukomde_afa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import com.google.zxing.WriterException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;


public class PemesananActivity extends AppCompatActivity {
    private Button btnpesan,btnQrcode;
    EditText inputDesc;
    AutoCompleteTextView inputEmail;
    ImageView ivOutput;
    String inputvalue;
    QRGEncoder qrgEncoder;
    ImageButton imageButton;
    Bitmap bitmap;
    Dialog dialog;

  String[] Email = new String[]{"deafaofficial@gmail.com","deafaofficial@gmail.com","deafaofficial@gmail.com","deafaofficial@gmail.com","deafaofficial@gmail.com","deafaofficial@gmail.com"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan);
        getSupportActionBar().hide();

        AutoCompleteTextView editText = findViewById(R.id.inputEmail);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Email);
        editText.setAdapter(adapter);




        inputEmail = findViewById(R.id.inputEmail);
        inputDesc = findViewById(R.id.InputDesc);
        btnQrcode = findViewById(R.id.btnQrcode);
        ivOutput = findViewById(R.id.iv_output);
        ivOutput = (ImageView) findViewById(R.id.iv_output);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backActivity();
            }
        });

        btnQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (inputDesc.length()==0){
                    Toast.makeText(PemesananActivity.this, "Silahkan Isi Form Pemesanan!", Toast.LENGTH_SHORT).show();

                }else if (inputDesc.length()>0){
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(PemesananActivity.this, "My Notification");
                    builder.setContentTitle("Transaksi Berhasil!");
                    builder.setContentText("Berikan bukti QRcode kepada penjual untuk di scan!");
                    builder.setSmallIcon(R.drawable.ic_baseline_shopping_cart_24);
                    builder.setAutoCancel(true);

                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(PemesananActivity.this);
                    managerCompat.notify(1,builder.build());


                    inputvalue = inputDesc.getText().toString().trim();
                    if (inputvalue.length()>0){
                        openDialog();

//                        AlertDialog dialog = new AlertDialog.Builder(PemesananActivity.this)
//                                .setIcon(R.drawable.order__1_)
//                                .setTitle("Pesanan telah berhasil dibuat!")
//                                .setMessage("Silahkan ScreenShoot QRcode yang telah tersedia!")
//                                .setPositiveButton("ok",null)
//                                .show();
//
//                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                        positiveButton.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                                dialog.dismiss();
//                            }
//                        });

                        WindowManager manager = (WindowManager)getSystemService(WINDOW_SERVICE);
                        Display display = manager.getDefaultDisplay();
                        Point point = new Point();
                        display.getSize(point);
                        int width = point.x;
                        int height = point.y;
                        int smallerdimension = width<height ? width:height;
                        smallerdimension = smallerdimension*4/4;
                        qrgEncoder = new QRGEncoder(inputvalue,null, QRGContents.Type.TEXT,smallerdimension);
                        try {
                            bitmap = qrgEncoder.encodeAsBitmap();
                            ivOutput.setImageBitmap(bitmap);
                        }catch (WriterException e){
                            Log.e("Tag",e.toString());
                        }
                    }

                }
                }




        });



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setItemIconTintList(null);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setSelectedItemId(R.id.home1);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.pesan:

                        return true;
                    case R.id.home1:
                        startActivity(new Intent(getApplicationContext()
                                ,HomeUtamaActivity.class));
                        overridePendingTransition(0,0);

                        return true;
                    case R.id.info1:
                        startActivity(new Intent(getApplicationContext()
                                ,InfoActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

        btnpesan = (Button) findViewById(R.id.btnPesanEmail);
        btnpesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (inputDesc.length()==0){
                    Toast.makeText(PemesananActivity.this, "Isi Form Bagian Detail Pesanan!", Toast.LENGTH_SHORT).show();

                }else if (inputDesc.length()>0){
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(PemesananActivity.this, "My Notification");
                    builder.setContentTitle("Transaksi Berhasil!");
                    builder.setContentText("Terimakasih Telah Berbelanja, Tunggu Notifikasi Dari Penjual");
                    builder.setSmallIcon(R.drawable.ic_baseline_library_add_check_24);
                    builder.setAutoCancel(true);

                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(PemesananActivity.this);
                    managerCompat.notify(1,builder.build());
                    SendEmail();
                }

            }
        });


    }

    public void HomeBalik(){
        Intent intent = new Intent(this,HomeUtamaActivity.class);
        startActivity(intent);


        finish();
    }

    public void HomeQr(){
        Intent intent = new Intent(this,QrCode.class);

        startActivity(intent);


        finish();
    }

    public void backActivity(){
        Intent intent = new Intent(this,HomeUtamaActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.kanan, R.anim.kanan1);



    }

    public void SendEmail(){

        String resipientList = inputEmail.getText().toString();
        String[] recipient = resipientList.split(",");

        String message = inputDesc.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipient);
        intent.putExtra(Intent.EXTRA_TEXT,message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Chose an email"));

    }

    public void openDialog(){
        dialog=new Dialog(this);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog);


        Button btnoke=dialog.findViewById(R.id.btniya);
        btnoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}