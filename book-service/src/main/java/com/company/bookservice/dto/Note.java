package com.company.bookservice.dto;

import java.util.Objects;

public class Note {

    private int noteId;
    private String bookId;
    private String note;

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
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
        Note note1 = (Note) o;
        return getNoteId() == note1.getNoteId() &&
                Objects.equals(getBookId(), note1.getBookId()) &&
                Objects.equals(getNote(), note1.getNote());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNoteId(), getBookId(), getNote());
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", bookId='" + bookId + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}

