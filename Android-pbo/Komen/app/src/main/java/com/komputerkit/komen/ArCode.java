package com.komputerkit.komen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class ArCode extends AppCompatActivity {
    QRGEncoder qrgEncoder;
    String inputvalue;
    ImageView ivOutput;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_code);
        getSupportActionBar().setTitle("Bukti Pesanan");
        Window window = getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(winParams);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(this.getResources().getColor(R.color.limedash));




        Intent intent = getIntent();
        inputvalue = intent.getStringExtra("kode");
        ivOutput = (ImageView) findViewById(R.id.iv_output);

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

    public void kembali(View view) {
        Intent intent = new Intent(ArCode.this,SelesaiAct.class);
        startActivity(intent);
    }
}