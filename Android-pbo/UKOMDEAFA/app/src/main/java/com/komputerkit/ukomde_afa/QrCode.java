package com.komputerkit.ukomde_afa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class QrCode extends AppCompatActivity {
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qr_code);
        imageView = (ImageView) findViewById(R.id.iv_output);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            int imageId = bundle.getInt("resid");
            imageView.setImageResource(imageId);
        }


    }
}