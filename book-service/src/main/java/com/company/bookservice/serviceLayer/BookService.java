package com.company.bookservice.serviceLayer;

import com.company.bookservice.dao.BookDao;
import com.company.bookservice.dto.Book;
import com.company.bookservice.viewModel.BookVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookService {
    // bring in the bookDao
    BookDao bookDao;
    // Bring it the note service as a client

    //ctor
    @Autowired
    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public BookVM newBook(BookVM bookVM) {
        Book book = new Book();
        book.setTitle(bookVM.getTitle());
        book.setAuthor(bookVM.getAuthor());
        book = bookDao.addBook(book);

        bookVM.setBookId(book.getId());
        return bookVM;
    }

    public BookVM fetchBook(int id) {
        Book book = bookDao.getBook(id);
        if (book == null) return null;

        BookVM bvm = new BookVM();
        bvm.setBookId(book.getId());
        bvm.setTitle(book.getTitle());
        bvm.setAuthor(book.getAuthor());

        // TODO - get notes from NoteServiceCLient and put in bvm

        return bvm;
    }

    public List<BookVM> fetchAllBooks() {
        List<Book> books = bookDao.getAllBooks();
        if (books.isEmpty() || books == null) return null;

        List<BookVM> bookVMs = new ArrayList<>();
        books.stream()
                .forEach(book -> {
                    //build a book view model
                    BookVM bvm = new BookVM();
                    bvm.setBookId(book.getId());
                    bvm.setTitle(book.getTitle());
                    bvm.setAuthor(book.getAuthor());
                    // get all notes for this book
                    // TODO - get notes from NoteServiceClient and put in bvm

                    // the local bvm to the lsit of bookVMs
                    bookVMs.add(bvm);
                });

        return bookVMs;
    }

    public void updateBook(BookVM bookVM) {
        // persists the object
        Book book = new Book();
        book.setTitle(bookVM.getTitle());
        book.setAuthor(bookVM.getAuthor());

        bookDao.updateBook(book);
    }

    public void deleteBook(int id) {
        // before deleting a book we need to delete the notes as referential Integrity
        // TODO - delete notes from NoteServiceClient

        // straight delete the object
        bookDao.deleteBook(id);
    }

}
