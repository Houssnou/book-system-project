package com.company.bookservice.dao;

import com.company.bookservice.dto.Book;

import java.util.List;

public interface BookDao {

    Book addBook(Book book);

    Book getBook(int id);

    List<Book> getAllBooks();

    void updateBooks(Book book);

    void deleteBook(int id);




}
