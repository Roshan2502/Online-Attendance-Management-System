package com.example.oams.items;

public class Shortageitems {
        private String std_name;
        private String percentage;

    public Shortageitems(String std_name, String percentage) {
        this.std_name = std_name;
        this.percentage = percentage;
    }

    public String getStd_name() {
        return std_name;
    }

    public String getPercentage() {
        return percentage;
    }
}
