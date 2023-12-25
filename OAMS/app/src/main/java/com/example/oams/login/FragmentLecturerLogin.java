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
import com.example.oams.connection.Constants;
import com.example.oams.connection.RequestHandler;
import com.example.oams.connection.SharedPrefManager;
import com.example.oams.teacher_activitys.HomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class FragmentLecturerLogin extends Fragment {
    ProgressBar progressBar;
    View v;
    private Button lc_btn;

    private EditText lc_email, lc_pwd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_login, container,false);
        lc_email = v.findViewById(R.id.lc_email);
        lc_pwd = v.findViewById(R.id.lc_pwd);
        lc_btn = v.findViewById(R.id.lc_btn);
        progressBar =v.findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);
        lc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        return v;
    }
    private void userLogin(){
        final String email = lc_email.getText().toString().trim();
        final String pwd = lc_pwd.getText().toString().trim();
                progressBar.setVisibility(View.VISIBLE);
                lc_btn.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_LOGIN, new Response.Listener<String>() {
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


                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(
                                getContext(),
                                obj.getString("message"),
                                Toast.LENGTH_LONG
                        ).show();
                        progressBar.setVisibility(View.GONE);
                        lc_btn.setVisibility(View.VISIBLE);
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
                        lc_btn.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("teacher_emailid", email);
                params.put("teacher_password", pwd);
                return params;
            }
        };
        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

}
