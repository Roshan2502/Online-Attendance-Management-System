package com.example.oams.items;

import androidx.annotation.NonNull;

public class SectionItem {
    private String sectionName;
    private String id;

    public SectionItem(String sectionName, String id) {
        this.sectionName = sectionName;
        this.id = id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getId() {
        return id;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return sectionName;
    }
}
