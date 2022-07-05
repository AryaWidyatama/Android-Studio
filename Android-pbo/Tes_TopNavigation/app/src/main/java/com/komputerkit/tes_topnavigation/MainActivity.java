package com.komputerkit.tes_topnavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.TableLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    TabLayout tableLayout;
    ViewPager viewPager2;
    String myString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();



        tableLayout = findViewById(R.id.tablelayout);
        viewPager2 = findViewById(R.id.viewpager);

        tableLayout.setupWithViewPager(viewPager2);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new act1(),"Monday");
        vpAdapter.addFragment(new act2(),"Tuesday");
        vpAdapter.addFragment(new act3(),"Wednesday");
        viewPager2.setAdapter(vpAdapter);


//        Intent intent = new Intent(this,act1.class);
//        Bundle data1 = new Bundle();
//        String ha = "ye";
//        data1.putString("haha",ha);
//        intent.putExtras(data1);
//        startActivity(intent);


//               String ha = "ye";
//        Bundle bundle = new Bundle();
//        bundle.putString("haha", ha);
//// set Fragmentclass Arguments
//        Fragment fragobj = new act1();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragobj.setArguments(bundle);
//        fragmentTransaction.replace(R.id.yq,fragobj).commit();



    }
    public String getMyData() {
        String he = "Halo gan";
        return he;

    }

    public String getMyData1() {
        String he = "Halo gan2";
        return he;

    }



}