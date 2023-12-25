package com.example.oams.teacher_activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.oams.R;
import com.example.oams.adapter.ShortageAdapter;
import com.example.oams.connection.Constants;
import com.example.oams.connection.RequestHandler;
import com.example.oams.connection.SharedPrefManager;
import com.example.oams.items.Shortageitems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShortageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Shortageitems> listItems;
    private ProgressDialog progressDialog;
    private String userId;
    private Button send_alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortage);
        recyclerView = findViewById(R.id.rv_shortage);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShortageActivity.this));

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Wait...");
        listItems = new ArrayList<>();
        //listItems.add(new Shortageitems("sanit","75"));
      loadRvdata();
        userId = SharedPrefManager.getUserId();

        send_alert = findViewById(R.id.send_alert);
        send_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_alert();
            }
        });
    }

    private void send_alert() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data....");
        progressDialog.show();
        StringRequest stringRequest =new StringRequest(Request.Method.POST, Constants.URL_SHORTAGE_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), "Successfully sended", Toast.LENGTH_LONG).show();

                            }else {
                                Toast.makeText(getApplicationContext(), "Unable to send", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ShortageActivity.this,"error"+e,Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ShortageActivity.this, error.getMessage()+ "Unable to connect server",Toast.LENGTH_LONG).show();
            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "alert");
                params.put("teacher_id", userId);
                return params;
            }
        };

        RequestHandler.getInstance(ShortageActivity.this).addToRequestQueue(stringRequest);

    }

    private void loadRvdata() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data....");
        progressDialog.show();
        StringRequest stringRequest =new StringRequest(Request.Method.POST, Constants.URL_SHORTAGE_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("data");
                            for (int i=0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                Shortageitems item = new Shortageitems(o.getString("student_name"),
                                        o.getString("percentage"));
                                listItems.add(item);
                            }
                            adapter = new ShortageAdapter(getBaseContext(), listItems);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ShortageActivity.this,"error"+e,Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ShortageActivity.this, error.getMessage()+ "Unable to connect server",Toast.LENGTH_LONG).show();
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

        RequestHandler.getInstance(ShortageActivity.this).addToRequestQueue(stringRequest);

    }
}
