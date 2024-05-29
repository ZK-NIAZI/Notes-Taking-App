package com.example.notestaking;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private List<Note> notes = new ArrayList<>();
    private NoteAdapter noteAdapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView noteRecyclerView = findViewById(R.id.noteRecyclerView);

        registerForContextMenu(noteRecyclerView);
        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("Notes", Context.MODE_PRIVATE);

        // Load saved notes
        loadNotes();

        // Set up RecyclerView and FloatingActionButton
        noteAdapter = new NoteAdapter(notes);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteRecyclerView.setAdapter(noteAdapter);

        FloatingActionButton addNoteFab = findViewById(R.id.addNoteFab);
        addNoteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddNoteDialog();
            }
        });

    }



    private void showAddNoteDialog() {
        // Create and inflate the dialog layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_note, null);

        // Find views in the dialog layout
        EditText noteTitleEditText = dialogView.findViewById(R.id.noteTitleEditText);
        EditText noteEditText = dialogView.findViewById(R.id.noteEditText);
        Button addNoteButton = dialogView.findViewById(R.id.addNoteButton);

        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        // Set the onClickListener for the "Add Note" button in the dialog
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noteTitle = noteTitleEditText.getText().toString();
                String noteText = noteEditText.getText().toString();
                if (!noteTitle.isEmpty() && !noteText.isEmpty()) {
                    String currentDate = getCurrentDate();
                    String currentDay = getCurrentDay();
                    // Create a new note with date and day
                    Note newNote = new Note(noteTitle, noteText, currentDate, currentDay);
                    notes.add(newNote);
                    noteAdapter.notifyDataSetChanged();
                    saveNotes(); // Save notes
                    alertDialog.dismiss(); // Close the dialog
                }
            }
        });

        // Show the dialog
        alertDialog.show();
    }
    private String getCurrentDate() {
        // Get the current date in your desired format
        // Example: "2022-01-01"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    private String getCurrentDay() {
        // Get the current day (Monday, Tuesday, etc.)
        // Example: "Monday"
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        return dayFormat.format(new Date());
    }

    private void saveNotes() {
        // Convert the List<Note> to a JSON string
        Gson gson = new Gson();
        String json = gson.toJson(notes);

        // Save the JSON string in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("notes", json);
        editor.apply();
    }

    private void loadNotes() {
        // Retrieve the JSON string from SharedPreferences
        String json = sharedPreferences.getString("notes", "");

        // Convert the JSON string to a List<Note>
        Gson gson = new Gson();
        Type type = new TypeToken<List<Note>>(){}.getType();
        notes = gson.fromJson(json, type);

        if (notes == null) {
            notes = new ArrayList<>();
        }
    }
}