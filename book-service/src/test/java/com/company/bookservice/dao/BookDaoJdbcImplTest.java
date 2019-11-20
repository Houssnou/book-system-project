package com.company.bookservice.dao;

import com.company.bookservice.dto.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookDaoJdbcImplTest {
    // dao impl
    @Autowired
    BookDao bookDao;

    Book book;
    List<Book> books;

    @Before
    public void setUp() throws Exception {
        // inst. test objects
        book = new Book();
        book.setAuthor("Paulo Coehlo");
        book.setTitle("The Alchemist");

        // clear the DB
        books = bookDao.getAllBooks();
        for (Book b : books) {
            bookDao.deleteBook(b.getId());
        }
    }

    @Test
    public void addGetDeleteBook() {
        // arrange
        //act
        book = bookDao.addBook(book);
        Book book1 = bookDao.getBook(book.getId());
        // assert
        assertEquals(book1, book);

        bookDao.deleteBook(book.getId());
        book1 = bookDao.getBook(book.getId());
        assertNull(book1);
    }

    @Test
    public void getAllBooks() {
        // arrange
        //act
        book = bookDao.addBook(book);

        // add a second book
        book = new Book();
        book.setAuthor("Paulo Coehlo");
        book.setTitle("Adultery");
        book = bookDao.addBook(book);
        // list
        books = bookDao.getAllBooks();
        assertEquals(books.size(), 2);
    }

    @Test
    public void updateBook() {
        // arrange
        //act
        book = bookDao.addBook(book);
        book.setTitle("Updated");
        book.setAuthor("Updated");
        book = bookDao.addBook(book);
        Book book1 = bookDao.getBook(book.getId());
        assertEquals(book1, book);
    }

}