package com.example.oams;

import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.oams.connection.Constants;
import com.example.oams.connection.RequestHandler;
import com.example.oams.items.SectionItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminBottamAttendaceReport extends BottomSheetDialogFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {
    LinearLayout admin_bottam_report; private boolean startOrEnd = true;
    List<SectionItem> depart;
    ArrayAdapter arrayAdapter;
     private Spinner spinnergrade_admin,spinnerANNEXURE;
    private TextView ad_selectfdate,ad_selectto;
    private String gradeid;
    private DownloadManager downloadManager;
    private String ANNEXURE;
    private Button admin_repot_btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_report, container, false);
        admin_bottam_report = view.findViewById(R.id.admin_bottam_report);
         spinnerANNEXURE = (Spinner) view.findViewById(R.id.ANNEXURE_SPINNER);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.ANNEXURE, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerANNEXURE.setAdapter(adapter);
        spinnerANNEXURE.setOnItemSelectedListener(this);
        ad_selectfdate = view.findViewById(R.id.ad_selectfdate);
        ad_selectfdate.setOnClickListener(this);
        ad_selectto = view.findViewById(R.id.ad_selectto);
        ad_selectto.setOnClickListener(this);



        spinnergrade_admin = view.findViewById(R.id.spinnergrade_admin);
        admin_repot_btn = view.findViewById(R.id.admin_repot_btn);
        admin_repot_btn.setOnClickListener(this);

        spinnergrade_admin.setOnItemSelectedListener(this);

        final SectionItem sectionItem = new SectionItem("Select Section","");
        depart = new ArrayList<>();
        depart.add(sectionItem);
        department();

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spinnergrade_admin :
                SectionItem e = depart.get(position);
                gradeid = e.getId();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ad_selectfdate :
                showDatePickerDialog();
                startOrEnd = true;
                break;
            case R.id.ad_selectto :
                showDatePickerDialog();
                startOrEnd = false;
                break;
            case R.id.admin_repot_btn :
                xsldown();
                break;
        }
    }

        private void  showDatePickerDialog(){
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String date = year + "-"+(month + 1)+ "-"+ dayOfMonth;
            if (startOrEnd) {
                ad_selectfdate.setText(date);
            } else {
                ad_selectto.setText(date);
            }
        }
    private void department() {

        StringRequest stringRequest =new StringRequest(Request.Method.POST, Constants.URL_GRADE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("grade");
                            for (int i=0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                SectionItem item = new SectionItem(o.getString("grade_name"),
                                        o.getString("grade_id"));
                                depart.add(item);
                            }
                            arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,depart);
                            spinnergrade_admin.setAdapter(arrayAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "Fetch");
                return params;
            }
        };

        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
    private void xsldown() {

        downloadManager = (DownloadManager) getContext().getSystemService(getContext().DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(Constants.ROOT_XSL+"action=attendance_report&from_date="+ad_selectfdate.getText().toString()+"&to_date="+ad_selectto.getText().toString()+"&gradeid="+gradeid+"&ANNEXURE="+spinnerANNEXURE.getSelectedItem());
        DownloadManager.Request request =new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long referance = downloadManager.enqueue(request);
        Toast.makeText(getActivity(),"Started Download",Toast.LENGTH_LONG).show();

    }
}
