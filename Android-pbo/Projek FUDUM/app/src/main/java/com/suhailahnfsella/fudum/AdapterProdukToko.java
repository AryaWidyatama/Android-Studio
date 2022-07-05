package com.suhailahnfsella.fudum;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by root on 2/3/17.
 */

public class AdapterProdukToko extends RecyclerView.Adapter<AdapterProdukToko.MyViewHolder>{

    private List<Menu> menuList;
    LoginModel loginModel;

    public AdapterProdukToko(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent,int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produk_toko, parent, false);
        MyViewHolder mViewHolder = new MyViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterProdukToko.MyViewHolder holder, final int position) {
        SharedPreferences sharedPreferences,sharedPreferences1;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(holder.itemView.getContext());
        Gson gson = new Gson();
        String json = sharedPreferences.getString("login","");
        loginModel = gson.fromJson(json,LoginModel.class);

        if (menuList.get(position).getIdtoko() == loginModel.getId()){
            holder.tvJudulProduk.setText(menuList.get(position).getProduk());
            holder.tvKategoriProduk.setText(menuList.get(position).getKategori());
            holder.tvRp.setText(String.valueOf(menuList.get(position).getHarga()));
//            holder.tvKecProduk.setText(menuList.get(position).getKecamatan());
//            holder.tvKabProduk.setText(menuList.get(position).getKabupaten());
            Glide.with(holder.itemView.getContext())
                    .load("" + menuList.get(position).getGambar())
                    .into(holder.imgProduk1);
            holder.tvMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(view.getContext(), holder.tvMenu);
                    popup.inflate(R.menu.menu_option);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.menu_update:
                                    Intent intent = new Intent(view.getContext(), UpdateProduk.class);
                                    intent.putExtra("IdMenu", menuList.get(position).getIdmenu());
                                    intent.putExtra("Produk", menuList.get(position).getProduk());
                                    intent.putExtra("Kategori", menuList.get(position).getKategori());
                                    intent.putExtra("Rp", menuList.get(position).getHarga());
                                    intent.putExtra("Deskripsi", menuList.get(position).getDeskripsi());
                                    intent.putExtra("Stok", menuList.get(position).getStok());
                                    intent.putExtra("GambarProduk", menuList.get(position).getGambar());
                                    intent.putExtra("GambarToko", menuList.get(position).getFototoko());
                                    intent.putExtra("Owner", menuList.get(position).getNamatoko());
                                    intent.putExtra("Kec", menuList.get(position).getKecamatan());
                                    intent.putExtra("Kab", menuList.get(position).getKabupaten());
                                    intent.putExtra("Tahun", menuList.get(position).getTahunusaha());
                                    intent.putExtra("Alamat", menuList.get(position).getAlamattoko());
                                    intent.putExtra("Sosmed", menuList.get(position).getSosmed());
                                    intent.putExtra("IdToko", menuList.get(position).getIdtoko());
                                    intent.putExtra("Whatsapp", menuList.get(position).getWhatsapp());
                                    intent.putExtra("Email", menuList.get(position).getEmail());
                                    intent.putExtra("KodeProduk", menuList.get(position).getKodeproduk());
                                    view.getContext().startActivity(intent);
                                    return true;

                                case R.id.menu_hapus:
                                    Intent intent1 = new Intent(view.getContext(), DeleteProduk.class);
                                    intent1.putExtra("IdMenu", menuList.get(position).getIdmenu());
                                    view.getContext().startActivity(intent1);
                                    return true;

                                default:
                                    return false;
                            }
                        }
                    });
                    popup.show();
                }
            });
        }else{
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
    }

    @Override
    public int getItemCount () {
        return menuList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvJudulProduk, tvKategoriProduk, tvRp, tvKecProduk, tvKabProduk, tvIdToko, tvMenu;

        public ImageView imgProduk1;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvJudulProduk = (TextView) itemView.findViewById(R.id.tvNamaUser);
            tvKategoriProduk = (TextView) itemView.findViewById(R.id.tvNamaPjg);
            tvRp = (TextView) itemView.findViewById(R.id.tvRp);
//            tvKecProduk = (TextView) itemView.findViewById(R.id.tvTglOrder);
//            tvKabProduk = (TextView) itemView.findViewById(R.id.tvKabProduk);
//            tvIdToko = (TextView) itemView.findViewById(R.id.tvIdUser);
            tvMenu = (TextView) itemView.findViewById(R.id.tvMenu);
            imgProduk1 = (ImageView) itemView.findViewById(R.id.imgProfil);
        }

//        @Override
//        public void onClick(View view) {
//            showtvOption(view);
//        }
//
//        private void showtvOption(View view) {
//            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
//            popupMenu.inflate(R.menu.menu_option);
//            popupMenu.setOnMenuItemClickListener(this);
//            popupMenu.show();
//        }
//
//        @Override
//        public boolean onMenuItemClick(MenuItem menuItem) {
//            switch (menuItem.getItemId()){
//                case R.id.menu_update:
//                    Intent intent = new Intent(context, UpdateProduk.class);
//                    context.startActivity(intent);
//                    return true;
//
//                case R.id.menu_hapus:
//                    Intent intent1 = new Intent(context, UpdateProduk.class);
//                    context.startActivity(intent1);
//                    return true;
//
//                default:
//                    return false;
//
//            }
//
//        }
    }
}
