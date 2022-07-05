package com.komputerkit.ukomde_afa;

import android.content.Context;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter {

    public List<dataMenu> dataMenuList;

    Context context;
    String[] flowerName;
    String[] kodeBarang;
    String[] EmailPenjual;
    LayoutInflater inflater;
    int[] flowerImage;




    public GridAdapter(Context context, List<dataMenu> dataMenuList) {
        this.context = context;
//        this.flowerName = flowerName;
//        this.flowerImage = flowerImage;
        this.dataMenuList = dataMenuList;
        inflater = LayoutInflater.from(context);

//       this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return dataMenuList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        Toast.makeText(context, ""+context, Toast.LENGTH_SHORT).show();


//        if (inflater == null){
//            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
            View view;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item,null);

        }
//            View view=LayoutInflater.from(context).inflate(R.layout.grid_item,null);





//
//
//            convertView = inflater.inflate(R.layout.grid_item, null);

//        View view = LayoutInflater.from(context).inflate(R.layout.grid_item,null);


        ImageView imageView = convertView.findViewById(R.id.gridimage);
        TextView textView = convertView.findViewById(R.id.item_name);
        TextView textView1 = convertView.findViewById(R.id.tvHargaGrid);


        textView.setText(dataMenuList.get(position).getMenu());
        textView1.setText(dataMenuList.get(position).getHarga());
//        imageView.setImageResource(flowerImage[position]);
//        textView.setText(flowerName[position]);

        Glide.with(context)
                .load("" + dataMenuList.get(position).getGambar())
                .apply(new RequestOptions().override(180, 180))
                .into(imageView);


        return convertView;
    }
}

