package com.komputerkit.catalogmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.komputerkit.catalogmovie.R;
import com.komputerkit.catalogmovie.activity.DetailMovieActivity;
import com.komputerkit.catalogmovie.model.Result;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private Context context;
    private List<Result> resultList;

    public MovieAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }


    @NonNull

    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(@NonNull final  ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.item_movie, parent, false);

        final MovieAdapter.MyViewHolder viewHolder = new MovieAdapter.MyViewHolder(view);
        viewHolder.relativeLayout.setOnClickListener(view1 -> {
            Intent i = new Intent(parent.getContext(), DetailMovieActivity.class);
            Result result = new Result();
            result.setOriginalTitle(resultList.get(viewHolder.getAdapterPosition()).getOriginalTitle());
            result.setOverview(resultList.get(viewHolder.getAdapterPosition()).getOverview());
            result.setPosterPath(resultList.get(viewHolder.getAdapterPosition()).getPosterPath());
            i.putExtra(DetailMovieActivity.EXTRA_MOVIE, result);
            parent.getContext().startActivity(i);
        });
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MyViewHolder holder, int position) {
        holder.tvTittle.setText(resultList.get(position).getTitle());
        holder.tvDeskripton.setText(resultList.get(position).getOverview());
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185" + resultList.get(position).getPosterPath())
                .into(holder.imgPoster);

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTittle, tvDeskripton;
        RelativeLayout relativeLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.imgMovie);
            tvTittle = itemView.findViewById(R.id.tvTittle);
            tvDeskripton = itemView.findViewById(R.id.tvDeskription);
            relativeLayout = itemView.findViewById(R.id.layoutMovie);
        }
    }
}
