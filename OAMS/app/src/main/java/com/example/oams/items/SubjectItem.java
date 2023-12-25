package com.example.oams.items;

import androidx.annotation.NonNull;

public class SubjectItem {
    private String subjectid;
    private String subjectname;
    private String subjectCode;

    public SubjectItem(String subjectid, String subjectname, String subjectCode) {
        this.subjectid = subjectid;
        this.subjectname = subjectname;
        this.subjectCode = subjectCode;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    @NonNull
    @Override
    public String toString() {
        return subjectname;
    }
}
