package com.example.notepad.Controller;

// CONTROLLER
// A new activity to save a new note or editing and deleting of existing notes
// Each note contains of two parts : title and body

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notepad.Model.DataManager;
import com.example.notepad.Model.NoteModel;
import com.example.notepad.R;

public class NewNoteActivity extends AppCompatActivity {

    EditText titleInput, bodyInput;
    ImageButton saveButton, deleteButton;
    DataManager dataManager;
    String title, originalTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        dataManager = new DataManager(this); // Initialize the DataManager

        titleInput = findViewById(R.id.title_input); // Getting a title for the note
        bodyInput = findViewById(R.id.body_input); // Getting note's body

        title = getIntent().getStringExtra("title");
        originalTitle = title;

        if (title != null) {
            // Editing an existing note
            titleInput.setText(title);
            String body = getIntent().getStringExtra("body");
            bodyInput.setText(body);
        }
        // A Button to save/edit a note
        saveButton = findViewById(R.id.save_note_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTitle = titleInput.getText().toString();
                String newBody = bodyInput.getText().toString();
                // A note cannot be saved if the title is empty
                if (!newTitle.isEmpty()) {
                    NoteModel note = new NoteModel(newTitle, newBody);
                    dataManager.saveNote(note);

                    if (!newTitle.equals(originalTitle)) {
                        dataManager.deleteNote(originalTitle);
                    }
                    Toast.makeText(NewNoteActivity.this,
                            "Note Saved", Toast.LENGTH_SHORT).show();

                    finish();
                } else {
                    // Show a toast message if the title is empty
                    Toast.makeText(NewNoteActivity.this,
                            "Title cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // A button to delete an existing note
        deleteButton = findViewById(R.id.delete_btn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (originalTitle != null) {
                    // Create an AlertDialog to get confirmation
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(NewNoteActivity.this);
                    builder.setTitle("Confirmation");
                    builder.setMessage("Are you sure you want to delete this note?");

                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // User confirmed deletion, so delete the note
                            dataManager.deleteNote(originalTitle);
                            Toast.makeText(NewNoteActivity.this,
                                    "Note Deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

                    // You may have a cancel option by uncommenting these lines, OPTIONAL

//                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // User canceled the deletion, so close the dialog
//                            dialog.dismiss();
//                        }
//                    });

                    builder.create().show();
                }
            }
        });
    }

}