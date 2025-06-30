package com.codeclause.event.service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.codeclause.event.dao.AttendeeDao;
import com.codeclause.event.model.Attendee;

public class AttendeeService {
  private final AttendeeDao dao;
  public AttendeeService() throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection conn = DriverManager.getConnection(
      "jdbc:mysql://localhost:3306/ems_db?useSSL=false&serverTimezone=UTC",
      "root", "Darshan@4998"
    );
    this.dao = new AttendeeDao(conn);
  }
  public void addAttendee(Attendee a) throws SQLException { dao.addAttendee(a); }
  public List<Attendee> getAllAttendees() throws SQLException { return dao.getAllAttendees(); }
  public void updateAttendee(Attendee a) throws SQLException { dao.updateAttendee(a); }
  public void deleteAttendee(int id) throws SQLException { dao.deleteAttendee(id); }
}
