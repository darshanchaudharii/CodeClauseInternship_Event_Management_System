package com.codeclause.event.dao;
import com.codeclause.event.model.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDao {
    private final Connection conn;

    public EventDao(Connection conn) {
        this.conn = conn;
    }

    public void addEvent(Event event) throws SQLException {
        String sql = "INSERT INTO event(title, date, location) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, event.getTitle());
            ps.setDate(2, Date.valueOf(event.getDate()));
            ps.setString(3, event.getLocation());
            ps.executeUpdate();
        }
    }

    public List<Event> getAllEvents() throws SQLException {
        String sql = "SELECT * FROM event";
        List<Event> events = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Event e = new Event();
                e.setEventId(rs.getInt("event_id"));
                e.setTitle(rs.getString("title"));
                e.setDate(rs.getDate("date").toLocalDate());
                e.setLocation(rs.getString("location"));
                events.add(e);
            }
        }
        return events;
    }

    public void updateEvent(Event event) throws SQLException {
        String sql = "UPDATE event SET title = ?, date = ?, location = ? WHERE event_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, event.getTitle());
            ps.setDate(2, Date.valueOf(event.getDate()));
            ps.setString(3, event.getLocation());
            ps.setInt(4, event.getEventId());
            ps.executeUpdate();
        }
    }

    public void deleteEvent(int eventId) throws SQLException {
        String sql = "DELETE FROM event WHERE event_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ps.executeUpdate();
        }
    }
}
