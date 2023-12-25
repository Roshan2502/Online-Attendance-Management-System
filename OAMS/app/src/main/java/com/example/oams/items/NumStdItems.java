package com.example.oams.items;

public class NumStdItems {
    private String ad_std_id,std_name, std_roll,std_phone, attendance_percentage,section;

    public NumStdItems(String ad_std_id, String std_name, String std_roll, String std_phone, String attendance_percentage, String section) {
        this.ad_std_id = ad_std_id;
        this.std_name = std_name;
        this.std_roll = std_roll;
        this.std_phone = std_phone;
        this.attendance_percentage = attendance_percentage;
        this.section = section;
    }

    public String getAd_std_id() {
        return ad_std_id;
    }

    public String getStd_name() {
        return std_name;
    }

    public String getStd_roll() {
        return std_roll;
    }

    public String getStd_phone() {
        return std_phone;
    }

    public String getAttendance_percentage() {
        return attendance_percentage;
    }

    public String getSection() {
        return section;
    }
}
