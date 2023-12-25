package com.example.oams.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oams.R;
import com.example.oams.StudentAttendanceActivity;

import java.util.Calendar;

public class StudentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    public static final String EXTRA_FDATE = "com.example.oams.EXTRA_FDATE";
    public static final String EXTRA_TODATE = "com.example.oams.EXTRA_TODATE";
    public static final String EXTRA_REG = "com.example.oams.EXTRA_REG";
    ImageView arrow;
    TextView STselectfdate, STselectto;
    EditText STreg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        arrow = findViewById(R.id.arrow);
        STselectfdate = findViewById(R.id.STselectfdate);
        STselectfdate.setOnClickListener(this);
        STselectto = findViewById(R.id.STselectto);
        STselectto.setOnClickListener(this);
        arrow.setOnClickListener(this);
        STreg = findViewById(R.id.Streg);
        Button button = findViewById(R.id.Stbtn);
        button.setOnClickListener(this);
    }
    private void  showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(StudentActivity.this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = year + "-"+(month + 1)+ "-"+ dayOfMonth;
        STselectfdate.setText(date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.STselectfdate :
                showDatePicker();
                break;
            case R.id.STselectto :
                EndDate enddate = new EndDate();
                enddate.endDatePicker();
            break;
            case R.id.arrow :
                Intent in = new Intent(StudentActivity.this, MainActivity.class);
                startActivity(in);
                break;
                case R.id.Stbtn :
                    Intent intent = new Intent(StudentActivity.this, StudentAttendanceActivity.class);
                    intent.putExtra(EXTRA_FDATE,STselectfdate.getText().toString());
                    intent.putExtra(EXTRA_TODATE,STselectto.getText().toString());
                    intent.putExtra(EXTRA_REG,STreg.getText().toString());
                    startActivity(intent);
                    break;
        }
    }
    public class EndDate implements DatePickerDialog.OnDateSetListener{
        private void  endDatePicker(){
            DatePickerDialog datePickerDialog = new DatePickerDialog(StudentActivity.this, this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String date = year + "-"+(month + 1)+ "-"+ dayOfMonth;
            STselectto.setText(date);

        }
    }
}

