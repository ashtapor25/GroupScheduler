// Credits to David Moodie @https://github.com/davejm/SwingCalendar
package ui.SwingCalendar;

import java.util.EventListener;

public interface CalendarEmptyClickListener extends EventListener {
    // Event dispatch methods
    void calendarEmptyClick(CalendarEmptyClickEvent e);
}
