package com.DAO;

import com.User.NoteDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoteDAO {

    public static boolean addNote(NoteDetails note, Connection conn) throws SQLException {
        String query = "INSERT INTO note (title, content, userId) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, note.getTitle());
            ps.setString(2, note.getContent());
            ps.setInt(3, note.getUser().getId());
            return ps.executeUpdate() > 0;
        }
    }

    public static List<NoteDetails> getAllNotes(int id, Connection conn) throws SQLException {
        List<NoteDetails> list = new ArrayList<>();
        String query = "SELECT * FROM note WHERE userId=? ORDER BY date DESC";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NoteDetails note = new NoteDetails();
                    note.setId(rs.getInt("id"));
                    note.setTitle(rs.getString("title"));
                    note.setContent(rs.getString("content"));
                    note.setDate(rs.getTimestamp("date").toLocalDateTime());
                    list.add(note);
                }
            }
        }
        return list;
    }

    public static NoteDetails getNoteById(int id, Connection conn) throws SQLException {
        NoteDetails note = null;
        String query = "SELECT * FROM note WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    note = new NoteDetails();
                    note.setId(rs.getInt("id"));
                    note.setTitle(rs.getString("title"));
                    note.setContent(rs.getString("content"));
                    note.setDate(rs.getTimestamp("date").toLocalDateTime());
                }
            }
        }
        return note;
    }

    public static boolean editNote(NoteDetails note, Connection conn) throws SQLException {
        String query = "UPDATE note SET title = ?, content = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, note.getTitle());
            ps.setString(2, note.getContent());
            ps.setInt(3, note.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public static boolean deleteNote(int id, Connection conn) throws SQLException {
        String query = "DELETE FROM note WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
