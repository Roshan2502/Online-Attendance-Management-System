package com.example.oams.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oams.R;
import com.example.oams.items.Shortageitems;

import java.util.List;

public class ShortageAdapter extends RecyclerView.Adapter<ShortageAdapter.ViewHolder>{
    Context context;
    List<Shortageitems> listItems;

    public ShortageAdapter(Context context, List<Shortageitems> listItems) {
        this.context = context;
        this.listItems = listItems;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_att_rv,parent, false);
        final ViewHolder vHolder = new  ViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Shortageitems listItem =listItems.get(position);
        holder.sectionName.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transation));
        holder.linearLayout.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_sacle_animation));

        holder.sectionName.setText(listItem.getStd_name());
        holder.std_reg.setText(listItem.getPercentage());

    }



    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView sectionName, std_reg;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.att_item_id);
            sectionName = itemView.findViewById(R.id.std_name);
            std_reg = itemView.findViewById(R.id.std_reg);
        }
    }

}
