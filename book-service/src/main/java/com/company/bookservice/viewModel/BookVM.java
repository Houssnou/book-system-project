package com.company.bookservice.viewModel;

import com.company.bookservice.dto.Note;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookVM {

    private int bookId;
    private String title;
    private String author;
    private List<Note> notes;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookVM bookVM = (BookVM) o;
        return getBookId() == bookVM.getBookId() &&
                Objects.equals(getTitle(), bookVM.getTitle()) &&
                Objects.equals(getAuthor(), bookVM.getAuthor()) &&
                Objects.equals(getNotes(), bookVM.getNotes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookId(), getTitle(), getAuthor(), getNotes());
    }

    @Override
    public String toString() {
        return "BookVM{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", notes=" + notes +
                '}';
    }
}
