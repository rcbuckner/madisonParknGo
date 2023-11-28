package com.cs407.madisonparkngo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LotAdapter extends RecyclerView.Adapter<LotAdapter.LotViewHolder> {

    private List<Lot> lotList; // Assuming Lot is a class representing your parking lot data
    private Context context;

    public LotAdapter(Context context, List<Lot> lotList) {
        this.context = context;
        this.lotList = lotList;
    }

    @NonNull
    @Override
    public LotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lot, parent, false);
        return new LotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LotViewHolder holder, int position) {
        Lot lot = lotList.get(position);
        holder.textViewLotName.setText(lot.getName());
        // Set other views in the holder as needed

        holder.itemView.setOnClickListener(v -> {
            // Handle item click, navigate to another page with details
        });
    }

    @Override
    public int getItemCount() {
        return lotList.size();
    }

    static class LotViewHolder extends RecyclerView.ViewHolder {
        TextView textViewLotName;
        // Other views

        public LotViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewLotName = itemView.findViewById(R.id.textViewLotName);
            // Initialize other views
        }
    }
}

