package com.codeclause.event.dao;
import com.codeclause.event.model.Attendee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendeeDao {
    private final Connection conn;

    public AttendeeDao(Connection conn) {
        this.conn = conn;
    }

    public void addAttendee(Attendee attendee) throws SQLException {
        String sql = "INSERT INTO attendee(name, email) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, attendee.getName());
            ps.setString(2, attendee.getEmail());
            ps.executeUpdate();
        }
    }

    public List<Attendee> getAllAttendees() throws SQLException {
        String sql = "SELECT * FROM attendee";
        List<Attendee> attendees = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Attendee a = new Attendee();
                a.setAttendeeId(rs.getInt("attendee_id"));
                a.setName(rs.getString("name"));
                a.setEmail(rs.getString("email"));
                attendees.add(a);
            }
        }
        return attendees;
    }

    public void updateAttendee(Attendee attendee) throws SQLException {
        String sql = "UPDATE attendee SET name = ?, email = ? WHERE attendee_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, attendee.getName());
            ps.setString(2, attendee.getEmail());
            ps.setInt(3, attendee.getAttendeeId());
            ps.executeUpdate();
        }
    }

    public void deleteAttendee(int attendeeId) throws SQLException {
        String sql = "DELETE FROM attendee WHERE attendee_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, attendeeId);
            ps.executeUpdate();
        }
    }
}
