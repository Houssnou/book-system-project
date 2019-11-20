package com.company.bookservice.controller;

import com.company.bookservice.exception.NotFoundException;
import com.company.bookservice.serviceLayer.BookService;
import com.company.bookservice.viewModel.BookVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/books")
public class BookController {
    @Autowired
    BookService service;

    public BookController(BookService bookService) {
        this.service = bookService;
    }

    // post
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookVM addBook(@RequestBody @Valid BookVM bookVM) {
        return service.newBook(bookVM);
    }
    // get all
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookVM> getAllBooks() {
        return service.fetchAllBooks();
    }
    // get by id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookVM getBook(@PathVariable int id) {
        BookVM bookVM;

        bookVM = service.fetchBook(id);

        if (bookVM == null) throw new NotFoundException("No Game matches Id: " + id);

        return bookVM;
    }
    // put
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBook(@PathVariable int id, @RequestBody @Valid BookVM bookVM) {

        if (bookVM.getBookId() != id) {
            throw new IllegalArgumentException("Id in parameter must match the ID in the request body");
        }

        BookVM toUpdate = service.fetchBook(bookVM.getBookId());

        if (toUpdate == null) throw new NotFoundException("No Book matches Id: " + id);

        service.updateBook(bookVM);
    }
    // delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable int id) {

        if (id == 0 || id < 0) {
            throw new IllegalArgumentException("Id must be a valid primary key.");
        }

        BookVM toDelete = service.fetchBook(id);

        if (toDelete == null) throw new NotFoundException("No Book matches Id: " + id);

        service.deleteBook(id);
    }
}
