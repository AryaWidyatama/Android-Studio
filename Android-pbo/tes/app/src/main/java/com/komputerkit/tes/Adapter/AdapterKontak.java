package com.komputerkit.tes.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.komputerkit.tes.EditActivity;
import com.komputerkit.tes.Model.Kontak;
import com.komputerkit.tes.R;

import java.util.List;

public class AdapterKontak extends RecyclerView.Adapter<AdapterKontak.MyViewHolder>{
    List<Kontak> mKontakList;

    public AdapterKontak(List <Kontak> KontakList) {
        mKontakList = KontakList;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kontak_list, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder,final int position){
        holder.mTextViewId.setText("Id = " + mKontakList.get(position).getIdkategori());
        holder.mTextViewNama.setText("Nama = " + mKontakList.get(position).getKategori());
        holder.mTextViewNomor.setText("Nomor = " + mKontakList.get(position).getKeterangan());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), EditActivity.class);
                mIntent.putExtra("Id", mKontakList.get(position).getIdkategori());
                mIntent.putExtra("Nama", mKontakList.get(position).getKategori());
                mIntent.putExtra("Nomor", mKontakList.get(position).getKeterangan());
                view.getContext().startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount () {
        return mKontakList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewId, mTextViewNama, mTextViewNomor;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextViewId = (TextView) itemView.findViewById(R.id.tvId);
            mTextViewNama = (TextView) itemView.findViewById(R.id.tvNama);
            mTextViewNomor = (TextView) itemView.findViewById(R.id.tvNomor);
        }
    }
}
