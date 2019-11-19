package com.company.bookservice.viewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookVM {

    private int bookId;
    private String title;
    private String author;
    //private List<>  = new ArrayList<>();


    public int getId() {
        return bookId;
    }

    public void setId(int id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookVM bookVM = (BookVM) o;
        return getId() == bookVM.getId() &&
                Objects.equals(getTitle(), bookVM.getTitle()) &&
                Objects.equals(getAuthor(), bookVM.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getAuthor());
    }

    @Override
    public String toString() {
        return "BookVM{" +
                "id=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
