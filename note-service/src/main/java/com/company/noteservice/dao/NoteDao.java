package com.company.noteservice.dao;

import com.company.noteservice.dto.Note;

import java.util.List;

public interface NoteDao {

    Note addNote(Note note);

    Note getNote(int id);

    List<Note> getAllNotes();

    void updateNote(Note note);

    void deleteNote(int id);

    List<Note> getByBookId(int id);
}
