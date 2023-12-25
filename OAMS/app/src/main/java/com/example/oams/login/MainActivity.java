package com.example.oams.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.oams.admin_activitys.HomeAdminActivity;
import com.example.oams.teacher_activitys.HomeActivity;
import com.example.oams.R;
import com.example.oams.connection.SharedPrefManager;

public class MainActivity extends AppCompatActivity {
    private Button login_btn;
    private TextView search_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedPrefManager.getInstance(this).isLoggedin()){
            String uname = SharedPrefManager.getUsername();
            switch (uname){
                case "admin":
                    startActivity(new Intent(this, HomeAdminActivity.class));
                    finish();
                    break;
                    default:
                        startActivity(new Intent(this, HomeActivity.class));
                        finish();
            }

            return;
        }
        login_btn = findViewById(R.id.login_btn);
        search_btn = findViewById(R.id.search_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginTabActivity.class);
                startActivity(intent);
            }
        });
        search_btn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent i = new  Intent( MainActivity.this, StudentActivity.class);
                  startActivity(i);
              }
          }

        );

    }
}
