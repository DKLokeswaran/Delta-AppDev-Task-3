package com.example.delta_appdev_task_3;

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

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class adapterForRecycler extends RecyclerView.Adapter<adapterForRecycler.ViewHolder> {
    private Context context;

    public adapterForRecycler(Context context) {
        this.context = context;
    }

    private ArrayList<DogImage> dogs=new ArrayList<>();
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_for_recycler,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull adapterForRecycler.ViewHolder holder, int position) {
        if(dogs.get(position).getBreeds().size()==0){
            holder.txtName.setText("No info available");
        }
        else {
            holder.txtName.setText(dogs.get(position).getBreeds().get(0).getName());

        }
        Picasso.get().load(dogs.get(position).getUrl()).resize(250,250).into(holder.photo);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,DogInfo.class);
                if(dogs.get(position).getBreeds().size()!=0){
                    intent.putExtra("name",dogs.get(position).getBreeds().get(0).getName());
                    intent.putExtra("behaviour",dogs.get(position).getBreeds().get(0).getTemperament());
                    intent.putExtra("h",dogs.get(position).getBreeds().get(0).getHeight().getMetric());
                    intent.putExtra("w",dogs.get(position).getBreeds().get(0).getWeight().getMetric());
                    intent.putExtra("life",dogs.get(position).getBreeds().get(0).getWeight().getMetric());
                    intent.putExtra("bfor",dogs.get(position).getBreeds().get(0).getBredFor());
                    intent.putExtra("bgroup",dogs.get(position).getBreeds().get(0).getBreedGroup());

                }
                intent.putExtra("link",dogs.get(position).getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dogs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName,txtHeight,txtWeight,txtBeh,txtSpan;
        private RelativeLayout relativeLayout;
        private ImageView photo;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            relativeLayout=itemView.findViewById(R.id.relLayout);
            txtName=itemView.findViewById(R.id.name);
            photo=itemView.findViewById(R.id.imageView);
        }
    }

    public void setDogs(ArrayList<DogImage> dogs) {
        this.dogs = dogs;
        notifyDataSetChanged();
    }
}
