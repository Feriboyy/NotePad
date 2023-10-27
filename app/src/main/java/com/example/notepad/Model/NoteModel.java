package com.example.notepad.Model;

// A MODEL to define the structure of a note : title & body

public class NoteModel {
    private String title;
    private String body;

    public NoteModel(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return title;
    }
}
