package com.komputerkit.ukomde_afa;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class HistoryPenjualAdapter extends RecyclerView.Adapter<HistoryPenjualAdapter.ViewHolder>{
    List<DataHistory> mKontakList;
    Context context;
    public HistoryPenjualAdapter(List<DataHistory> MenuList) {
        this.mKontakList = MenuList;

    }



    @Override
    public HistoryPenjualAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_history, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryPenjualAdapter.ViewHolder holder, final int position) {

        Intent intent = (OrderPenjual.ma.getIntent());
        String value = intent.getStringExtra("idKategori");

        if (mKontakList.get(position).getTelp().equals(value)){
            holder.tvHargabrg.setText("Status : " + mKontakList.get(position).getStatus());
            holder.tvjudulrcv.setText( mKontakList.get(position).getNamaMenu());
            holder.tvdescrcv.setText( "Nama : " + mKontakList.get(position).getNamaUser());
            holder.tvkodebrg.setText( "Alamat : " +mKontakList.get(position).getAlamat());
            Glide.with(holder.itemView.getContext())
                    .load("" + mKontakList.get(position).getGambar())
                    .into(holder.imgviewrcv);
//        holder.tvdescrcv.setText("Desc" + mKontakList.get(position).getTelp());
//        holder.tvdescrcv.setText(myDatalist.getDescBarang());
//        holder.imgviewrcv.setImageResource(mKontakList.get(position).getGambar());
//        holder.tvkodebrg.setText(myDatalist.getKodeBarang());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(),PenjualOrder.class);
                    intent.putExtra("deskripsi",mKontakList.get(position).getAlamat());
                    intent.putExtra("menu",mKontakList.get(position).getNamaMenu());
                    intent.putExtra("harga",mKontakList.get(position).getNamaUser());
                    intent.putExtra("gambarbarang",mKontakList.get(position).getGambar());
                    intent.putExtra("kode",mKontakList.get(position).getStatus());
                    intent.putExtra("idmenu",mKontakList.get(position).getNomorTransaksi());
//                    intent.putExtra("email",mKontakList.get(position).getEmail());
//                    intent.putExtra("idkategori",mKontakList.get(position).getIdkategori());

                    //  intent.putExtra("gambarbarang",myDatalist.getGambarBarang());
//                intent.putExtra("Judul",mKontakList.get(position).getKategori());
////                intent.putExtra("Judul",myDatalist.getDescBarang());
////                intent.putExtra("ubahkodebarang",myDatalist.getKodeBarang());
//                intent.putExtra("Harga",mKontakList.get(position).getKeterangan());

                   view.getContext().startActivity(intent);
                }
            });

        }else {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }


    }

    @Override
    public int getItemCount() {
        return mKontakList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView tvkodebrg;
        ImageView imgviewrcv;
        TextView tvjudulrcv;
        TextView tvdescrcv;
        CardView cardView;
        TextView tvHargabrg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgviewrcv = itemView.findViewById(R.id.imgviewrcv);
            tvjudulrcv = itemView.findViewById(R.id.tvjudulrcv);
            tvdescrcv = itemView.findViewById(R.id.tvdescrcv);
            cardView = itemView.findViewById(R.id.cardview3);
            tvkodebrg = itemView.findViewById(R.id.tvKodebrg);
            tvHargabrg = itemView.findViewById(R.id.tvHargabrg);
        }


    }
}
