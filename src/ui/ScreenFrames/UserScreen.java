package ui.ScreenFrames;

import Data.Converter;
import Data.Guest;
import Data.Person;
import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;
import ui.GroupScheduler;
import ui.SwingCalendar.Calendar;
import ui.SwingCalendar.WeekCalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserScreen extends JFrame {

    private GroupScheduler gs;
    private Person user;
    private JPanel TopControlBar;
    private JLabel UserName;
    private WeekCalendar cal;

    public void setUser(Person user) {
        this.user = user;
        UserName.setText(user.getName());
        cal.setEvents(Converter.ScheduleListtoCalendarEventList(user.getSchedules()));
    }

    public UserScreen(GroupScheduler gs) {
        super();
        this.gs = gs;
        user = new Guest("dummy", 0);
        TopControlBar = new JPanel();
        TopControlBar.setLayout(new BorderLayout());

        UserName = new JLabel(user.getName());
        UserName.setFont(new Font("Gothic", Font.BOLD, 15));
        TopControlBar.add(UserName, BorderLayout.NORTH);

        cal = new WeekCalendar(Converter.ScheduleListtoCalendarEventList(user.getSchedules()));

        cal.addCalendarEventClickListener(e -> System.out.println(e.getCalendarEvent()));
        cal.addCalendarEmptyClickListener(e -> {
            System.out.println(e.getDateTime());
            System.out.println(Calendar.roundTime(e.getDateTime().toLocalTime(), 30));
        });

        JButton goToTodayBtn = new JButton("Today");
        goToTodayBtn.addActionListener(e -> cal.goToToday());

        JButton nextWeekBtn = new JButton(">");
        nextWeekBtn.addActionListener(e -> cal.nextWeek());

        JButton prevWeekBtn = new JButton("<");
        prevWeekBtn.addActionListener(e -> cal.prevWeek());

        JButton nextMonthBtn = new JButton(">>");
        nextMonthBtn.addActionListener(e -> cal.nextMonth());

        JButton prevMonthBtn = new JButton("<<");
        prevMonthBtn.addActionListener(e -> cal.prevMonth());

        JPanel weekControls = new JPanel();
        weekControls.add(prevMonthBtn);
        weekControls.add(prevWeekBtn);
        weekControls.add(goToTodayBtn);
        weekControls.add(nextWeekBtn);
        weekControls.add(nextMonthBtn);

        TopControlBar.add(weekControls, BorderLayout.CENTER);

        JPanel UserButtons = new JPanel();

        JButton addSchedule = new JButton("Add Schedule");
        addSchedule.addActionListener(e -> gs.addSchedule());

        JButton importGoogle = new JButton("Import from Google Calendar");

        JButton showSpots = new JButton("Show Recommended Times");
        showSpots.addActionListener(e -> gs.showAvailableSpots());

        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> gs.logout());

        UserButtons.add(addSchedule);
        UserButtons.add(importGoogle);
        UserButtons.add(showSpots);
        UserButtons.add(logout);

        this.setLayout(new BorderLayout());
        this.add(TopControlBar, BorderLayout.NORTH);
        this.add(cal, BorderLayout.CENTER);
        this.add(UserButtons, BorderLayout.SOUTH);
        this.setSize(1280, 720);
        this.setVisible(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                exitProcedure();
            }
        });
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void exitProcedure() {
        gs.save();
        this.dispose();
        System.exit(0);
    }
}
