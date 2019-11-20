package com.company.noteservice.controller;

import com.company.noteservice.ServiceLayer.NoteServiceLayer;
import com.company.noteservice.viewModel.NoteVM;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(NoteServiceController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class NoteServiceControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    NoteServiceLayer service;
    NoteVM noteToCreate;
    NoteVM noteDB;
    List<NoteVM> notes;
    // Mapper to turn Java objects into JSON and vice versa
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        // setup test objects
        noteToCreate = new NoteVM();
        noteToCreate.setBookId(1);
        noteToCreate.setNote("The Alchemist Best Quote: And, when you want something, all the universe conspires in helping you to achieve it.");
        noteDB = new NoteVM();
        noteDB.setNoteId(1);
        noteDB.setBookId(1);
        noteDB.setNote("The Alchemist Best Quote: And, when you want something, all the universe conspires in helping you to achieve it.");
        notes = new ArrayList<>();
        notes.add(noteDB);
        // fire up
        setUpServiceMock();
    }

    @Test
    public void shouldAddNote() throws Exception {
        //Assemble
        String inputJson = mapper.writeValueAsString(noteToCreate);
        String outputJson = mapper.writeValueAsString(noteDB);
        System.out.println(inputJson);
        System.out.println(outputJson);
        //Act
        mockMvc.perform(post("/notes")
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
        NoteVM invalid = new NoteVM();
        String inputJson = mapper.writeValueAsString(invalid);
        System.out.println(inputJson);
        //Act
        mockMvc.perform(post("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldGetAllNotes() throws Exception {
        String outputJson = mapper.writeValueAsString(notes);
        //Act
        mockMvc.perform(get("/notes"))
                //Assert
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetNote() throws Exception {
        String outputJson = mapper.writeValueAsString(noteDB);
        //Act
        mockMvc.perform(get("/notes/1"))
                //Assert
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturn404WhenNotFound() throws Exception {
        //Act
        mockMvc.perform(get("/notes/100"))
                //Assert
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateNote() throws Exception {
        //Assemble
        String inputJson = mapper.writeValueAsString(noteDB);
        //Act
        mockMvc.perform(put("/notes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn422WhenUpdatingWithInvalidId() throws Exception {
        //Assemble
        String inputJson = mapper.writeValueAsString(noteDB);
        //Act
        mockMvc.perform(put("/notes/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                //Assert
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void shouldDeleteNote() throws Exception {
        //Act
        mockMvc.perform(delete("/notes/1"))
                //Assert
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404WhenDeletingNonExistingNote() throws Exception {
        //Act
        mockMvc.perform(delete("/notes/100"))
                //Assert
                .andExpect(status().isNotFound());
    }

    // helper method
    private void setUpServiceMock() {
        doReturn(noteDB).when(service).newNote(noteToCreate);
        doReturn(noteDB).when(service).retrieveNote(1);
        doReturn(notes).when(service).retrieveAllNotes();
        doReturn(notes).when(service).retrieveNoteByBookId(1);
    }
}

