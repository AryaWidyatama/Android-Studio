package com.komputerkit.ukomde_afa;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    List<DataHistory> mKontakList;
    Context context;
    public HistoryAdapter(List<DataHistory> MenuList) {
        this.mKontakList = MenuList;

    }



    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_history_user, parent,false);
       ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, final int position) {
        String ha = "proses";
        Intent intent = (act1.ma.getActivity().getIntent());

        int value = intent.getIntExtra("idKategori",0);

        if (mKontakList.get(position).getIdUser() == value){
            if (mKontakList.get(position).getStatus().equals(ha)){
                holder.statusnewrcv.setText(mKontakList.get(position).getStatus());
               holder.tvjudulrcv.setText( mKontakList.get(position).getNamaMenu());
                holder.tvnamatoko.setText( mKontakList.get(position).getNamaToko());
                holder.tvHargabrg.setText( mKontakList.get(position).getTotal());
                holder.jumlah.setText( mKontakList.get(position).getJumlah());
             //   holder.tvdescrcv.setText( "Nama : " + mKontakList.get(position).getNamaUser());
               // holder.tvkodebrg.setText( "Alamat : " +mKontakList.get(position).getAlamat());
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
                        Intent intent = new Intent(view.getContext(),ProsesAct.class);
                        intent.putExtra("deskripsi",mKontakList.get(position).getAlamat());
                        intent.putExtra("menu",mKontakList.get(position).getNamaMenu());
                        intent.putExtra("harga",mKontakList.get(position).getNamaUser());
                        intent.putExtra("jumlah",mKontakList.get(position).getJumlah());
                        intent.putExtra("created",mKontakList.get(position).getCreatedAt());
                        intent.putExtra("nomor",mKontakList.get(position).getTelp());
                        intent.putExtra("gambarbarang",mKontakList.get(position).getGambar());
                        intent.putExtra("kode",mKontakList.get(position).getStatus());
                        intent.putExtra("hargamn",mKontakList.get(position).getHarga());
                        intent.putExtra("nmrtrk",String.valueOf(mKontakList.get(position).getKodeTransaksi()));
                        intent.putExtra("idhistory",mKontakList.get(position).getNomorTransaksi());
                        intent.putExtra("kodebarang",mKontakList.get(position).getKodeBarang());
                        intent.putExtra("keterangan",mKontakList.get(position).getKeterangan());
                        intent.putExtra("total",mKontakList.get(position).getTotal());
                        intent.putExtra("tanggal",mKontakList.get(position).getTanggal());
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
            }else {

         //   FrameLayout linearLayout = (FrameLayout) context.getResources();
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }

        return;



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
        TextView tvnamatoko;
        TextView statusnewrcv;
        TextView jumlah;
        FrameLayout ha;
        Dialog dialog;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgviewrcv = itemView.findViewById(R.id.imgtokorcv);
            tvjudulrcv = itemView.findViewById(R.id.judulrcvhstr);
            tvnamatoko = itemView.findViewById(R.id.tvnamatokorcv);
            tvdescrcv = itemView.findViewById(R.id.tvdescrcv);
            cardView = itemView.findViewById(R.id.cardview3);
            tvkodebrg = itemView.findViewById(R.id.tvKodebrg);
            tvHargabrg = itemView.findViewById(R.id.hargarcv);
            jumlah = itemView.findViewById(R.id.jumlahrcv);
            statusnewrcv = itemView.findViewById(R.id.statusnewrcv);
            ha = itemView.findViewById(R.id.ya);
        }


    }

}
