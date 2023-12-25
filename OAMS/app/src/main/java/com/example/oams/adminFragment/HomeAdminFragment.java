package com.example.oams.adminFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.oams.AdminBottamAttendaceReport;
import com.example.oams.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class HomeAdminFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Button allpresent;
    LinearLayout admin_bottam_report,admin_std,ad_teach_add;
    private TextView datetoday;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_home,container,false);
        admin_bottam_report = view.findViewById(R.id.admin_bottam_report);
        admin_bottam_report.setOnClickListener(this);

        datetoday = view.findViewById(R.id.datetoday);
        Date date = new Date();
        SimpleDateFormat dateFormatWithZone = new SimpleDateFormat("EEE dd-MM-yyyy", Locale.getDefault());
        String currentDate = dateFormatWithZone.format(date);
        datetoday.setText(currentDate);
        admin_std = view.findViewById(R.id.admin_std);
        admin_std.setOnClickListener(this);

        ad_teach_add = view.findViewById(R.id.ad_teach_add);
        ad_teach_add.setOnClickListener(this);

        return  view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.admin_bottam_report :
                AdminBottamAttendaceReport adminBottamAttendaceReport = new AdminBottamAttendaceReport();
                adminBottamAttendaceReport.show(getFragmentManager(),"bottam report");
                break;
            case  R.id.admin_std :
                getFragmentManager().beginTransaction().replace(R.id.ad_fragment_container,
                        new StudentAdminFragment()).commit();
                break;
            case R.id.ad_teach_add :
                getFragmentManager().beginTransaction().replace(R.id.ad_fragment_container,
                        new TeacherAdminFragment()).commit();
                break;
        }
    }
}

