package com.example.marketappauth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdaptor extends RecyclerView.Adapter<CustomAdaptor.MyViewHolder> {
    private Context context;
    private List<Components> cmp;

    public CustomAdaptor(Context context, List<Components> cmps) {
        this.context = context;
        this.cmp = cmps;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mName;
        ImageView mImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.name);

            mImage = itemView.findViewById(R.id.image);
        }
    }

    @NonNull
    @Override
    public CustomAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.componant_list_item2,parent,false);

        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdaptor.MyViewHolder holder, int position) {
        Components com= cmp.get(position);
        holder.mName.setText(com.getName());
        holder.mImage.setImageResource(com.getImage());
    }

    @Override
    public int getItemCount() {
        return cmp.size();
    }
}
