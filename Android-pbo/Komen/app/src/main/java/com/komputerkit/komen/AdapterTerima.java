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

public class AdapterTerima extends RecyclerView.Adapter<AdapterTerima.MyViewHolder>{
    List<PesananModel> mKategoriList;

    public AdapterTerima(List <PesananModel> KategoriList) {
        this.mKategoriList = KategoriList;
    }

    @Override
    public AdapterTerima.MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produk, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder (AdapterTerima.MyViewHolder holder, final int position){
//        Intent intent = (TerimaAct.ma.getIntent());
//        int value = intent.getIntExtra("idKategori",0);
//        //   int value = 8;
//        if (mKategoriList.get(position).getIdSiswa() == value){
//            if (mKategoriList.get(position).getStatus().equals("Diterima")){
                holder.tvJudulProduk.setText(mKategoriList.get(position).getNamabarang());
                holder.tvKategoriProduk.setText(mKategoriList.get(position).getKodePesanan());
                holder.tvRp.setText(String.valueOf(mKategoriList.get(position).getHarga()));
//            holder.tvKecProduk.setText(mKategoriList.get(position).getKecamatan());
//            holder.tvKabProduk.setText(mKategoriList.get(position).getKabupaten());
                Glide.with(holder.itemView.getContext())
                        .load("" + mKategoriList.get(position).getFotoBarang())
                        .into(holder.imgProduk1);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), DetailPesanan.class);
                        intent.putExtra("NamaP", mKategoriList.get(position).getNamaPengguna());
                        intent.putExtra("KelasP", mKategoriList.get(position).getKelasPengguna());
                        intent.putExtra("JurusanP", mKategoriList.get(position).getJurusanPengguna());
                        intent.putExtra("NamaB", mKategoriList.get(position).getNamabarang());
                        intent.putExtra("DescP", mKategoriList.get(position).getDeskripsiPesanan());
                        intent.putExtra("HargaB", mKategoriList.get(position).getHarga());
                        intent.putExtra("JumlahP", mKategoriList.get(position).getJumlahPesanan());
                        intent.putExtra("JumlahH", mKategoriList.get(position).getJumlahHarga());
                        intent.putExtra("Tanggal", mKategoriList.get(position).getTanggalTransaksi());
                        intent.putExtra("Status", mKategoriList.get(position).getStatus());
                        intent.putExtra("Tujuan", mKategoriList.get(position).getTujuan());
                        intent.putExtra("KodeP", mKategoriList.get(position).getKodePesanan());
                        intent.putExtra("Gambar", mKategoriList.get(position).getFotoBarang());
//                    intent.putExtra("Alamat", mKategoriList.get(position).getAlamattoko());
//                    intent.putExtra("Sosmed", mKategoriList.get(position).getSosmed());
//                    intent.putExtra("IdToko", mKategoriList.get(position).getIdtoko());
//                    intent.putExtra("Whatsapp", mKategoriList.get(position).getWhatsapp());
//                    intent.putExtra("Email", mKategoriList.get(position).getEmail());
//                    intent.putExtra("KodeProduk", mKategoriList.get(position).getKodeproduk());
                        view.getContext().startActivity(intent);
                    }
                });
//            }else {
//
//                holder.itemView.setVisibility(View.GONE);
//                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
//            }
//        }else {
//
//            //   FrameLayout linearLayout = (FrameLayout) context.getResources();
//            holder.itemView.setVisibility(View.GONE);
//            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
//        }

        return;
    }

    @Override
    public int getItemCount () {
        return mKategoriList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvJudulProduk, tvKategoriProduk, tvRp, tvKecProduk, tvKabProduk;

        public ImageView imgProduk1;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvJudulProduk = (TextView) itemView.findViewById(R.id.tvNamaUser);
            tvKategoriProduk = (TextView) itemView.findViewById(R.id.tvNamaPjg);
            tvRp = (TextView) itemView.findViewById(R.id.tvRp);
//            tvKecProduk = (TextView) itemView.findViewById(R.id.tvTglOrder);
//            tvKabProduk = (TextView) itemView.findViewById(R.id.tvKabProduk);
            imgProduk1 = (ImageView) itemView.findViewById(R.id.imgProfil);
        }
    }

}
