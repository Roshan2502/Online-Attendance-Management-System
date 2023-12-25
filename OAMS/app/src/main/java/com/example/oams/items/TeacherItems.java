package com.example.oams.items;

import com.example.oams.connection.Constants;

public class TeacherItems {

    private String teach_id;
    private String teach_name;
    private String teach_email;
    private String teach_grade;
    private String teach_img;

    public TeacherItems(String teach_id, String teach_name, String teach_email, String teach_grade, String teach_img) {
        this.teach_id = teach_id;
        this.teach_name = teach_name;
        this.teach_email = teach_email;
        this.teach_grade = teach_grade;
        this.teach_img = teach_img;
    }

    public String getTeach_id() {
        return teach_id;
    }

    public String getTeach_name() {
        return teach_name;
    }

    public String getTeach_email() {
        return teach_email;
    }

    public String getTeach_grade() {
        return teach_grade;
    }

    public String getTeach_img() {
        return Constants.ROOT_IMG +teach_img;
    }
}
