package com.example.oams.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.oams.R;
import com.example.oams.admin_activitys.HomeAdminActivity;
import com.example.oams.connection.Constants;
import com.example.oams.connection.RequestHandler;
import com.example.oams.connection.SharedPrefManager;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class FragmentAdminLogin extends Fragment {

    View v;
    private EditText ad_uname, ad_pwd;
    private Button ad_btn;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_admin, container,false);
        ad_uname = v.findViewById(R.id.ad_uname);
        ad_pwd = v.findViewById(R.id.ad_pwd);
        ad_btn = v.findViewById(R.id.ad_btn);
        progressBar = v.findViewById(R.id.progressBaradmin);
        progressBar.setVisibility(View.GONE);
        ad_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminLogin();
            }
        });
        return v;
    }
    private void adminLogin(){
        final String uname = ad_uname.getText().toString().trim();
        final String pwd = ad_pwd.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);
        ad_btn.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_ADMIN_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")){

                        SharedPrefManager.getInstance(getContext())
                                .userlogin(
                                        obj.getString("id"),
                                        obj.getString("email"),
                                        obj.getString("username")
                                );

                        Toast.makeText(getContext(),"Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getContext(), HomeAdminActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(
                                getContext(),
                                obj.getString("message"),
                                Toast.LENGTH_LONG
                        ).show();
                        progressBar.setVisibility(View.GONE);
                        ad_btn.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(
                            getContext(),
                            e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(
                                getContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    ad_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("admin_user_name", uname);
                params.put("admin_password", pwd);
                return params;
            }
        };
        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

}
