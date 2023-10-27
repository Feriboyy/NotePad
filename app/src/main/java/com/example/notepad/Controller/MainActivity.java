package com.example.notepad.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notepad.Model.DataManager;
import com.example.notepad.Model.NoteModel;
import com.example.notepad.Model.TitleAdapter;
import com.example.notepad.R;

import java.util.ArrayList;


// I have used MVC architecture
// CONTROLLER
// In MainActivity, there is a list of all current note.
// By clicking on each item in the list, there we can see title and body of each note and edit them.
// A button is defined to create a new note.

public class MainActivity extends AppCompatActivity {

    ListView noteList;
    ImageButton createNoteButton;
    ArrayList<NoteModel> notes;
    TitleAdapter adapter;
    DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataManager = new DataManager(this); // Initialize a Model
        notes = new ArrayList<>(); // Initialize the list of notes
        adapter = new TitleAdapter(this, notes, dataManager);

        noteList = findViewById(R.id.lv_titles);
        noteList.setAdapter(adapter);

        createNoteButton = findViewById(R.id.new_note_btn);
        createNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch NewNoteActivity for creating a new note
                Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                startActivity(intent);
            }
        });

        noteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Retrieve the selected note
                NoteModel selectedNote = notes.get(position);

                // Launch NoteActivity to display and edit the note
                Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
                intent.putExtra("title", selectedNote.getTitle());
                intent.putExtra("body", selectedNote.getBody());
                startActivity(intent);
            }
        });

        loadNotes(); // Load notes when the activity is created
    }
    // Method to load notes and update the ListView
    private void loadNotes() {
        notes.clear();
        notes.addAll(dataManager.loadNotes());
        adapter.notifyDataSetChanged();
    }
    // Reloading notes when resuming to MainActivity
    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }
}