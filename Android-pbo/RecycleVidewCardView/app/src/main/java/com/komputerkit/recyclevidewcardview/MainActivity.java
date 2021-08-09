package com.komputerkit.recyclevidewcardview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SiswaAdapter adapter;
    List<Siswa> siswaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        load();
        isiData();
    }

    public void load (){
        recyclerView = findViewById(R.id.rcvSiswa);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void isiData(){
        siswaList = new ArrayList<Siswa>();
        siswaList.add(new Siswa("Jono", "Surabaya"));
        siswaList.add(new Siswa("Oki", "Surabaya"));
        siswaList.add(new Siswa("Halo", "Surabaya"));
        siswaList.add(new Siswa("Yes", "Surabaya"));
        siswaList.add(new Siswa("Jono", "Surabaya"));
        siswaList.add(new Siswa("Jono", "Surabaya"));
        siswaList.add(new Siswa("Jono", "Surabaya"));
        siswaList.add(new Siswa("Jono", "Surabaya"));
        siswaList.add(new Siswa("Jono", "Surabaya"));
        siswaList.add(new Siswa("Jono", "Surabaya"));
        siswaList.add(new Siswa("Jono", "Surabaya"));
        siswaList.add(new Siswa("Jono", "Surabaya"));
        siswaList.add(new Siswa("Jono", "Surabaya"));
        siswaList.add(new Siswa("Jono", "Surabaya"));
        siswaList.add(new Siswa("Jono", "Surabaya"));
        siswaList.add(new Siswa("Jono", "Surabaya"));
        siswaList.add(new Siswa("Jono", "Surabaya"));

        adapter = new SiswaAdapter(this, siswaList);
        recyclerView.setAdapter(adapter);
    }

    public void btnTambah(View view) {
        siswaList.add(new Siswa("JONO RAMBO", "JAKARTA"));
        adapter.notifyDataSetChanged();
    }
}