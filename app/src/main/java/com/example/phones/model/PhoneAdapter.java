package com.example.phones.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phones.R;

import java.util.ArrayList;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.ViewHolder> {


    OnItemClickListener clickListener;
    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }


    ArrayList<Phones> phones;
    public PhoneAdapter(ArrayList<Phones> phones){
        this.phones = phones;
    }
    @NonNull
    @Override
    public PhoneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent,false);
        return new PhoneAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneAdapter.ViewHolder holder, int position) {
        holder.bind(phones.get(position));
    }

    @Override
    public int getItemCount() {
        return phones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image, edit;
        private TextView name;
        private TextView description;
        private TextView date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.phoneName);
            description = itemView.findViewById(R.id.phoneDsecription);
            date = itemView.findViewById(R.id.datePhone);
            image = itemView.findViewById(R.id.phoneImage);
            edit = itemView.findViewById(R.id.phoneCardEdit);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(itemView, getAdapterPosition());
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onEditIconClick(itemView, getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    clickListener.onLingClick(itemView, getAdapterPosition());

                    return true;
                }
            });

        }

        public void bind(Phones phones) {

            name.setText(phones.getName());
            description.setText(phones.getDescriptions());
            date.setText(phones.getDate());
            image.setImageResource(phones.getImage());
        }
    }
}
