package com.arte.quicknotes;

import com.arte.quicknotes.models.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio on 27/4/16.
 */
public class NoteListMock {

    private static List<Note> noteList;

    public static List<Note> getList() {
        if (noteList == null) {
            createList();
        }
        return noteList;
    }

    public static void addNote(Note note) {
        noteList.add(note);
    }

    private static void createList() {
        noteList = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Note note = new Note();
            note.setTitle("Note #" + (i + 1));
            note.setContent("sadjfiisjdfiaijsdfi sidfjisjdfijsidfjijsdif adsfasdfijsdifj aidsjfiasdfiasd isdjfijasidfj iajsdfijsaidfjia idsjfijaijdfiii aidjidjifj");
            noteList.add(note);
        }
    }
}
