package com.codeclause.event.service;

import com.codeclause.event.dao.EventDao;
import com.codeclause.event.model.Event;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class EventService {
    private final EventDao eventDao;

    public EventService() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url      = "jdbc:mysql://localhost:3306/ems_db?useSSL=false&serverTimezone=UTC";
        String user     = "root";
        String password = "Darshan@4998";
        Connection conn = DriverManager.getConnection(url, user, password);
        this.eventDao   = new EventDao(conn);
    }

    public void createEvent(Event event) throws SQLException {
        eventDao.addEvent(event);
    }

    public List<Event> listEvents() throws SQLException {
        return eventDao.getAllEvents();
    }

    public void updateEvent(Event event) throws SQLException {
        eventDao.updateEvent(event);
    }

    public void deleteEvent(int eventId) throws SQLException {
        eventDao.deleteEvent(eventId);
    }
}
