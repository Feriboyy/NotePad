package com.example.notepad.Model;

// MODEL

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataManager {
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "Notes";
    // Constructor for the controller
    public DataManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveNote(NoteModel note) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(note.getTitle(), note.getBody());
        editor.apply();
    }
    // Delete an existing Note
    public void deleteNote(String title) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(title);
        editor.apply();
    }
    // Method to load all notes from SharedPreferences and return them as a list
    public List<NoteModel> loadNotes() {
        // Restore all the notes from SharedPreferences
        Map<String, ?> allNotes = sharedPreferences.getAll();
        // Initializing an empty ArrayList called notes to store the loaded notes.
        List<NoteModel> notes = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allNotes.entrySet()) {
            // Iterate through all stored notes
            NoteModel note = new NoteModel(entry.getKey(), entry.getValue().toString());
            notes.add(note);
        }
        return notes;
    }
}