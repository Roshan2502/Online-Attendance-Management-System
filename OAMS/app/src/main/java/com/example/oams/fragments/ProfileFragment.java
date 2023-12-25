package com.example.oams.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.oams.R;
import com.example.oams.connection.Constants;
import com.example.oams.connection.RequestHandler;
import com.example.oams.connection.SharedPrefManager;
import com.example.oams.items.TeacherItems;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProfileFragment extends Fragment {
    View v;

    private TextView user_name, user_email;
    private ProgressDialog progressDialog;
    private List<TeacherItems> listTech;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile, container, false);

        user_name = v.findViewById(R.id.user_name);
        user_email = v.findViewById(R.id.user_email);

        String userEmail = SharedPrefManager.getUserEmail();
        String userName = SharedPrefManager.getUsername();

        user_name.setText(userName);
        user_email.setText(userEmail);
        return v;
    }

}
