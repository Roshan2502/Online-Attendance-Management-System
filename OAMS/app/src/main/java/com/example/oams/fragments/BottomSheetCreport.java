package com.example.oams.fragments;

import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.oams.R;
import com.example.oams.connection.Constants;
import com.example.oams.connection.SharedPrefManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.Calendar;


public class BottomSheetCreport extends BottomSheetDialogFragment  implements DatePickerDialog.OnDateSetListener, View.OnClickListener{
    private TextView selectfdate,selectto;
    private Button crt_btn;
    private ProgressDialog progressDialog;
    private  DownloadManager downloadManager;
    private String userId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.createreport,container, false);
        selectfdate = v.findViewById(R.id.selectfdate);
        selectfdate.setOnClickListener(this);
        selectto = v.findViewById(R.id.selectto);
        selectto.setOnClickListener(this);
        crt_btn = v.findViewById(R.id.crt_btn);
        crt_btn.setOnClickListener(this);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Started Download");
        userId = SharedPrefManager.getUserId();
        return v;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectfdate:
                showDatePicker();
                break;
            case R.id.selectto:
                Enddate enddate = new Enddate();
                enddate.endDatePicker();
                break;
            case R.id.crt_btn:
                pdfdown();
                progressDialog.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 3000);
                break;
        }
    }
    private void  showDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String date = year + "-"+  (month + 1) + "-"+ dayOfMonth;
        selectfdate.setText(date);
    }




    private void pdfdown() {

        downloadManager = (DownloadManager) getContext().getSystemService(getContext().DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(Constants.ROOT_PDF+"action=attendance_report&from_date="+selectfdate.getText().toString()+"&to_date="+selectto.getText().toString()+"&teacher_id="+userId);
        DownloadManager.Request request =new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long referance = downloadManager.enqueue(request);

    }

    public class Enddate implements DatePickerDialog.OnDateSetListener{
      private void  endDatePicker(){
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
          selectto.setText(date);

      }
  }
}
