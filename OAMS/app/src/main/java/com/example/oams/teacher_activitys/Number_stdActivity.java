package com.example.oams.teacher_activitys;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.oams.R;
import com.example.oams.adapter.NumStudentAdapter;
import com.example.oams.connection.Constants;
import com.example.oams.connection.RequestHandler;
import com.example.oams.connection.SharedPrefManager;
import com.example.oams.items.NumStdItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Number_stdActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<NumStdItems> listStd;
    private RecyclerView.Adapter adapter;
    private ProgressDialog progressDialog;
    private static final int REQUEST_CALL = 1;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_std);
        listStd= new ArrayList<>();
        std_data();
        recyclerView = findViewById(R.id.nu_std_rv);
        NumStudentAdapter recyclerViewAdapter  = new NumStudentAdapter(Number_stdActivity.this,listStd);
        recyclerView.setLayoutManager(new LinearLayoutManager(Number_stdActivity.this));
        recyclerView.setAdapter(recyclerViewAdapter);
        userId = SharedPrefManager.getUserId();

//        listStd.add(new NumStdItems("1","sanit","114cs17038","cs","90087"));
//        listStd.add(new NumStdItems("1","sanit","114cs17038","cs","90087"));
//        listStd.add(new NumStdItems("1","sanit","114cs17038","cs","90087"));
//        adapter = new NumStudentAdapter(Number_stdActivity.this, listStd);
//        recyclerView.setAdapter(adapter);
    }


    private void std_data() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data....");
        progressDialog.show();
        StringRequest stringRequest =new StringRequest(Request.Method.POST, Constants.URL_ADDATN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("data");
                            for (int i=0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                NumStdItems item = new NumStdItems(
                                        o.getString("student_id"),
                                        o.getString("student_name"),
                                        o.getString("student_roll_number"),
                                        o.getString("phone_no"),
                                        o.getString("attendance_percentage"),
                                        o.getString("Section")
                                );
                                listStd.add(item);
                            }
                            adapter = new NumStudentAdapter(Number_stdActivity.this, listStd);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Number_stdActivity.this,"error"+e,Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Number_stdActivity.this, error.getMessage()+ "Unable to connect server",Toast.LENGTH_LONG).show();
            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "fetch");
                params.put("teacher_id", userId);
                return params;
            }
        };

        RequestHandler.getInstance(Number_stdActivity.this).addToRequestQueue(stringRequest);
    }

}
