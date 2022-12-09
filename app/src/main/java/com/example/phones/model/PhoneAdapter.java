package com.example.phones.model;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phones.R;
import com.example.phones.ui.PhonesFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.ViewHolder> {


    OnItemClickListener clickListener;
    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }
    PhonesFragment phonesFragment;
    ArrayList<Phones> phones;

    public void setNewDataPhone(ArrayList<Phones> newPhones){
        this.phones = newPhones;
    }

    public int getMenuPosition() {
        return menuPosition;
    }

    private int menuPosition;

    public PhoneAdapter(ArrayList<Phones> phones, PhonesFragment phonesFragment){
        this.phones = phones;
        this.phonesFragment = phonesFragment;
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

    public void filterList(ArrayList<Phones> phonesFilter){
        phones = phonesFilter;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image, edit;
        private TextView name;
        private TextView description;
        private TextView date;


        public ImageView getImage() {
            return image;
        }

        public ImageView getEdit() {
            return edit;
        }

        public TextView getName() {
            return name;
        }

        public TextView getDescription() {
            return description;
        }

        public TextView getDate() {
            return date;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.phoneName);
            description = itemView.findViewById(R.id.phoneDsecription);
            date = itemView.findViewById(R.id.datePhone);
            image = itemView.findViewById(R.id.phoneImage);
            edit = itemView.findViewById(R.id.phoneCardEdit);

            registerContextMenu(itemView);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(itemView, getAdapterPosition());
                }
            });

            image.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        menuPosition = getLayoutPosition();
                        itemView.showContextMenu(15, 15);
                    }
                    return true;
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onEditIconClick(itemView, getAdapterPosition());
                }
            });


        }

        private void registerContextMenu(View itemView) {
            if (phonesFragment!=null){
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        menuPosition = getLayoutPosition();
                        return false;
                    }
                });
                phonesFragment.registerForContextMenu(itemView);

            }
        }

        public void bind(Phones phones) {
            name.setText(phones.getName());
            description.setText(phones.getDescriptions());
            if (phones.getDate()!=null) {
                date.setText(new SimpleDateFormat("dd-MM-yyyy").format(phones.getDate()));
            }
            image.setImageResource(phones.getImage());
        }
    }
}
