package ui.ScreenFrames;

import Data.Converter;
import ui.GroupScheduler;
import ui.SwingCalendar.Calendar;
import ui.SwingCalendar.CalendarEvent;
import ui.SwingCalendar.WeekCalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class EventScreen extends JFrame {

    private GroupScheduler gs;
    private WeekCalendar cal;

    public EventScreen(ArrayList<CalendarEvent> events, GroupScheduler gs) {
        super();
        this.gs = gs;
        cal = new WeekCalendar(events);
        cal.addCalendarEventClickListener(gs);

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

        this.add(weekControls, BorderLayout.NORTH);

        this.add(cal, BorderLayout.CENTER);
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

    public void repaint() {
        cal.repaint();
    }

    public void setEvents(ArrayList<CalendarEvent> events) {
        cal.setEvents(events);
        this.repaint();
    }

    private void exitProcedure() {
        gs.save();
        this.dispose();
        System.exit(0);
    }
}
