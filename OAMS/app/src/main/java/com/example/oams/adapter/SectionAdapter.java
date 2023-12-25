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
import com.example.oams.items.SectionItem;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder>{
    Context context;
    List<SectionItem> listItems;
    private ImageButton section_del_btn;
    private  String std_del;

    public SectionAdapter(Context context, List<SectionItem> listItems) {
        this.context = context;
        this.listItems = listItems;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_section,parent, false);
        final ViewHolder vHolder = new  ViewHolder(v);
        section_del_btn = v.findViewById(R.id.Section_del);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final SectionItem listItem =listItems.get(position);
        holder.sectionName.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transation));
        holder.linearLayout.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_sacle_animation));

        holder.sectionName.setText(listItem.getSectionName());
        section_del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                std_del = listItem.getId();
                deleSection();
                listItems.remove(position);
                notifyItemRemoved(position);
            }
        });

    }

    private void deleSection() {
        final String del_std = std_del.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_GRADE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")){


                        Toast.makeText(context,
                                std_del,
                                Toast.LENGTH_LONG
                        ).show();Toast.makeText(context,
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
                params.put("grade_id", del_std);
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView sectionName;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.section_item);
            sectionName = itemView.findViewById(R.id.section_name);
        }
    }

}
