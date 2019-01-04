package Data;

import ui.SwingCalendar.CalendarEvent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Converter {

    public static CalendarEvent ScheduleToCalendarEvent(Schedule schedule) {
        LocalDate date = schedule.getTimeFrame().getStart().toLocalDate();
        LocalTime start = schedule.getTimeFrame().getStart().toLocalTime();
        LocalTime end = schedule.getTimeFrame().getEnd().toLocalTime();
        String text = schedule.getName();
        return new CalendarEvent(date, start, end, text);
    }

    public static ArrayList<CalendarEvent> ScheduleListtoCalendarEventList(TreeSet<Schedule> ScheduleList) {
        ArrayList<CalendarEvent> ret = new ArrayList<>();
        for(Schedule s:ScheduleList) {
            ret.add(ScheduleToCalendarEvent(s));
        }
        return ret;
    }

    public static Schedule CalendarEventToSchedule(CalendarEvent e) {
        ZonedDateTime start = ZonedDateTime.of(e.getDate(), e.getStart(), ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.of(e.getDate(), e.getEnd(), ZoneId.systemDefault());
        TimeFrame timeframe = new TimeFrame(start, end);
        return new Schedule(e.getText(), timeframe);
    }

    public static ArrayList<CalendarEvent> TimeframesToCalendarEvents(List<TimeFrame> timeframes) {
        ArrayList<CalendarEvent> ret = new ArrayList<>();
        int timeframeNo = 1;
        for(TimeFrame tf: timeframes) {
            LocalDate date = tf.getStart().toLocalDate();
            LocalTime start = tf.getStart().toLocalTime();
            LocalTime end = tf.getEnd().toLocalTime();
            String text = "Recommended " + timeframeNo;
            CalendarEvent toadd = new CalendarEvent(date, start, end, text);
            timeframeNo += 1;
            ret.add(toadd);
        }
        return ret;
    }
}
