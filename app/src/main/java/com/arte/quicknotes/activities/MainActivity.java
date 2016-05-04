package com.arte.quicknotes.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.arte.quicknotes.NotesDataSource;
import com.arte.quicknotes.R;
import com.arte.quicknotes.adapters.NotesAdapter;
import com.arte.quicknotes.models.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesAdapter.Events {

    private NotesAdapter mAdapter;
    private List<Note> mNoteListDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupActivity();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mensaje flotante
        //Toast.makeText(this, "Acaba de resumirse la actividad", Toast.LENGTH_SHORT).show();
    }

    private void setupActivity() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final Context context = this;
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, NoteActivity.class);
                    startActivityForResult(intent, 0);
                }
            });
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.notes_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mNoteListDB = new ArrayList<>(getDBContent());
        mAdapter = new NotesAdapter(mNoteListDB, this);

        if (recyclerView != null) {
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mNoteListDB.clear();
        mNoteListDB.addAll(getDBContent());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNoteClicked(Note note) {
        Intent intent = new Intent(this, NoteActivity.class);
        Bundle arguments = new Bundle();
        arguments.putSerializable(NoteActivity.PARAM_NOTE, note);
        intent.putExtras(arguments);
        startActivityForResult(intent, 0);
    }

    private List<Note> getDBContent() {
        NotesDataSource mDataSource = new NotesDataSource(this);
        Cursor c = mDataSource.getAllNotes();
        List<Note> noteListDB = new ArrayList<>();
        if(c != null) {
            while(c.moveToNext()) {
                Note noteDB = new Note();
                noteDB.setId((int) c.getLong(0));
                noteDB.setTitle(c.getString(1));
                noteDB.setContent(c.getString(2));
                noteListDB.add(noteDB);
            }
        }
        return noteListDB;
    }
}
