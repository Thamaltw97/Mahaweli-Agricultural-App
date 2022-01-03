package com.example.marketappauth.adapters;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.marketappauth.R;
import com.example.marketappauth.harvest.models.Model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<Model> mList;
    Context context;

    public MyAdapter(Context context , ArrayList<Model> mList){

        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.yield_list_item , parent ,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model model = mList.get(position);
        int y = Integer.parseInt(model.getWeight());
        int x = Integer.parseInt(model.getPrice());
        String date = model.getDate();

       // holder.year.setText(model.getYear());
        // holder.yield.setText(model.getYeild());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView year , yield;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

/*
            year = itemView.findViewById(R.id.yield_year);
            yield = itemView.findViewById(R.id.yield_txt);
*/

        }
    }
}