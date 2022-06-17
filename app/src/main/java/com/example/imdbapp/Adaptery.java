package com.example.imdbapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder> {
    private Context mContext;
    private List<MovieModelClass> mData;

    public Adaptery(Context mContext, List<MovieModelClass> mData){
        this.mContext=mContext;
        this.mData= mData;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View v;
        LayoutInflater inflater= LayoutInflater.from(mContext);
        v= inflater.inflate(R.layout.movie_item, parent, attachToRoot:false);

        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){
        holder.id.setText(mData.get(position).getId());
        holder.name.setText(mData.get(position).getName());

        Glide.with(mContext);
            .load(string:"https://image.tmdb.org/t/p/w500" + mData.get(position).getImg());
            .into(holder.Img);
    }
}
