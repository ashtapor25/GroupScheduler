// Credits to David Moodie @https://github.com/davejm/SwingCalendar
package ui.SwingCalendar;

import java.util.EventListener;

public interface CalendarEventClickListener extends EventListener {
    // Event dispatch methods
    void calendarEventClick(CalendarEventClickEvent e);
}
