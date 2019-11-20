package com.company.bookservice.dao;

import com.company.bookservice.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookDaoJdbcImpl implements BookDao {
    public static final String INSERT_BOOK =
            "insert into book (title, author) values (?, ?)";
    public static final String SELECT_BOOK_BY_ID =
            "select * from book where book_id = ?";
    public static final String SELECT_ALL_BOOKS =
            "select * from book";
    public static final String UPDATE_BOOK =
            "update book set title = ?, author = ?where book_id = ?";
    public static final String DELETE_BOOK =
            "delete from book where book_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDaoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Book addBook(Book book) {
        jdbcTemplate.update(INSERT_BOOK,
                book.getTitle(),
                book.getAuthor());

        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);

        book.setId(id);

        return book;
    }

    @Override
    public Book getBook(int id) {
        try {

            return jdbcTemplate.queryForObject(SELECT_BOOK_BY_ID, this::mapRowToBook, id);

        } catch (EmptyResultDataAccessException e) {
            // if nothing is returned just catch the exception and return null
            return null;
        }
    }

    @Override
    public List<Book> getAllBooks() {

        return jdbcTemplate.query(SELECT_ALL_BOOKS, this::mapRowToBook);
    }

    @Override
    public void updateBook(Book book) {
        jdbcTemplate.update(UPDATE_BOOK,
                book.getTitle(),
                book.getAuthor(),
                book.getId());
    }

    @Override
    public void deleteBook(int id) {
        jdbcTemplate.update(DELETE_BOOK, id);
    }

    // Helper methods
    private Book mapRowToBook(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("book_id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));

        return book;
    }
}
