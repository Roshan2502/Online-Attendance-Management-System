package com.example.oams.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.example.oams.connection.Constants;
import com.example.oams.connection.RequestHandler;
import com.example.oams.connection.SharedPrefManager;
import com.example.oams.items.Dashboard;
import com.example.oams.teacher_activitys.Number_stdActivity;
import com.example.oams.teacher_activitys.AddAttendanceActivity;
import com.example.oams.R;
import com.example.oams.teacher_activitys.ShortageActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Button add_atten;
    private LinearLayout linearLayout,createreport,numberofstd;
    private RelativeLayout sms_balance,avg_attendance, shortage_dash;
    private ProgressBar avg_attendance_prog,shortage_pro,sms_balance_pro;
    private  TextView sms_balance_text,shortage_text,avg_attendance_text;
    private int percentage,wallet,shortage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        add_atten = view.findViewById(R.id.add_atten);
        add_atten.setOnClickListener(this);
        linearLayout = view.findViewById(R.id.shortage);
        linearLayout.setOnClickListener(this);

        createreport = view.findViewById(R.id.createreport);
        createreport.setOnClickListener(this);
        numberofstd = view.findViewById(R.id.numberofstd);
        numberofstd.setOnClickListener(this);

//        dashboard

        shortage_dash = view.findViewById(R.id.shortage_dash);
        sms_balance = view.findViewById(R.id.sms_balance);
        avg_attendance = view.findViewById(R.id.avg_attendance);

        avg_attendance_prog = view.findViewById(R.id.avg_attendance_prog);
        shortage_pro = view.findViewById(R.id.shortage_pro);
        sms_balance_pro = view.findViewById(R.id.sms_balance_pro);




        avg_attendance_text = view.findViewById(R.id.avg_attendance_text);
        shortage_text = view.findViewById(R.id.shortage_text);
        sms_balance_text = view.findViewById(R.id.sms_balance_text);


dash_data();

        return  view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onClick(View view1) {
        switch (view1.getId()){
            case R.id.add_atten :
                Intent intent = new Intent(getContext(), AddAttendanceActivity.class);
                startActivity(intent);
                break;
            case R.id.shortage :
                Intent in = new Intent(getContext(), ShortageActivity.class);
                startActivity(in);
                break;
                case R.id.numberofstd :
                Intent ins = new Intent(getContext(), Number_stdActivity.class);
                startActivity(ins);
                break;
            case R.id.createreport :
                BottomSheetCreport bottomSheetCreport= new BottomSheetCreport();
                bottomSheetCreport.show(getFragmentManager(),"bottomSheetCreport");
                break;
        }
    }
    private void dash_data() {
        StringRequest stringRequest =new StringRequest(Request.Method.POST, Constants.URL_DASH_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            avg_attendance_prog.setProgress(Integer.parseInt(jsonObject.getString("percentage")));
                            shortage_pro.setProgress(Integer.parseInt(jsonObject.getString("shortage")));
                            sms_balance_pro.setProgress(Integer.parseInt(jsonObject.getString("sms")));


                            avg_attendance_text.setText(jsonObject.getString("percentage")+"%");
                            shortage_text.setText(jsonObject.getString("shortage"));
                            sms_balance_text.setText(jsonObject.getString("sms"));

//                            Toast.makeText(getContext(),jsonObject.getString("percentage"),Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),"error"+e,Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage()+ "Unable to connect server",Toast.LENGTH_LONG).show();
            }
        }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put("action", "fetch");
                params.put("teacher_id", SharedPrefManager.getUserId());
                return params;
            }
        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
}