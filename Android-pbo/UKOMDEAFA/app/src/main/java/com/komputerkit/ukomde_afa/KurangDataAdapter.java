package com.komputerkit.ukomde_afa;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class  KurangDataAdapter extends RecyclerView.Adapter<KurangDataAdapter.ViewHolder>{
    List<dataMenu> mKontakList;
    Context context;
    public KurangDataAdapter(List<dataMenu> MenuList) {
        this.mKontakList = MenuList;

    }



    @Override
    public KurangDataAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
     View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item, parent,false);
     ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull KurangDataAdapter.ViewHolder holder,final int position) {
        Intent intent = (KurangData.ma.getIntent());
        String value = intent.getStringExtra("idKategori");
        if (mKontakList.get(position).getEmail().equals(value)){
            holder.tvHargabrg.setText("Harga :  Rp." + mKontakList.get(position).getHarga());
            holder.tvjudulrcv.setText( mKontakList.get(position).getMenu());
            holder.tvdescrcv.setText( mKontakList.get(position).getDeskripsi());
            holder.tvkodebrg.setText( "Kode : " +mKontakList.get(position).getKode());
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
                    Intent intent = new Intent(view.getContext(),DetailHapusBarang.class);
                    intent.putExtra("desc",mKontakList.get(position).getDeskripsi());
                    intent.putExtra("Nama",mKontakList.get(position).getMenu());
                    intent.putExtra("Alamat",mKontakList.get(position).getHarga());
                    intent.putExtra("gambarbarang",mKontakList.get(position).getGambar());
                    intent.putExtra("kode",mKontakList.get(position).getKode());
                    intent.putExtra("idmenu",mKontakList.get(position).getIdmenu());

                    //  intent.putExtra("gambarbarang",myDatalist.getGambarBarang());
//                intent.putExtra("Judul",mKontakList.get(position).getKategori());
////                intent.putExtra("Judul",myDatalist.getDescBarang());
////                intent.putExtra("ubahkodebarang",myDatalist.getKodeBarang());
//                intent.putExtra("Harga",mKontakList.get(position).getKeterangan());

                    view.getContext().startActivity(intent);
                }
            });
        }
        else {
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
