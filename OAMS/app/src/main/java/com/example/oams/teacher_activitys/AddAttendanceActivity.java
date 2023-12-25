package com.example.oams.teacher_activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.oams.R;
import com.example.oams.adapter.ViewPagerAdapter;
import com.example.oams.fragments.FragmentCustom;
import com.example.oams.fragments.FragmentQuick;
import com.google.android.material.tabs.TabLayout;


public class AddAttendanceActivity extends AppCompatActivity  {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_add_attendance);
        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new FragmentQuick(), "");
        adapter.AddFragment(new FragmentCustom(), "");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.quickly);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_playlist_add_black_24dp);

    }

}
