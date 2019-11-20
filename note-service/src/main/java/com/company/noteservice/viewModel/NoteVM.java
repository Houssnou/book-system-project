package com.company.noteservice.viewModel;

import com.company.noteservice.dto.Book;
import com.company.noteservice.dto.Note;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class NoteVM {
    @NotEmpty(message = "you must provide an id")
    private int noteId;
    @NotEmpty(message = "you must provide a Book")
    private Book book;
    @NotEmpty(message = "you must provide a note")
    @Size(max = 255)
    private String note;

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteVM noteVM = (NoteVM) o;
        return noteId == noteVM.noteId &&
                Objects.equals(book, noteVM.book) &&
                Objects.equals(note, noteVM.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteId, book, note);
    }

    @Override
    public String toString() {
        return "NoteVM{" +
                "noteId=" + noteId +
                ", book=" + book +
                ", note='" + note + '\'' +
                '}';
    }
}
