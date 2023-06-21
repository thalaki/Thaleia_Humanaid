package com.example.humanaid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterItem extends RecyclerView.Adapter<AdapterItem.ItemViewHolder> {
    Context context;
    ArrayList<dataUser> dataUserArrayList;

    public AdapterItem(Context context, ArrayList<dataUser> dataUserArrayList) {
        this.context = context;
        this.dataUserArrayList = dataUserArrayList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.viewBind(dataUserArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataUserArrayList.size();
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView,
                surnameTextView,
                dateTextView,
                timeTextView,
                serviceTextView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            surnameTextView = itemView.findViewById(R.id.surnameTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            serviceTextView = itemView.findViewById(R.id.serviceTextView);
        }

        public void viewBind(dataUser dataUser) {
            nameTextView.setText(dataUser.getName());
            surnameTextView.setText(dataUser.getSurname());
            timeTextView.setText(dataUser.getTime());
            serviceTextView.setText(dataUser.getService());
        }
    }

}