package com.arte.quicknotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.arte.quicknotes.database.NotesDBHelper;
import com.arte.quicknotes.models.Note;

/**
 * Created by Sergio on 4/5/16.
 */
public class NotesDataSource {

    private static SQLiteDatabase db;
    private NotesDBHelper dbHelper;
    private boolean dbEmpty = true;

    public NotesDataSource(Context context) {
        dbHelper = new NotesDBHelper(context);
        if(dbEmpty) {
            populate();
            dbEmpty = false;
        }
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createNote(String title, String content) {
        ContentValues values = getContentValues(title, content);
        db.insert(NotesDBHelper.NoteEntry.TABLE_NAME, null, values);
    }

    public static void updateNote(long id, String title, String content) {
        ContentValues values = getContentValues(title, content);
        String whereClause = NotesDBHelper.NoteEntry._ID + " = ?";
        String[] args = { String.valueOf(id) };
        db.update(NotesDBHelper.NoteEntry.TABLE_NAME, values, whereClause, args);
    }

    public static void deleteNote(Note note) {
        long deleteId = note.getId();
        String[] args = { String.valueOf(deleteId) };
        db.delete(NotesDBHelper.NoteEntry.TABLE_NAME,
                NotesDBHelper.NoteEntry._ID + " = ?",
                args);
    }

    private void populate() {
        for (int i = 0; i < 3; i++) {
            String title = "Note";
            String content = "sadjfiisjdfiaijsdfi sidfjisjdfijsidfjijsdif adsfasdfijsdifj aidsjfiasdfiasd isdjfijasidfj iajsdfijsaidfjia idsjfijaijdfiii aidjidjifj";
            createNote(title, content);
        }
    }

    public void deleteAllNotes() {
        db.delete(NotesDBHelper.NoteEntry.TABLE_NAME, null, null);
    }

    public Cursor getAllNotes() {
        String[] projection = {
                NotesDBHelper.NoteEntry._ID,
                NotesDBHelper.NoteEntry.COLUMN_NAME_TITLE,
                NotesDBHelper.NoteEntry.COLUMN_NAME_CONTENT
        };
        String sortOrder =
                NotesDBHelper.NoteEntry._ID + " DESC";

        return db.query(
                NotesDBHelper.NoteEntry.TABLE_NAME,     // The table to query
                projection,                             // The columns to return
                null,                                   // The columns for the WHERE clause
                null,                                   // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                sortOrder                               // The sort order
        );
    }

    private static ContentValues getContentValues(String title, String content) {
        ContentValues values = new ContentValues();
        values.put(NotesDBHelper.NoteEntry.COLUMN_NAME_TITLE, title);
        values.put(NotesDBHelper.NoteEntry.COLUMN_NAME_CONTENT, content);
        return values;
    }

}
