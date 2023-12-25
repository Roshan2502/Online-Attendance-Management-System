package com.example.oams.adminFragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.example.oams.admin_activitys.AddTeacherActivity;
import com.example.oams.R;
import com.example.oams.adapter.TeacherAdapter;
import com.example.oams.connection.Constants;
import com.example.oams.connection.RequestHandler;
import com.example.oams.items.TeacherItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TeacherAdminFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private List<TeacherItems> listTech;
    private RecyclerView.Adapter adapter;
    private ProgressDialog progressDialog;
    private Button teach_add;
    private ImageButton teacher_del;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_teacher,container,false);
        recyclerView = view.findViewById(R.id.rv_teacher_ad);

//        listTech= new ArrayList<>();
//        teachData();
        final TeacherAdapter recyclerViewAdapter  = new TeacherAdapter(getContext(),listTech);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);

        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listTech.clear();
                teachData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Wait...");
        teach_add = view.findViewById(R.id.teach_add);
        teach_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddTeacherActivity.class);
                startActivity(intent);
            }
        });

        return  view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listTech= new ArrayList<>();
    }

    private void teachData() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading data....");
        progressDialog.show();
        StringRequest stringRequest =new StringRequest(Request.Method.POST, Constants.URL_TEACH_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("data");
                            for (int i=0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                TeacherItems item = new TeacherItems(
                                        o.getString("teacher_id"),
                                        o.getString("teacher_name"),
                                        o.getString("teacher_emailid"),
                                        o.getString("grade_name"),
                                        o.getString("teacher_image")
                                );
                                listTech.add(item);

                            }
                            adapter = new TeacherAdapter(getActivity(), listTech);
                            recyclerView.setAdapter(adapter);



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
        listTech.clear();
        listTech= new ArrayList<>();
        teachData();
    }
}

