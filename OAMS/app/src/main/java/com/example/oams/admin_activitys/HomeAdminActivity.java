package com.example.oams.admin_activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.example.oams.R;
import com.example.oams.adminFragment.HomeAdminFragment;
import com.example.oams.adminFragment.SectionAdminFragment;
import com.example.oams.adminFragment.StudentAdminFragment;
import com.example.oams.adminFragment.TeacherAdminFragment;
import com.example.oams.connection.SharedPrefManager;
import com.example.oams.login.MainActivity;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeAdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    public static FragmentManager fragmentManager;
    private long backPressedTime;
    private Toast backToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        Toolbar toolbar = findViewById(R.id.ad_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.ad_drawer_layout);
        NavigationView navigationView = findViewById(R.id.ad_nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.ad_fragment_container,
                    new HomeAdminFragment()).commit();
            navigationView.setCheckedItem(R.id.ad_nav_home);
            }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case  R.id.ad_nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.ad_fragment_container,
                        new HomeAdminFragment()).commit();
                break;
                case  R.id.ad_nav_section:
                getSupportFragmentManager().beginTransaction().replace(R.id.ad_fragment_container,
                        new SectionAdminFragment()).commit();
                break;
            case  R.id.ad_nav_teacher:
                getSupportFragmentManager().beginTransaction().replace(R.id.ad_fragment_container,
                        new TeacherAdminFragment()).commit();
                break;
            case  R.id.ad_nav_student:
                getSupportFragmentManager().beginTransaction().replace(R.id.ad_fragment_container,
                        new StudentAdminFragment()).commit();
                break;
            case R.id.ad_nav_logout:
                SharedPrefManager.getInstance(getApplicationContext()).
                logout();
                Intent intent = new Intent(HomeAdminActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else  {

        //super.onBackPressed();
        }
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.ad_fragment_container,
                    new HomeAdminFragment()).commit();
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }



}
