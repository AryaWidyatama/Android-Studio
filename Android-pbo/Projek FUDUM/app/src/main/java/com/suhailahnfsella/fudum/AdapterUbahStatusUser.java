package com.suhailahnfsella.fudum;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by root on 2/3/17.
 */

public class AdapterUbahStatusUser extends RecyclerView.Adapter<AdapterUbahStatusUser.MyViewHolder>{
    List<UserModel> mKategoriList;
    LoginModel loginModel;

    public AdapterUbahStatusUser(List <UserModel> KategoriList) {
        this.mKategoriList = KategoriList;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent,int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ubah_status, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder (AdapterUbahStatusUser.MyViewHolder holder, final int position){
        SharedPreferences sharedPreferences,sharedPreferences1;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(holder.itemView.getContext());
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");
        loginModel = gson.fromJson(json,LoginModel.class);

        if (mKategoriList.get(position).getStatus() == 2) {
            holder.tvNamaUser.setText(mKategoriList.get(position).getUsername());
            holder.tvNamaPjg.setText(mKategoriList.get(position).getNamapanjang());
            holder.tvEmail.setText(mKategoriList.get(position).getEmail());
            holder.tvTelp.setText(mKategoriList.get(position).getTelp());
            holder.tvLevel.setText(mKategoriList.get(position).getLevel());
            holder.tvSts.setText(String.valueOf(mKategoriList.get(position).getId()));
            Glide.with(holder.itemView.getContext())
                    .load("" + mKategoriList.get(position).getFotoprofil())
                    .into(holder.imgProfil);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), KonfirmasiUbahStatus.class);
                    intent.putExtra("IdUser", mKategoriList.get(position).getId());
                    intent.putExtra("Whatsapp", mKategoriList.get(position).getTelp());
                    view.getContext().startActivity(intent);
                }
            });
        } else {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
    }

    @Override
    public int getItemCount () {
        return mKategoriList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNamaUser, tvNamaPjg, tvEmail, tvTelp, tvLevel, tvSts;

        public ImageView imgProfil;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvNamaUser = (TextView) itemView.findViewById(R.id.tvNamaUser);
            tvNamaPjg = (TextView) itemView.findViewById(R.id.tvNamaPjg);
            tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            tvTelp = (TextView) itemView.findViewById(R.id.tvTelp);
            tvLevel = (TextView) itemView.findViewById(R.id.tvLevel);
            tvSts = (TextView) itemView.findViewById(R.id.tvIdKonfirm);
            imgProfil = (ImageView) itemView.findViewById(R.id.imgProfil);
        }
    }
}