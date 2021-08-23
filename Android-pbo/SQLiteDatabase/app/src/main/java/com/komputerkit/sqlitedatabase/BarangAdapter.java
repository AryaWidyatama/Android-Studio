package com.komputerkit.sqlitedatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.ViewHolder> {

    Context context;
    List<Barang> barangList;

    public BarangAdapter(Context context, List<Barang> barangList) {
        this.context = context;
        this.barangList = barangList;
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_barang,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvBarang.setText("Nama Barang : "+barangList.get(i).getBarang());
        viewHolder.tvStok.setText("Jumlah Stok : "+barangList.get(i).getStok());
        viewHolder.tvHarga.setText("Harga Barang : "+barangList.get(i).getHarga());

        viewHolder.tvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,viewHolder.tvMenu);
                popupMenu.inflate(R.menu.menu_item);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.ubah:
                               ((MainActivity)context).selectUpdate(barangList.get(i).getIdbarang());
                                break;

                            case R.id.hapus:
                                ((MainActivity)context).deleteData(barangList.get(i).getIdbarang());
                                break;
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return barangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvBarang, tvStok, tvHarga, tvMenu;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);

            tvBarang = itemView.findViewById(R.id.tvBarang);
            tvStok = itemView.findViewById(R.id.tvStok);
            tvHarga = itemView.findViewById(R.id.tvHarga);
            tvMenu = itemView.findViewById(R.id.tvMenu);
        }
    }
}
