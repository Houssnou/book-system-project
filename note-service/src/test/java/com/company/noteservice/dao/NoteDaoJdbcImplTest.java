package com.company.noteservice.dao;

import com.company.noteservice.dto.Note;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NoteDaoJdbcImplTest {
    @Autowired
    NoteDao noteDao;

    private Note note;

    @Before
    public void setUp() throws Exception {
        clearDatabase();
        setUpTestObjects();
    }

    public void setUpTestObjects(){
        note = new Note();
        note.setNote("This book is heavy");
        note.setBookId(1);
    }

    public void clearDatabase(){
        List<Note> notes = noteDao.getAllNotes();
        for (Note n : notes) {
            noteDao.deleteNote(n.getNoteId());
        }
    }

    @Test
    public void shouldAddAndGetAndDeleteNote() {

        note = noteDao.addNote(note);
        Note note1 = noteDao.getNote(note.getNoteId());
        // assert
        assertEquals(note1, note);

        noteDao.deleteNote(note.getNoteId());
        note1 = noteDao.getNote(note.getNoteId());
        assertNull(note1);
    }



    @Test
    public void shouldGetAllNotes() {

        note = noteDao.addNote(note);

        note = new Note();
        note.setNote("This book is missing pages");
        note.setBookId(123);
        note = noteDao.addNote(note);

      List<Note> notes = noteDao.getAllNotes();
        assertEquals(notes.size(), 2);
    }

    @Test
    public void updateNote() {
        note = noteDao.addNote(note);
        note.setNote("This note has been Revised");
        note.setBookId(127);
        note = noteDao.addNote(note);
        Note note1 = noteDao.getNote(note.getNoteId());
        assertEquals(note1, note);
    }

}