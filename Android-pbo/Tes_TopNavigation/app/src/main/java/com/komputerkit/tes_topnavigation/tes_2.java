package com.komputerkit.tes_topnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.view.MotionEvent.ACTION_DOWN;

public class tes_2 extends AppCompatActivity {
    float x1,y1,x2,y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tes2);

//        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
//
//        bottomNavigationView.setSelectedItemId(R.id.info1);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()){
//                    case R.id.pesan:
//
////                        startActivity(new Intent(getApplicationContext()
////                                ,History.class));
////                        overridePendingTransition(0,0);
//
//                    case R.id.home1:
//                        startActivity(new Intent(getApplicationContext()
//                                ,MainActivity.class));
//                        overridePendingTransition(0,0);
//
//
//                        return true;
//                    case R.id.info1:
////                        String value = intent.getStringExtra("data");
////                        if (getLogin != null){
////                            Intent intent1 = new Intent(HomeUtamaActivity.this,InfoActivity.class);
////                            intent1.putExtra("user",getLogin.getEmail());
////                            intent1.putExtra("telp",getLogin.getRelasi());
////                            startActivity(intent1);
////                            overridePendingTransition(0,0);
////                        }else{
//                        startActivity(new Intent(getApplicationContext()
//                                ,tes_2.class));
//                        overridePendingTransition(0,0);
//                        //   }
//
//                        return true;
//
//                }
//                return false;
//            }
//        });
    }

//    public boolean onTouchEvent(MotionEvent touchevent){
//        switch (touchevent.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                x1 = touchevent.getX();
//                y1 = touchevent.getY();
//                break;
//            case MotionEvent.ACTION_UP:
//                x2 = touchevent.getX();
//                y2 = touchevent.getY();
//                if(x1 <  x2){
//                Intent i = new Intent(tes_2.this, MainActivity.class);
//                startActivity(i);
//            }else if(x1 > x2){
//                Intent i = new Intent(tes_2.this, MainActivity.class);
//                startActivity(i);
//            }
//            break;
//        }
//        return false;
//
//
//
//    }
}