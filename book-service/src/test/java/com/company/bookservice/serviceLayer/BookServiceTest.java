package com.company.bookservice.serviceLayer;

import com.company.bookservice.dao.BookDao;
import com.company.bookservice.dao.BookDaoJdbcImpl;
import com.company.bookservice.dto.Book;
import com.company.bookservice.viewModel.BookVM;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class BookServiceTest {
    // call the dao
    BookDao bookDao;
    BookService bookService;

    @Before
    public void setUp() throws Exception {
        // fire up bookDaoMock
        setBookDaoMock();

        bookService = new BookService(bookDao);
    }

    @Test
    public void shouldCreateAndFetchBook() {
        BookVM bookVM = new BookVM();
        bookVM.setAuthor("Paulo Coehlo");
        bookVM.setTitle("The Alchemist");

        bookVM = bookService.newBook(bookVM);
        System.out.println(bookVM);
        BookVM bookVM1 = bookService.fetchBook(bookVM.getBookId());
        assertEquals(bookVM1, bookVM);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionIfEmptyFields() {
        bookService.newBook(new BookVM());
    }

    @Test
    public void shouldFetchAllBooks() {
        BookVM bookVM = new BookVM();
        bookVM.setAuthor("Paulo Coehlo");
        bookVM.setTitle("The Alchemist");

        bookVM = bookService.newBook(bookVM);

        List<BookVM> bookVMs = bookService.fetchAllBooks();
        assertEquals(bookVMs.size(), 1);
    }

    // set up mocks
    private void setNoteServiceMock() {

    }

    private void setBookDaoMock() {
        bookDao = mock(BookDaoJdbcImpl.class);
        // book to create
        Book bookToCreate = new Book();
        bookToCreate.setAuthor("Paulo Coehlo");
        bookToCreate.setTitle("The Alchemist");

        // book from DB
        Book bookDB = new Book();
        bookDB.setId(1);
        bookDB.setAuthor("Paulo Coehlo");
        bookDB.setTitle("The Alchemist");

        List<Book> books = new ArrayList<>();
        books.add(bookDB);

        // asssert
        doReturn(bookDB).when(bookDao).addBook(bookToCreate);
        doReturn(bookDB).when(bookDao).getBook(1);
        doReturn(books).when(bookDao).getAllBooks();
    }
}