package com.codeclause.event.model;

public class Attendee {
    private int attendeeId;
    private String name;
    private String email;

    public int getAttendeeId() {
        return attendeeId;
    }
    public void setAttendeeId(int attendeeId) {
        this.attendeeId = attendeeId;
    }
    public String getName() {
        return name;
    } 
    public void setName(String name) {
        this.name = name;
    } 
    public String getEmail() {
        return email;
    } 
    public void setEmail(String email) {
        this.email = email;
    }
}
