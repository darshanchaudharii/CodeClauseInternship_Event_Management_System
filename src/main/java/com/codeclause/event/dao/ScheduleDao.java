package com.codeclause.event.dao;

import com.codeclause.event.model.Schedule;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDao {
    private final Connection conn;

    public ScheduleDao(Connection conn) {
        this.conn = conn;
    }

    public void addSchedule(Schedule schedule) throws SQLException {
        String sql = "INSERT INTO schedule(event_id, start_time, end_time, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, schedule.getEventId());
            ps.setTimestamp(2, Timestamp.valueOf(schedule.getStartTime()));
            ps.setTimestamp(3, Timestamp.valueOf(schedule.getEndTime()));
            ps.setString(4, schedule.getDescription());
            ps.executeUpdate();
        }
    }

    public List<Schedule> getAllSchedules() throws SQLException {
        String sql = "SELECT * FROM schedule";
        List<Schedule> schedules = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Schedule s = new Schedule();
                s.setScheduleId(rs.getInt("schedule_id"));
                s.setEventId(rs.getInt("event_id"));
                s.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                s.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                s.setDescription(rs.getString("description"));
                schedules.add(s);
            }
        }
        return schedules;
    }

    public void updateSchedule(Schedule schedule) throws SQLException {
        String sql = "UPDATE schedule SET event_id = ?, start_time = ?, end_time = ?, description = ? WHERE schedule_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, schedule.getEventId());
            ps.setTimestamp(2, Timestamp.valueOf(schedule.getStartTime()));
            ps.setTimestamp(3, Timestamp.valueOf(schedule.getEndTime()));
            ps.setString(4, schedule.getDescription());
            ps.setInt(5, schedule.getScheduleId());
            ps.executeUpdate();
        }
    }

    public void deleteSchedule(int scheduleId) throws SQLException {
        String sql = "DELETE FROM schedule WHERE schedule_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, scheduleId);
            ps.executeUpdate();
        }
    }
}
