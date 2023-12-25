package com.example.oams.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.oams.R;

import com.example.oams.connection.Constants;
import com.example.oams.connection.RequestHandler;
import com.example.oams.items.TeacherItems;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder> {
    Context context;
    List<TeacherItems> listTech;
    private ImageButton teacher_del;
    private  String teach_del;
    RequestOptions options ;
    Dialog dialog;
    private TextView dialog_name,dialog_name2;
    private CircleImageView circleImageView;


    public TeacherAdapter(Context context, List<TeacherItems> listTech) {
        this.context = context;
        this.listTech = listTech;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_person_black_24dp)
                .error(R.drawable.ic_person_black_24dp);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teacher_list_admin,parent, false);
        final ViewHolder vHolder = new ViewHolder(v);
        teacher_del= v.findViewById(R.id.teacher_del);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dailog_profile);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_name = dialog.findViewById(R.id.dialog_name);
                dialog_name2 = dialog.findViewById(R.id.dialog_name2);
                circleImageView = dialog.findViewById(R.id.profile_image);
                dialog_name.setText(listTech.get(vHolder.getAdapterPosition()).getTeach_name());
                dialog_name2.setText(listTech.get(vHolder.getAdapterPosition()).getTeach_grade());

                Glide.with(context).load(listTech.get(vHolder.getAdapterPosition()).getTeach_img()).apply(options).into(circleImageView);
                dialog.show();
            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final TeacherItems listItem =listTech.get(position);
        holder.teach_name.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transation));
        holder.linearLayout.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_sacle_animation));
        holder.teach_name.setText(listItem.getTeach_name());
        holder.teach_email.setText(listItem.getTeach_email());
        holder.teach_grade.setText(listItem.getTeach_grade());

        Glide.with(context).load(listItem.getTeach_img()).apply(options).into(holder.teach_img);
        teacher_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teach_del = listItem.getTeach_id();
                delTeach();
                listTech.remove(position);
                notifyItemRemoved(position);
            }
        });
    }



    private void delTeach() {
        final String del_tech = teach_del.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_TEACH_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")){


                        Toast.makeText(context,
                                obj.getString("message"),
                                Toast.LENGTH_LONG
                        ).show();

                    }else {
                        Toast.makeText(context,
                                obj.getString("message"),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.dismiss();
                        Toast.makeText(context,
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "delete");
                params.put("teacher_id", del_tech);
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

    @Override
    public int getItemCount() {
        return listTech.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView teach_name,teach_email,teach_grade;
        private  ImageView teach_img;
        public LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.teacher_list);
            teach_name = itemView.findViewById(R.id.teacher_name);
            teach_email = itemView.findViewById(R.id.teacher_email);
            teach_grade = itemView.findViewById(R.id.teacher_grade);
            teach_img = itemView.findViewById(R.id.teach_img);
        }
    }
}
