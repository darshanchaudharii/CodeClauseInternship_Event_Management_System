package com.codeclause.event.service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.codeclause.event.dao.ScheduleDao;
import com.codeclause.event.model.Schedule;

public class ScheduleService {
  private final ScheduleDao dao;
  public ScheduleService() throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection conn = DriverManager.getConnection(
      "jdbc:mysql://localhost:3306/ems_db?useSSL=false&serverTimezone=UTC",
      "root", "Darshan@4998"
    );
    this.dao = new ScheduleDao(conn);
  }
  public void addSchedule(Schedule s)       throws SQLException { dao.addSchedule(s); }
  public List<Schedule> getAllSchedules()   throws SQLException { return dao.getAllSchedules(); }
  public void updateSchedule(Schedule s)    throws SQLException { dao.updateSchedule(s); }
  public void deleteSchedule(int id)        throws SQLException { dao.deleteSchedule(id); }
}
