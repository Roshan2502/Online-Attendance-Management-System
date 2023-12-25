package com.example.oams.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
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
import com.example.oams.R;
import com.example.oams.connection.Constants;
import com.example.oams.connection.RequestHandler;
import com.example.oams.items.StdItems;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    Context context;
    List<StdItems> listStd;
    private ImageButton std_del_btn;
    private  String std_del;

    public StudentAdapter(Context context, List<StdItems> listStd) {
        this.context = context;
        this.listStd = listStd;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.std_list_admin,parent, false);
        final ViewHolder vHolder = new ViewHolder(v);
        std_del_btn = v.findViewById(R.id.std_del_btn);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final StdItems listItem =listStd.get(position);
        holder.ad_std_name.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transation));
        holder.linearLayout.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_sacle_animation));

        holder.ad_std_name.setText(listItem.getStd_name());
        holder.ad_std_roll.setText(listItem.getStd_roll());
        holder.ad_std_section.setText(listItem.getStd_section());

        std_del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                std_del = listItem.getAd_std_id();
                deleStd();
                listStd.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    private void deleStd() {
        final String del_std = std_del.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_STD, new Response.Listener<String>() {
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
                params.put("student_id", del_std);
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

    @Override
    public int getItemCount() {
        return listStd.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ad_std_name,ad_std_roll,ad_std_section;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.std_list);
            ad_std_name = itemView.findViewById(R.id.ad_std_name);
            ad_std_roll = itemView.findViewById(R.id.ad_std_roll);
            ad_std_section = itemView.findViewById(R.id.ad_std_section);
        }
    }
}
