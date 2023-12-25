package com.example.oams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.oams.connection.Constants;
import com.example.oams.login.StudentActivity;
import com.facebook.shimmer.ShimmerFrameLayout;

public class StudentAttendanceActivity extends AppCompatActivity {
    private  WebView webView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ShimmerFrameLayout mShimmerViewContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        webView = findViewById(R.id.webview2);
        swipeRefreshLayout = findViewById(R.id.STswiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadweb();
             swipeRefreshLayout.setRefreshing(false);
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        });


        loadweb();


    }

    private void loadweb() {
        Intent intent = getIntent();
        String fdate = intent.getStringExtra(StudentActivity.EXTRA_FDATE);
        String todate = intent.getStringExtra(StudentActivity.EXTRA_TODATE);
        String reg = intent.getStringExtra(StudentActivity.EXTRA_REG);
        webView.loadUrl(Constants.ROOT_IP+"android_attendence/studentreport.php?action=student_report&student_roll_number="+reg+"&from_date="+fdate+"&to_date="+todate);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            mShimmerViewContainer.stopShimmer();
            mShimmerViewContainer.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShimmerViewContainer.stopShimmer();
    }
}
