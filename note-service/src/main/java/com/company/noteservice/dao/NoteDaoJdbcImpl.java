package com.company.noteservice.dao;

import com.company.noteservice.dto.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NoteDaoJdbcImpl implements NoteDao {

    private static final String INSERT_NOTE_SQL =
            "insert into note (note_id, book_id, note) values (?, ?, ?)";

    private static final String SELECT_NOTE_SQL =
            "select * from note where note_id = ?";

    private static final String SELECT_ALL_NOTES_SQL =
            "select * from note";

    private static final String UPDATE_NOTE_SQL =
            "update note set book_id = ?, note = ? where note_id = ?";

    private static final String DELETE_NOTE =
            "delete from note where note_id = ?";


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public NoteDaoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Note addNote(Note note) {
        jdbcTemplate.update(
                INSERT_NOTE_SQL,
                note.getBookId(),
                note.getNote());

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        note.setNoteId(id);
        return note;
    }

    @Override
    public Note getNote(int id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_NOTE_SQL, this::mapRowToNote, id);
        }
    }

    @Override
    public List<Note> getAllNotes() {
        return null;
    }

    @Override
    public void updateNote(Note note) {

    }

    @Override
    public void deleteNote(int id) {

    }
    private Note mapRowToNote(ResultSet rs, int rowNum) throws SQLException {
        Note note = new Note();
        note.setNoteId(rs.getInt("note_id"));
        note.setB(rs.getString("model"));
        note.setManufacturer(rs.getString("manufacturer"));
        note.setMemoryAmount(rs.getString("memory_amount"));
        note.setProcessor(rs.getString("processor"));
        note.setPrice(rs.getBigDecimal("price"));
        note.setQuantity(rs.getInt("quantity"));

        return note;

}
