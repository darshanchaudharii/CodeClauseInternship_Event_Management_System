package com.codeclause.event.ui;
import com.codeclause.event.model.Attendee;
import com.codeclause.event.model.Event;
import com.codeclause.event.model.Schedule;
import com.codeclause.event.service.AttendeeService;
import com.codeclause.event.service.EventService;
import com.codeclause.event.service.ScheduleService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainFrame extends JFrame {
    private final EventService eventService;
    private final AttendeeService attendeeService;
    private final ScheduleService scheduleService;

    public MainFrame() throws Exception {
        super("Event Management System");
        eventService    = new EventService();
        attendeeService = new AttendeeService();
        scheduleService = new ScheduleService();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Events",    new EventPanel());
        tabs.addTab("Attendees", new AttendeePanel());
        tabs.addTab("Schedules", new SchedulePanel());
        add(tabs, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new MainFrame().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        });
    }
private class EventPanel extends JPanel {
        private final JTable table = new JTable();
        private final JTextField titleField    = new JTextField(10);
        private final JTextField dateField     = new JTextField("YYYY-MM-DD", 10);
        private final JTextField locationField = new JTextField(10);

        EventPanel() {
            setLayout(new BorderLayout());
            JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
            JButton addBtn    = new JButton("Add Event");
            JButton deleteBtn = new JButton("Delete Event");
            form.add(new JLabel("Title:"));    form.add(titleField);
            form.add(new JLabel("Date:"));     form.add(dateField);
            form.add(new JLabel("Location:")); form.add(locationField);
            form.add(addBtn);                  form.add(deleteBtn);
            add(form, BorderLayout.NORTH);
            add(new JScrollPane(table), BorderLayout.CENTER);

            addBtn.addActionListener(e -> {
                try {
                    Event ev = new Event();
                    ev.setTitle(titleField.getText().trim());
                    ev.setDate(LocalDate.parse(dateField.getText().trim()));
                    ev.setLocation(locationField.getText().trim());
                    eventService.createEvent(ev);
                    titleField.setText("");
                    dateField.setText("YYYY-MM-DD");
                    locationField.setText("");
                    loadData();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            deleteBtn.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(this,
                        "Select a row first", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    List<Event> list = eventService.listEvents();
                    Event ev = list.get(row);
                    if (JOptionPane.showConfirmDialog(this,
                            "Delete \"" + ev.getTitle() + "\"?", "Confirm", JOptionPane.YES_NO_OPTION)
                        == JOptionPane.YES_OPTION) {
                        eventService.deleteEvent(ev.getEventId());
                        loadData();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            loadData();
        }

        private void loadData() {
            try {
                List<Event> list = eventService.listEvents();
                DefaultTableModel m = new DefaultTableModel(
                    new String[]{"Sr no.", "Title", "Date", "Location"}, 0);
                for (int i = 0; i < list.size(); i++) {
                    Event ev = list.get(i);
                    m.addRow(new Object[]{i + 1, ev.getTitle(), ev.getDate(), ev.getLocation()});
                }
                table.setModel(m);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
private class AttendeePanel extends JPanel {
        private final JTable table = new JTable();
        private final JTextField nameField  = new JTextField(10);
        private final JTextField emailField = new JTextField(15);

        AttendeePanel() {
            setLayout(new BorderLayout());
            JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
            JButton addBtn    = new JButton("Add Attendee");
            JButton deleteBtn = new JButton("Delete Attendee");
            form.add(new JLabel("Name:"));  form.add(nameField);
            form.add(new JLabel("Email:")); form.add(emailField);
            form.add(addBtn);                form.add(deleteBtn);
            add(form, BorderLayout.NORTH);
            add(new JScrollPane(table), BorderLayout.CENTER);

            addBtn.addActionListener(e -> {
                try {
                    Attendee a = new Attendee();
                    a.setName(nameField.getText().trim());
                    a.setEmail(emailField.getText().trim());
                    attendeeService.addAttendee(a);
                    nameField.setText("");
                    emailField.setText("");
                    loadData();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            deleteBtn.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(this,
                        "Select a row", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    List<Attendee> list = attendeeService.getAllAttendees();
                    Attendee a = list.get(row);
                    if (JOptionPane.showConfirmDialog(this,
                            "Delete \"" + a.getName() + "\"?", "Confirm", JOptionPane.YES_NO_OPTION)
                        == JOptionPane.YES_OPTION) {
                        attendeeService.deleteAttendee(a.getAttendeeId());
                        loadData();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            loadData();
        }

        private void loadData() {
            try {
                List<Attendee> list = attendeeService.getAllAttendees();
                DefaultTableModel m = new DefaultTableModel(
                    new String[]{"Sr no.", "Name", "Email"}, 0);
                for (int i = 0; i < list.size(); i++) {
                    Attendee a = list.get(i);
                    m.addRow(new Object[]{i + 1, a.getName(), a.getEmail()});
                }
                table.setModel(m);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
private class SchedulePanel extends JPanel {
        private final JTable table = new JTable();
        private final JComboBox<String> eventCombo = new JComboBox<>();
        private final JTextField startField   = new JTextField("YYYY-MM-DD HH:MM", 12);
        private final JTextField endField     = new JTextField("YYYY-MM-DD HH:MM", 12);
        private final JTextField descField    = new JTextField(15);
        private final DateTimeFormatter fmt   = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        SchedulePanel() {
            setLayout(new BorderLayout());
            JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
            JButton addBtn    = new JButton("Add Schedule");
            JButton deleteBtn = new JButton("Delete Schedule");

            form.add(new JLabel("Event:")); form.add(eventCombo);
            form.add(new JLabel("Start:")); form.add(startField);
            form.add(new JLabel("End:"));   form.add(endField);
            form.add(new JLabel("Desc:"));  form.add(descField);
            form.add(addBtn); form.add(deleteBtn);
            add(form, BorderLayout.NORTH);
            add(new JScrollPane(table), BorderLayout.CENTER);

            refreshEventCombo();

            addBtn.addActionListener(e -> {
                try {
                    int idx = eventCombo.getSelectedIndex();
                    List<Event> evs = eventService.listEvents();
                    int eventId = evs.get(idx).getEventId();

                    Schedule s = new Schedule();
                    s.setEventId(eventId);
                    s.setStartTime(LocalDateTime.parse(
                        startField.getText().trim().replace(" ", "T")));
                    s.setEndTime(LocalDateTime.parse(
                        endField.getText().trim().replace(" ", "T")));
                    s.setDescription(descField.getText().trim());
                    scheduleService.addSchedule(s);

                    startField.setText("YYYY-MM-DD HH:MM");
                    endField  .setText("YYYY-MM-DD HH:MM");
                    descField .setText("");
                    loadData();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            deleteBtn.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(this,
                        "Please select a schedule to delete.",
                        "No Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    List<Schedule> list = scheduleService.getAllSchedules();
                    Schedule toDel = list.get(row);
                    if (JOptionPane.showConfirmDialog(this,
                            "Delete schedule \"" + toDel.getDescription() + "\"?",
                            "Confirm Delete", JOptionPane.YES_NO_OPTION)
                        == JOptionPane.YES_OPTION) {
                        scheduleService.deleteSchedule(toDel.getScheduleId());
                        loadData();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Delete Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            loadData();
        }

        private void refreshEventCombo() {
            eventCombo.removeAllItems();
            try {
                List<Event> events = eventService.listEvents();
                for (int i = 0; i < events.size(); i++) {
                    Event ev = events.get(i);
                    eventCombo.addItem((i + 1) + " - " + ev.getTitle());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Failed to load events: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void loadData() {
            try {
                refreshEventCombo();
                List<Schedule> list = scheduleService.getAllSchedules();
                DefaultTableModel m = new DefaultTableModel(
                    new String[]{"Sr no.", "Event", "Start", "End", "Description"}, 0);
                for (int i = 0; i < list.size(); i++) {
                    Schedule s = list.get(i);
                    m.addRow(new Object[]{
                        i + 1,
                        s.getEventId(),
                        s.getStartTime().format(fmt),
                        s.getEndTime().format(fmt),
                        s.getDescription()
                    });
                }
                table.setModel(m);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Load Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
