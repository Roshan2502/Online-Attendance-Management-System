package com.example.oams.adminFragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.oams.R;
import com.example.oams.admin_activitys.StudentAddActivity;
import com.example.oams.adapter.StudentAdapter;
import com.example.oams.connection.Constants;
import com.example.oams.connection.RequestHandler;
import com.example.oams.items.StdItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StudentAdminFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private List<StdItems> listStd;
    private RecyclerView.Adapter adapter;
    private ProgressDialog progressDialog;
    private Button ad_std_add;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_student,container,false);
        recyclerView = view.findViewById(R.id.rv_student_ad);
        final StudentAdapter recyclerViewAdapter  = new StudentAdapter(getContext(),listStd);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Wait...");
        ad_std_add = view.findViewById(R.id.ad_std_add);
        ad_std_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StudentAddActivity.class);
                startActivity(intent);
            }
        });
        swipeRefreshLayout = view.findViewById(R.id.swipeStd);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listStd.clear();
                StdData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return  view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listStd= new ArrayList<>();
//        StdData();

    }

    private void StdData() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading data....");
        progressDialog.show();
        StringRequest stringRequest =new StringRequest(Request.Method.POST, Constants.URL_STD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("data");
                            for (int i=0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                StdItems item = new StdItems(
                                        o.getString("student_id"),
                                        o.getString("student_name"),
                                        o.getString("student_roll_number"),
                                        o.getString("grade_name"));
                                listStd.add(item);

                            }
                            adapter = new StudentAdapter(getActivity(), listStd);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.getMessage()+ "Unable to connect server",Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "fetch");
                return params;
            }
        };
        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onStart() {
        super.onStart();
        listStd.clear();
        StdData();
    }
}

