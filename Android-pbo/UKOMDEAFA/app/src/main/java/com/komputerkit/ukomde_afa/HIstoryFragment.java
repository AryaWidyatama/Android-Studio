package com.komputerkit.ukomde_afa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;

public class HIstoryFragment extends AppCompatActivity {
    TabLayout tableLayout;
    ViewPager viewPager2;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_history_fragment);

        tableLayout = findViewById(R.id.tablelayout);
        viewPager2 = findViewById(R.id.viewpager);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backActivity();
            }
        });

        tableLayout.setupWithViewPager(viewPager2);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new act1(),"Proses");
        vpAdapter.addFragment(new act2(),"Ready");
        vpAdapter.addFragment(new act3(),"Selesai");
        viewPager2.setAdapter(vpAdapter);
    }

    public Integer getMyData() {


        Intent mIntent = getIntent();
        Intent intent = new Intent();
        intent.putExtra("idKategori", mIntent.getIntExtra("idKategori",0));
        Integer he = mIntent.getIntExtra("idKategori",0);
        return he;

    }

    public Integer getMyData1() {
        Intent mIntent = getIntent();
        Intent intent = new Intent();
        intent.putExtra("idKategori", mIntent.getIntExtra("idKategori",0));
        Integer he = mIntent.getIntExtra("idKategori",0);
        return he;

    }

    public Integer getMyData2() {
        Intent mIntent = getIntent();
        Intent intent = new Intent();
        intent.putExtra("idKategori", mIntent.getIntExtra("idKategori",0));
        Integer he = mIntent.getIntExtra("idKategori",0);
        return he;

    }

    public void backActivity() {
        Intent intent = new Intent(this, HomeUtamaActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.kanan, R.anim.kanan1);
        finish();


    }
}