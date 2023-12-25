package com.example.oams.items;

public class StdItems {
    private String ad_std_id,std_name, std_roll, std_section;

    public StdItems(String ad_std_id, String std_name, String std_roll, String std_section) {
        this.ad_std_id = ad_std_id;
        this.std_name = std_name;
        this.std_roll = std_roll;
        this.std_section = std_section;
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

    public String getStd_section() {
        return std_section;
    }
}
