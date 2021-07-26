package com.komputerkit.counter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int count = 0;
    TextView tvHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        load();
    }

    public void load(){
        tvHasil = findViewById(R.id.tvHasil);
    }

    public void btnUP(View view) {
        count++;
       tvHasil.setText(count+"");
    }

    public void btnDown(View view) {
        count--;
        tvHasil.setText(count+"");
    }
}