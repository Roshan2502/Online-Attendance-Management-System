package com.example.oams.admin_activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.oams.items.SubjectItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddTeacherActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private CircleImageView circleImageView;
    static  int PRegcode=1;
    static  int REQSCODE=1;
    Uri pickedimg ;
    List<SectionItem> depart;
    List<SubjectItem> subject;
    ArrayAdapter arrayAdapter, arrayAdaptersub;
    private Spinner spinner, spinnersub;
    private Button add_teach_btn;
    private EditText add_tech_name, add_tech_email,add_tech_pwd;
    public String gradeid, subid;
    private ProgressDialog progressDialog;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

//        getSupportActionBar().setTitle("sanit");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        add_tech_name  = findViewById(R.id.add_tech_name);
        add_tech_email = findViewById(R.id.add_tech_email);
        add_tech_pwd = findViewById(R.id.add_tech_pwd);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Wait...");

        circleImageView =  findViewById(R.id.profile_image);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 22){
                    permisstion();
                }else {
                    openGellary();
                }
            }
        });
        spinner = findViewById(R.id.spinnergrade);

        spinner.setOnItemSelectedListener(this);
        spinnersub = findViewById(R.id.spinnersub);
        spinnersub.setOnItemSelectedListener(this);
        final SectionItem sectionItem = new SectionItem("Select Section","");
        depart = new ArrayList<>();
        depart.add(sectionItem);
        department();

        SubjectItem subjectItem = new SubjectItem("", "Select Subject","");
        subject = new ArrayList<>();
        subject.add(subjectItem);
        subjectdata();

        add_teach_btn = findViewById(R.id.teach_add_btn);
        add_teach_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!add_tech_name.getText().toString().isEmpty()
                        && !add_tech_email.getText().toString().isEmpty()
                        && !add_tech_pwd.getText().toString().isEmpty()
                        && !gradeid.isEmpty()
                        && !subid.isEmpty()
                ){

                        addData();
                }else {
                    Toast.makeText(getApplicationContext(),"Please fill empty fields", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spinnergrade :
                SectionItem e = depart.get(position);
                gradeid = e.getId();
//                Toast.makeText(parent.getContext(),gradeid,Toast.LENGTH_LONG).show();
                break;
            case R.id.spinnersub:
                SubjectItem sub = subject.get(position);
                subid = sub.getSubjectid();
//                Toast.makeText(parent.getContext(),subid,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void addData() {
        final  String teach_name = add_tech_name.getText().toString().trim();
        final  String teach_email = add_tech_email.getText().toString().trim();
        final  String teach_pwd = add_tech_pwd.getText().toString().trim();
        final  String section = gradeid;
        final  String subject = subid;
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_TEACH_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getBoolean("error")){
                        Toast.makeText(AddTeacherActivity.this,
                                obj.getString("message"),
                                Toast.LENGTH_LONG
                        ).show();
//                        Intent intent = new Intent(StudentAddActivity.this, StudentAdminFragment.class);
//                        startActivity(intent);
                    }else {
                        Toast.makeText(AddTeacherActivity.this,
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
                        Toast.makeText(AddTeacherActivity.this,
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "Add");
                params.put("teacher_name", teach_name);
                params.put("teacher_emailid", teach_email);
                params.put("teacher_password", teach_pwd);
                params.put("teacher_grade_id", section);
                params.put("subjectid", subject);
                params.put("teacher_image", imgToString(bitmap));
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void subjectdata() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
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
                                SubjectItem item = new SubjectItem(
                                        o.getString("id"),
                                        o.getString("SubjectName"),
                                        o.getString("SubjectCode")
                                );
                                subject.add(item);
                            }
                            arrayAdaptersub = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,subject);
                            spinnersub.setAdapter(arrayAdaptersub);
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
                params.put("action", "subject");
                return params;
            }
        };

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
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

    private void openGellary() {

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQSCODE);
    }

    private void permisstion() {
        if (ContextCompat.checkSelfPermission(AddTeacherActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(AddTeacherActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(AddTeacherActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PRegcode);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            openGellary();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQSCODE && data != null ){
            pickedimg = data.getData();
//            circleImageView.setImageURI(pickedimg);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),pickedimg);
                circleImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private String imgToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG ,100,byteArrayOutputStream);
        byte[] imByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imByte,Base64.DEFAULT);
    }
}
