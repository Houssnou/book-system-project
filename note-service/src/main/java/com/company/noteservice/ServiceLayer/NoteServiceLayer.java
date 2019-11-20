package com.company.noteservice.ServiceLayer;

import com.company.noteservice.dao.NoteDao;
import com.company.noteservice.dto.Note;
import com.company.noteservice.viewModel.NoteVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NoteServiceLayer {
    NoteDao noteDao;

    @Autowired
    public NoteServiceLayer(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    public NoteVM newNote(NoteVM noteVM) {
        Note note = new Note();
        note.setBookId(noteVM.getBookId());
        note.setNote(noteVM.getNote());
        note = noteDao.addNote(note);

        noteVM.setNoteId(note.getNoteId());
        return noteVM;
    }

    public NoteVM retrieveNote(int id) {
        Note note = noteDao.getNote(id);
        if (note == null) return null;

        return buildNoteVM(note);
    }

    public List<NoteVM> retrieveAllNotes() {
        List<Note> notes = noteDao.getAllNotes();
        if (notes.isEmpty() || notes == null) return null;

        List<NoteVM> nvmList = new ArrayList<>();
        noteDao.getAllNotes().stream().forEach(note -> nvmList.add(buildNoteVM(note)));

        return nvmList;
    }

    public List<NoteVM> retrieveNoteByBookId(int id) {
        List<Note> notes = noteDao.getByBookId(id);
        if (notes.isEmpty() || notes == null) return null;

        List<NoteVM> nvmList = new ArrayList<>();
        noteDao.getAllNotes().stream().forEach(note -> nvmList.add(buildNoteVM(note)));

        return nvmList;
    }

    public void updateNote(NoteVM noteVM) {

        Note note = new Note();
        note.setBookId(noteVM.getBookId());
        note.setNote(noteVM.getNote());

        noteDao.updateNote(note);
    }

    public void deleteNote(int id) {

        noteDao.deleteNote(id);
    }


    private NoteVM buildNoteVM(Note note) {

        NoteVM nvm = new NoteVM();
        nvm.setNoteId(note.getNoteId());
        nvm.setBookId(note.getBookId());
        nvm.setNote(note.getNote());

        return nvm;
    }

}
