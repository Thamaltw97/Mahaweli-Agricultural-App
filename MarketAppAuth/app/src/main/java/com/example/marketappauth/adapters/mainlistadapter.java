package com.example.marketappauth.adapters;

import com.example.marketappauth.HarvestDetailsActivity;

public class mainlistadapter  {
/*
    private LayoutInflater layoutInflater;
    private List<String> data;



    Adapter(Context context, List<String> data){
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
 */
/*       View view = layoutInflater.inflate(R.layout.,viewGroup,false);
        return new ViewHolder(view);*//*

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        // bind the textview with data received

        String title = data.get(i);
        viewHolder.textTitle.setText(title);

        // similarly you can set new image for each card and descriptions



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle,textDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), HarvestDetailsActivity.class);
                    i.putExtra("title",data.get(getAdapterPosition()));
                    v.getContext().startActivity(i);
                }
            });
*/
/*            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDesc);*//*

        }
    }
*/

}
