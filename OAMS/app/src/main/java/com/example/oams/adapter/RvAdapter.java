package com.example.oams.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oams.R;
import com.example.oams.items.ListItem;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
    private Context context;
    List<ListItem> listItems;

    public RvAdapter(Context context, List<ListItem> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_att_rv,parent, false);
        final ViewHolder vHolder = new ViewHolder(v);


        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    ListItem listItem =listItems.get(position);
    holder.std_name.setText(listItem.getName());
    holder.std_reg.setText(listItem.getRegno());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView std_name, std_reg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            std_name = itemView.findViewById(R.id.std_name);
            std_reg = itemView.findViewById(R.id.std_reg);
        }
    }
}
