package com.suhailahnfsella.fudum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryPenjual extends AppCompatActivity {

    RecyclerView recyclerView;
    private RecyclerView mRecyclerView;
    ApiInterface mApiInterface;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_penjual);
        mRecyclerView = (RecyclerView) findViewById(R.id.rcvHistoryPenjual);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        load();

        //tinggal import ke rcv ne mana adapter e? ini

        getSupportActionBar().setTitle("Riwayat Penjualan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientku));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void load(){
        Call<GetDataHistory> kontakCall = mApiInterface.getHistory();

        kontakCall.enqueue(new Callback<GetDataHistory>() {
            @Override
            public void onResponse(Call<GetDataHistory> call, Response<GetDataHistory>
                    response) {
                List<DataHistory> KontakList = response.body().getData();
                Log.d("Retrofit Get", "Jumlah data Kontak: " +
                        String.valueOf(KontakList.size()));

                mAdapter = new AdapterHistoryPenjual(KontakList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetDataHistory> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }
}