package com.company.bookservice.controller;

import com.company.bookservice.dto.Book;
import com.company.bookservice.serviceLayer.BookService;
import com.company.bookservice.viewModel.BookVM;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class BookControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService service;

    BookVM bookToCreate;
    BookVM bookDB;
    List<BookVM> books;

    // Mapper to turn Java objects into JSON and vice versa
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        // setup test objects
        bookToCreate = new BookVM();
        bookToCreate.setAuthor("Paulo Coehlo");
        bookToCreate.setTitle("The Alchemist");

        bookDB = new BookVM();
        bookDB.setBookId(1);
        bookDB.setAuthor("Paulo Coehlo");
        bookDB.setTitle("The Alchemist");

        books = new ArrayList<>();
        books.add(bookDB);

        // fire up
        setUpServiceMock();
    }

    @Test
    public void shouldAddBook() throws Exception {
        //Assemble
        String inputJson = mapper.writeValueAsString(bookToCreate);
        String outputJson = mapper.writeValueAsString(bookDB);
        System.out.println(inputJson);
        System.out.println(outputJson);
        //Act
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldThrow422WhenEmptyFields() throws Exception {
        //Assemble
        BookVM invalid = new BookVM();

        String inputJson = mapper.writeValueAsString(invalid);


        //Act
        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldGetAllBooks()  throws Exception {
        String outputJson = mapper.writeValueAsString(books);

        //Act
        mockMvc.perform(get("/books"))
                //Assert
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetBook() throws Exception {
        String outputJson = mapper.writeValueAsString(bookDB);

        //Act
        mockMvc.perform(get("/books/1"))
                //Assert
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn404WhenNotFound() throws Exception {
        //Act
        mockMvc.perform(get("/books/100"))
                //Assert
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateBook() throws Exception {
        //Assemble
        String inputJson = mapper.writeValueAsString(bookDB);
        //Act
        mockMvc.perform(put("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn422WhenUpdatingWithInvalidId() throws Exception {
        //Assemble
        String inputJson = mapper.writeValueAsString(bookDB);
        //Act
        mockMvc.perform(put("/books/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldDeleteBook() throws Exception {
        //Act
        mockMvc.perform(delete("/books/1"))
                //Assert
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404WhenDeletingNonExistingBook() throws Exception {
        //Act
        mockMvc.perform(delete("/books/100"))
                //Assert
                .andExpect(status().isNotFound());
    }

    // helper method
    private void setUpServiceMock() {
        doReturn(bookDB).when(service).newBook(bookToCreate);
        doReturn(bookDB).when(service).fetchBook(1);
        doReturn(books).when(service).fetchAllBooks();
    }
}