package com.example.oams.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.oams.R;
import com.example.oams.adapter.ViewPagerAdapter;
import com.example.oams.admin_activitys.HomeAdminActivity;
import com.example.oams.connection.SharedPrefManager;
import com.example.oams.teacher_activitys.HomeActivity;
import com.google.android.material.tabs.TabLayout;

public class LoginTabActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(SharedPrefManager.getInstance(this).isLoggedin()) {
            String uname = SharedPrefManager.getUsername();
            switch (uname) {
                case "admin":
                    startActivity(new Intent(this, HomeAdminActivity.class));
                    finish();
                    break;
                default:
                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
            }
        }
        setContentView(R.layout.activity_login_tab);

        tabLayout = findViewById(R.id.tablayout_id_login);
        viewPager = findViewById(R.id.viewpager_id_login);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new FragmentLecturerLogin(), "");
        adapter.AddFragment(new FragmentAdminLogin(), "");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }
}
