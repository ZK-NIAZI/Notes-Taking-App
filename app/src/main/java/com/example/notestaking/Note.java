package com.example.notestaking;

// Update Note.java
public class Note {
    private String title;
    private String text;

    private String date; // New field for date
    private String day;

    public Note(String title, String text,String date, String day) {
        this.title = title;
        this.text = text;
        this.date=date;
        this.day=day;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }
}


