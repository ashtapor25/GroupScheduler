package test;

import Data.*;
import Data.TimeFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import Exception.ScheduleConflictException;

public class TestObserver {
    private Event event;
    private TimeFrame tf;
    private Person p1, p2;

    @BeforeEach
    public void runBefore() {
        loadData();
        p1 = new Host("Alpha");
        p2 = new Guest("Beta");
    }

    @Test
    public void testNotify() {
        p1.addEvent(event);
        p2.addEvent(event);
        event.setEventScheduleTF(tf);
        assertTrue(p1.getSchedules().contains(event.getEventSchedule()));
        assertTrue(p2.getSchedules().contains(event.getEventSchedule()));
    }

    @Test
    public void testNotifyCollision() {
        p1.addEvent(event);
        p2.addEvent(event);
        TimeFrame collidingTF = new TimeFrame(tf.getStart().minusHours(1),tf.getEnd().plusHours(1));
        Schedule collidingSchedule = new Schedule("collider", collidingTF);
        try {
            p2.addSchedule(collidingSchedule);
        } catch (ScheduleConflictException e) {
            e.printStackTrace();
        }
        event.setEventScheduleTF(tf);
        assertTrue(p1.getSchedules().contains(event.getEventSchedule()));
        assertFalse(p2.getSchedules().contains(event.getEventSchedule()));
    }

    private void loadData() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime hourAfter = now.plusHours(1);
        tf = new TimeFrame(now, hourAfter);
        event = new Event("birthday", null);
    }

}
