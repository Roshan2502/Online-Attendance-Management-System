package com.example.oams.admin_activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.oams.R;
import com.example.oams.connection.Constants;
import com.example.oams.connection.RequestHandler;
import com.example.oams.items.SectionItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.oams.R.layout.activity_student_add;

public class StudentAddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private TextView dateshow;
    private Button std_add_btn;
    public String ids;
    private  EditText add_std_name,add_std_rollno,add_std_phone;
    ProgressDialog progressDialog;
     Spinner spinner;
    List<SectionItem> depart;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Wait...");
        setContentView(activity_student_add);
        dateshow =findViewById(R.id.dateshow2);

        add_std_name = findViewById(R.id.add_std_name);
        add_std_rollno = findViewById(R.id.add_std_rollno);
        add_std_phone = findViewById(R.id.add_std_phone);

        dateshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        std_add_btn = findViewById(R.id.std_add_btn);
        std_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adddata();
            }
        });
        spinner = findViewById(R.id.spinner);

//        SectionItem sectionItem = new SectionItem("sanit","2");
//        SectionItem sectionItem1 = new SectionItem("sahil","2");
//        depart = new ArrayList<>();
//        depart.add(sectionItem);
//        depart.add(sectionItem1);
//        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,depart);
//        spinner.setAdapter(arrayAdapter);

        depart = new ArrayList<>();
        department();

//        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,depart);
//        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SectionItem e = depart.get(position);
                String empName = e.getSectionName();
                ids = e.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void adddata() {

        final  String add_std = add_std_name.getText().toString().trim();
        final  String add_rollno = add_std_rollno.getText().toString().trim();
        final  String add_phone = add_std_phone.getText().toString().trim();
        final  String add_date = dateshow.getText().toString().trim();
        final  String section = ids;
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_STD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")){


                        Toast.makeText(StudentAddActivity.this,
                                obj.getString("message"),
                                Toast.LENGTH_LONG
                        ).show();
//                        Intent intent = new Intent(StudentAddActivity.this, StudentAdminFragment.class);
//                        startActivity(intent);
                    }else {
                        Toast.makeText(StudentAddActivity.this,
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
                        progressDialog.dismiss();
                        Toast.makeText(StudentAddActivity.this,
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "Add");
                params.put("student_name", add_std);
                params.put("student_roll_number", add_rollno);
                params.put("student_dob", add_date);
                params.put("student_grade_id", section);
                params.put("phone_no", add_phone);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void department() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data....");
        progressDialog.show();
        StringRequest stringRequest =new StringRequest(Request.Method.POST, Constants.URL_GRADE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("grade");
                            for (int i=0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                SectionItem item = new SectionItem(o.getString("grade_name"),
                                        o.getString("grade_id"));
                                depart.add(item);
                            }
                            arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,depart);
                            spinner.setAdapter(arrayAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();
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

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
    private void  showDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String date = year + "-"+  month + "-"+ dayOfMonth;
        dateshow.setText(date);
    }
}
