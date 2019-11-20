package com.company.noteservice.viewModel;




import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class NoteVM {

    private int noteId;
    @NotNull(message = "you must provide a Book")
    private Integer bookId;
    @NotEmpty(message = "you must provide a note")
    @Size(max = 255)
    private String note;


    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
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
        NoteVM noteVM = (NoteVM) o;
        return noteId == noteVM.noteId &&
                Objects.equals(bookId, noteVM.bookId) &&
                Objects.equals(note, noteVM.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteId, bookId, note);
    }

    @Override
    public String toString() {
        return "NoteVM{" +
                "noteId=" + noteId +
                ", bookId=" + bookId +
                ", note='" + note + '\'' +
                '}';
    }
}
