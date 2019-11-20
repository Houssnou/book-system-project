package com.company.noteservice.controller;


import com.company.noteservice.ServiceLayer.NoteServiceLayer;
import com.company.noteservice.exceptions.NotFoundException;

import com.company.noteservice.viewModel.NoteVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteServiceController {

    @Autowired
    NoteServiceLayer noteServiceLayer;

    public NoteServiceController(NoteServiceLayer noteServiceLayer) {
        this.noteServiceLayer = noteServiceLayer;
    }
    // create Note
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteVM createNote(@RequestBody @Valid NoteVM note) {
        return noteServiceLayer.newNote(note);
    }

    // get note by id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NoteVM getNote (@PathVariable("id") int noteId) {
        NoteVM noteVM = noteServiceLayer.retrieveNote(noteId);
        if(noteVM == null)
            throw new NotFoundException("Note could not be retrieved for id " + noteId);
        return noteVM;
    }
    // get Note by Book
    @GetMapping("/book/{book_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<NoteVM> getNotesByBook (@PathVariable("book_id") int bookId) {
        List<NoteVM> notes = noteServiceLayer.retrieveNoteByBookId(bookId);
        if(notes == null)
            throw new NotFoundException("Note could not be retrieved for book id " + bookId);
        return notes;
    }

    // get all notes
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NoteVM> getAllNotes() {
        return noteServiceLayer.retrieveAllNotes();
    }
    //delete note
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable("id") int noteId)  {
        NoteVM noteVM = noteServiceLayer.retrieveNote(noteId);
        if(noteVM == null)
            throw new NotFoundException("Note could not be retrieved for id " + noteId);
        noteServiceLayer.deleteNote(noteId);
    }
    // update note
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateNote(@PathVariable("id") int noteId, @RequestBody @Valid NoteVM noteVM) {
        if (noteVM.getNoteId() == 0)
            noteVM.setNoteId(noteId);
        if (noteId != noteVM.getNoteId()) {
            throw new IllegalArgumentException("note ID on path must match the ID in the note object");
        }
        noteServiceLayer.updateNote(noteVM);
    }


}
