package com.company.noteservice.ServiceLayer;

import com.company.noteservice.dao.NoteDao;
import com.company.noteservice.dao.NoteDaoJdbcImpl;
import com.company.noteservice.dto.Note;
import com.company.noteservice.viewModel.NoteVM;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class NoteServiceLayerTest {

    NoteDao noteDao;
    NoteServiceLayer noteServiceLayer;

    @Before
    public void setUp() throws Exception {

        setNoteDaoMock();

        noteServiceLayer = new NoteServiceLayer(noteDao);
    }

    @Test
    public void shouldCreateAndRetrieveNote() {
        NoteVM noteVM = new NoteVM();
        noteVM.setBookId(10);
        noteVM.setNote("8th edition of book");

        noteVM = noteServiceLayer.newNote(noteVM);
        System.out.println(noteVM);
        NoteVM noteVM1 = noteServiceLayer.retrieveNote(noteVM.getNoteId());
        assertEquals(noteVM1, noteVM);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionIfEmptyFields() {
        noteServiceLayer.newNote(new NoteVM());
    }

    @Test
    public void shouldRetrieveAllNotes() {
        NoteVM noteVM = new NoteVM();
        noteVM.setBookId(10);
        noteVM.setNote("8th edition of book");

        noteVM = noteServiceLayer.newNote(noteVM);

        List<NoteVM> bookVMs = noteServiceLayer.retrieveAllNotes();
        assertEquals(bookVMs.size(), 1);
    }

    @Test
    public void shouldRetrieveAllNotesByBookId() {
        NoteVM noteVM = new NoteVM();
        noteVM.setBookId(10);
        noteVM.setNote("8th edition of book");

        noteVM = noteServiceLayer.newNote(noteVM);

        List<NoteVM> bookVMs = noteServiceLayer.retrieveNoteByBookId(10);
        assertEquals(bookVMs.size(), 1);
    }


    private void setNoteDaoMock() {
        noteDao = mock(NoteDaoJdbcImpl.class);

        Note noteToCreate = new Note();
        noteToCreate.setBookId(10);
        noteToCreate.setNote("8th edition of book");

        Note noteDB = new Note();
        noteDB.setNoteId(1);
        noteDB.setBookId(10);
        noteDB.setNote("8th edition of book");

        List<Note> notes = new ArrayList<>();
        notes.add(noteDB);

        doReturn(noteDB).when(noteDao).addNote(noteToCreate);
        doReturn(noteDB).when(noteDao).getNote(1);
        doReturn(notes).when(noteDao).getAllNotes();
        doReturn(notes).when(noteDao).getByBookId(10);
    }
}