package com.suhailahnfsella.fudum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

public class AdapterHistoryOrderan extends RecyclerView.Adapter<AdapterHistoryOrderan.ViewHolder>{

    RcvHistoryOrderan[] rcvHistoryOrderans;
    Context context;
    List<DataHistory> mar;
    LoginModel loginModel;

    public AdapterHistoryOrderan(List<DataHistory> mar) {
        this.mar = mar;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_history,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        SharedPreferences sharedPreferences,sharedPreferences1;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(holder.itemView.getContext());
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");
        loginModel = gson.fromJson(json,LoginModel.class);

        if (mar.get(i).getIduser() == loginModel.getId()){
            holder.tvKodeProduk.setText(mar.get(i).getKodeproduk());
            holder.tvJumlahProduk.setText(String.valueOf(mar.get(i).getJumlahpesanan()));
            holder.tvNamaPemesan.setText(mar.get(i).getNamapenerima());
            holder.tvAlamatPenerima.setText(mar.get(i).getAlamatpenerima());
            holder.tvTglOrder.setText(mar.get(i).getTgl());
            holder.status.setText(mar.get(i).getStatus());
            holder.via.setText(mar.get(i).getVia());

            holder.btnBuktiTrk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), BuktiTransfer.class);
                    intent.putExtra("NoPesanan", mar.get(i).getNopesanan());
                    intent.putExtra("IdUser", mar.get(i).getIduser());
                    intent.putExtra("IdToko", mar.get(i).getIdtoko());
                    intent.putExtra("KodeProduk", mar.get(i).getKodeproduk());
                    intent.putExtra("JumlahProduk", mar.get(i).getJumlahpesanan());
                    intent.putExtra("NamaPemesan", mar.get(i).getNamapenerima());
                    intent.putExtra("AlamatPemesan", mar.get(i).getAlamatpenerima());
                    intent.putExtra("TglOrder", mar.get(i).getTgl());
                    intent.putExtra("Status", mar.get(i).getStatus());
                    intent.putExtra("Via", mar.get(i).getVia());
                    intent.putExtra("bukti", mar.get(i).getBukti());
                    v.getContext().startActivity(intent);
                }
            });
        }else {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }


    }

    @Override
    public int getItemCount() {
        return mar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvKodeProduk, tvNamaPemesan, tvAlamatPenerima, tvJumlahProduk, tvTglOrder,status,via;
        Button btnBuktiTrk;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKodeProduk = itemView.findViewById(R.id.tvNamaUser);
            tvJumlahProduk = itemView.findViewById(R.id.tvJumlahProduk);
            tvNamaPemesan = itemView.findViewById(R.id.tvNamaPemesan);
            tvAlamatPenerima = itemView.findViewById(R.id.tvAlamatPenerima);
            tvTglOrder = itemView.findViewById(R.id.tvTglOrder);
            status = itemView.findViewById(R.id.tvStatus);
            via = itemView.findViewById(R.id.via);
            btnBuktiTrk = itemView.findViewById(R.id.btnBuktiTrk);
        }
    }
}
