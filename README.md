# Event Management System

A Java Swing desktop application for organizing and coordinating events, attendees, and schedules, backed by a MySQL database.

---

## Table of Contents

1. [Features](#features)
2. [Tech Stack](#tech-stack)
3. [Prerequisites](#prerequisites)
4. [Installation & Setup](#installation--setup)
5. [Usage](#usage)
6. [Project Structure](#project-structure)
7. [Database Schema](#database-schema)
8. [Future Enhancements](#future-enhancements)
9. [License](#license)

---

## Features

* **Event Management**: Create, list, and delete events with sequential numbering.
* **Attendee Management**: Add, list, and remove attendees.
* **Scheduling**: Assign start/end times to events, enforce referential integrity.
* **UI Flow**: Tabbed interface for Events, Attendees, Schedules.
* **Confirmation Dialogs**: User confirmation before deletions.

---

## Tech Stack

* **Language:** Java 17
* **UI:** Swing
* **Database:** MySQL
* **Build:** Maven
* **Version Control:** Git

---

## Prerequisites

* Java JDK 17+
* MySQL Server
* Maven 3+
* (Optional) Spring Tool Suite (STS) 4 or any Java IDE

---

## Installation & Setup

1. **Clone the repository**

   ```bash
   git clone <repo-url>
   cd event-management-system
   ```

2. **Configure MySQL**

   * Create database and tables:

     ```sql
     CREATE DATABASE ems_db;
     USE ems_db;

     CREATE TABLE event (
       event_id INT AUTO_INCREMENT PRIMARY KEY,
       title VARCHAR(100) NOT NULL,
       date DATE NOT NULL,
       location VARCHAR(150)
     );

     CREATE TABLE attendee (
       attendee_id INT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       email VARCHAR(100) UNIQUE NOT NULL
     );

     CREATE TABLE schedule (
       schedule_id INT AUTO_INCREMENT PRIMARY KEY,
       event_id INT,
       start_time DATETIME,
       end_time DATETIME,
       description VARCHAR(255),
       FOREIGN KEY (event_id) REFERENCES event(event_id)
     );
     ```

3. **Configure Database Credentials**

   * Update JDBC URL, user, and password in each `*Service` class.

4. **Build the project**

   ```bash
   mvn clean package
   ```

---

## Usage

### Run from IDE

* Import as Maven project.
* Run `MainFrame.java` as a Java Application.

### Run from Command Line

```bash
java -jar target/ems-1.0.0-SNAPSHOT.jar
```

**Navigate tabs** to manage Events, Attendees, and Schedules.

---

## Project Structure

```
src/main/java/com/codeclause/event
├── model
│   ├── Event.java
│   ├── Attendee.java
│   └── Schedule.java
├── dao
│   ├── EventDao.java
│   ├── AttendeeDao.java
│   └── ScheduleDao.java
├── service
│   ├── EventService.java
│   ├── AttendeeService.java
│   └── ScheduleService.java
└── ui
    └── MainFrame.java
```

---

## Database Schema

* **event**: event\_id, title, date, location
* **attendee**: attendee\_id, name, email
* **schedule**: schedule\_id, event\_id (FK), start\_time, end\_time, description

---

## Future Enhancements

* **Update functionality** for events, attendees, schedules.
* **Assign attendees to events** via a join table and UI.
* **Export data** to CSV/Excel.
* **Search & filter** capabilities.
* **Authentication & role-based access**.

---

## License

This project is licensed under the MIT License.
