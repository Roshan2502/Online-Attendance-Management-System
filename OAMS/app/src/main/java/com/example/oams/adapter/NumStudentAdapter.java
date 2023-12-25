package com.example.oams.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oams.R;
import com.example.oams.items.NumStdItems;

import java.util.List;

public class NumStudentAdapter extends RecyclerView.Adapter<NumStudentAdapter.ViewHolder> {
    Context context;
    List<NumStdItems> listStd;
    Dialog dialog;
    private TextView dialog_name,dialog_name2,dialog_call;
    private static final int REQUEST_CALL = 1;

    public NumStudentAdapter(Context context, List<NumStdItems> listStd) {
        this.context = context;
        this.listStd = listStd;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.num_std_list,parent, false);
        final ViewHolder vHolder = new ViewHolder(v);
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dailog_profile);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_name = dialog.findViewById(R.id.dialog_name);
                dialog_name2 = dialog.findViewById(R.id.dialog_name2);
                dialog_call = dialog.findViewById(R.id.dialog_call);
                dialog_name.setText(listStd.get(vHolder.getAdapterPosition()).getStd_name());
                dialog_name2.setText(listStd.get(vHolder.getAdapterPosition()).getSection());
                dialog_call.setText(listStd.get(vHolder.getAdapterPosition()).getStd_phone());
                dialog.show();
                dialog_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String phone = dialog_call.getText().toString();
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                        context.startActivity(intent);
                    }
                });
            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final NumStdItems listItem =listStd.get(position);
        holder.num_std_name.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transation));
        holder.linearLayout.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_sacle_animation));

        holder.num_std_name.setText(listItem.getStd_name());
        holder.num_std_roll.setText(listItem.getStd_roll());
        holder.num_std_section.setText(listItem.getAttendance_percentage());

    }

    @Override
    public int getItemCount() {
        return listStd.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView num_std_name,num_std_roll,num_std_section;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.num_std_list);
            num_std_name = itemView.findViewById(R.id.num_std_name);
            num_std_roll = itemView.findViewById(R.id.num_std_roll);
            num_std_section = itemView.findViewById(R.id.num_std_section);
        }
    }

}
