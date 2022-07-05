package com.komputerkit.komen;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by root on 2/3/17.
 */

public class AdapterKategori extends RecyclerView.Adapter<AdapterKategori.MyViewHolder>{

    List<Barang> mKategoriList;

    public AdapterKategori(List <Barang> KategoriList) {
        this.mKategoriList = KategoriList;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent,int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produk, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder, final int position){
//        Intent intent = (Kategori.ma.getIntent());
      //  int value = intent.getIntExtra("IdKategori",0);


            holder.tvJudulProduk.setText(mKategoriList.get(position).getBarang());
            holder.tvKategoriProduk.setText(mKategoriList.get(position).getKodeBRG());
            holder.tvRp.setText(String.valueOf(mKategoriList.get(position).getHarga()));
//            holder.tvKecProduk.setText(mKategoriList.get(position).getKecamatan());
//            holder.tvKabProduk.setText(mKategoriList.get(position).getKabupaten());
            Glide.with(holder.itemView.getContext())
                    .load("" + mKategoriList.get(position).getFotoBrg())
                    .into(holder.imgProduk1);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetailProduk.class);
                    intent.putExtra("Produk", mKategoriList.get(position).getBarang());
                    intent.putExtra("Kategori", mKategoriList.get(position).getKodeBRG());
                    intent.putExtra("Rp", mKategoriList.get(position).getHarga());
                    intent.putExtra("Deskripsi", mKategoriList.get(position).getDeskripsi());
                   intent.putExtra("Stok", mKategoriList.get(position).getStok());
                    intent.putExtra("GambarProduk", mKategoriList.get(position).getFotoBrg());
                    intent.putExtra("KodeProduk", mKategoriList.get(position).getKodeBRG());
//                    intent.putExtra("GambarToko", mKategoriList.get(position).getFototoko());
//                    intent.putExtra("Owner", mKategoriList.get(position).getNamatoko());
//                    intent.putExtra("Kec", mKategoriList.get(position).getKecamatan());
//                    intent.putExtra("Kab", mKategoriList.get(position).getKabupaten());
//                    intent.putExtra("Tahun", mKategoriList.get(position).getTahunusaha());
//                    intent.putExtra("Alamat", mKategoriList.get(position).getAlamattoko());
//                    intent.putExtra("Sosmed", mKategoriList.get(position).getSosmed());
//                    intent.putExtra("IdToko", mKategoriList.get(position).getIdtoko());
//                    intent.putExtra("Whatsapp", mKategoriList.get(position).getWhatsapp());
//                    intent.putExtra("Email", mKategoriList.get(position).getEmail());
//                    intent.putExtra("KodeProduk", mKategoriList.get(position).getKodeproduk());
                    view.getContext().startActivity(intent);
                }
            });

    }

    @Override
    public int getItemCount () {
        return mKategoriList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvJudulProduk, tvKategoriProduk, tvRp, tvKecProduk, tvKabProduk, tvIdToko;

        public ImageView imgProduk1;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvJudulProduk = (TextView) itemView.findViewById(R.id.tvNamaUser);
            tvKategoriProduk = (TextView) itemView.findViewById(R.id.tvNamaPjg);
            tvRp = (TextView) itemView.findViewById(R.id.tvRp);
//            tvKecProduk = (TextView) itemView.findViewById(R.id.tvTglOrder);
//            tvKabProduk = (TextView) itemView.findViewById(R.id.tvKabProduk);
//            tvIdToko = (TextView) itemView.findViewById(R.id.tvIdUser);
            imgProduk1 = (ImageView) itemView.findViewById(R.id.imgProfil);
        }
    }
}
