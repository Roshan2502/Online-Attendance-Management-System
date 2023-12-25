package com.example.oams.fragments;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.oams.R;
import com.example.oams.connection.Constants;
import com.example.oams.connection.SharedPrefManager;

import java.util.Calendar;

public class FragmentCustom extends Fragment implements DatePickerDialog.OnDateSetListener {
    private String userId;
    private TextView dateshow;
    private WebView webView;
    ProgressBar progressBar;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_custom, container,false);

        dateshow = v.findViewById(R.id.dateshow_multi);
        progressBar = v.findViewById(R.id.progressBar);
        progressBar.setMax(100);
        Toast.makeText(getActivity(),"Select date first",Toast.LENGTH_LONG).show();
        webView = v.findViewById(R.id.webview);
        webView.loadUrl(Constants.ROOT_IP+"android_attendence/add_multi_attendance.php?date="+dateshow.getText().toString()+"&id="+userId);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });

        dateshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = SharedPrefManager.getUserId();

    }

    private void  showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this,
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
        webView.reload();
        webView.loadUrl(Constants.ROOT_IP+"android_attendence/add_multi_attendance.php?date="+dateshow.getText().toString()+"&id="+userId);
    }
}
