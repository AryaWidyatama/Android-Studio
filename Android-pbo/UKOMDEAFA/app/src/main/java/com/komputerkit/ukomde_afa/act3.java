package com.komputerkit.ukomde_afa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link act3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class act3 extends Fragment {
    private RecyclerView mRecyclerView;
    ApiMenuInterface mApiInterface;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences sharedPreferences,sharedPreferences1;
    public static act3 ma;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public act3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment act3.
     */
    // TODO: Rename and change types and number of parameters
    public static act3 newInstance(String param1, String param2) {
        act3 fragment = new act3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        HIstoryFragment activity = (HIstoryFragment) getActivity();
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_act2, null);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycleView);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiClient.getClient().create(ApiMenuInterface.class);
        ma=this;

        run();
        panggilRetrofit();

        return root;

    }

    public void run(){
        ma=this;
        HIstoryFragment activity = (HIstoryFragment) getActivity();
        Integer myData = activity.getMyData2();

        Intent intent = new Intent();
        intent.putExtra("idKategori", myData);
    }

    public void panggilRetrofit(){
        Call<GetDataHistory> kontakCall = mApiInterface.getHistory();

        kontakCall.enqueue(new Callback<GetDataHistory>() {
            @Override
            public void onResponse(Call<GetDataHistory> call, Response<GetDataHistory>
                    response) {
                List<DataHistory> KontakList = response.body().getData();
                Log.d("Retrofit Get", "Jumlah data Kontak: " +
                        String.valueOf(KontakList.size()));

                mAdapter = new HistoryAdapter3(KontakList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<GetDataHistory> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }
}