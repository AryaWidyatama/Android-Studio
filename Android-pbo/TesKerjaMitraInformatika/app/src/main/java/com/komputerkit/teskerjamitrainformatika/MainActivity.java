package com.komputerkit.teskerjamitrainformatika;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView gambar;
    EditText input;
    String a1,a2,a3,a4,a5,a6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gambar = findViewById(R.id.imageView);
        input = findViewById(R.id.editTextTextPersonName);

    }

    public void ubah(View view) {
        a1 = input.getText().toString();

        if (a1.equals("H")){
                    gambar.setScaleX(-1);

        }else if (a1.equals("V")){
            gambar.setScaleY(-1);

        }else if (a1.equals("H")){
            gambar.setScaleX(1);

        }else if (a1.equals("5")){
            gambar.setTranslationX(5);


        }else if (a1.equals("V")){
            gambar.setScaleY(1);

        }else if (a1.equals("-12")){
            gambar.setTranslationX(-12);

        }else{
            Toast.makeText(this, "input keywords", Toast.LENGTH_SHORT).show();
        }

    }
}