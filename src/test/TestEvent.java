package test;

import Data.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import Exception.ScheduleConflictException;
import ui.GroupScheduler;
import ui.ScreenFrames.EventScreen;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEvent {
    private Event event;
    private Person p1;
    private Person p2;
    ZonedDateTime availableStart;

    @BeforeEach
    public void runBefore() {
        p1 = new Host("Alpha", 0);
        p2 = new Guest("Beta", 0);
        loadData();
    }

    @Test
    public void testgetEmptySlots() {
        event.addParticipant(p1);
        event.addParticipant(p2);
        List<TimeFrame> ret = event.getEmptySpots();
        ZonedDateTime firstday = availableStart;
        ZonedDateTime secondday = firstday.plusDays(1);
        TimeFrame block1 = new TimeFrame(firstday.plusHours(12), firstday.plusHours(12).plusMinutes(30));
        TimeFrame block2 = new TimeFrame(firstday.plusHours(12).plusMinutes(30), firstday.plusHours(13));
        TimeFrame block3 = new TimeFrame(firstday.plusHours(18), firstday.plusHours(18).plusMinutes(30));
        TimeFrame block4 = new TimeFrame(firstday.plusHours(18).plusMinutes(30), firstday.plusHours(19));
        TimeFrame block5 = new TimeFrame(secondday.plusHours(12), secondday.plusHours(12).plusMinutes(30));
        TimeFrame block6 = new TimeFrame(secondday.plusHours(12).plusMinutes(30), secondday.plusHours(13));
        TimeFrame block7 = new TimeFrame(secondday.plusHours(18), secondday.plusHours(18).plusMinutes(30));
        TimeFrame block8 = new TimeFrame(secondday.plusHours(18).plusMinutes(30), secondday.plusHours(19));
        List<TimeFrame> expected = new ArrayList<>();
        for(TimeFrame t: ret) {
            System.out.println(t);
        }
        expected.add(block1);
        expected.add(block2);
        expected.add(block3);
        expected.add(block4);
        expected.add(block5);
        expected.add(block6);
        expected.add(block7);
        expected.add(block8);
        for(int i = 0; i < ret.size(); i++) {
            assertEquals(expected.get(i), ret.get(i));
        }
    }

    @Test
    public void testgetEmptySlotsOnScreen() {
        event.addParticipant(p1);
        event.addParticipant(p2);
        List<TimeFrame> ret = event.getEmptySpots();
        ZonedDateTime firstday = availableStart;
        ZonedDateTime secondday = firstday.plusDays(1);
        TreeSet<Schedule> testschedules = new TreeSet<>();
        for(Integer i = 0; i < ret.size() ; i++) {
            String name = "Available " + i.toString();
            testschedules.add(new Schedule(name, ret.get(i)));
        }
        EventScreen eventScreen = new EventScreen(Converter.ScheduleListtoCalendarEventList(testschedules), new GroupScheduler());
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    private void loadData() {
        ZonedDateTime now = ZonedDateTime.now();
        this.availableStart = ZonedDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 0, 0, 0, 0, ZoneId.systemDefault());
        ZonedDateTime availableEnd = availableStart.plusDays(2);
        ZonedDateTime secondDayStart = availableStart.plusDays(1);
        TimeFrame availableTF = new TimeFrame(availableStart, availableEnd);
        event = new Event("casual meal", availableTF);
        TimeFrame sleep1tf = new TimeFrame(availableStart, availableStart.plusHours(9));
        TimeFrame sleep2tf = new TimeFrame(availableStart.plusHours(19), secondDayStart.plusHours(9));
        TimeFrame sleep3tf = new TimeFrame(secondDayStart.plusHours(19), secondDayStart.plusDays(1).plusHours(9));
        TimeFrame morningWork1tf = new TimeFrame(availableStart.plusHours(9), availableStart.plusHours(12));
        TimeFrame morningWork2tf = new TimeFrame(secondDayStart.plusHours(9), secondDayStart.plusHours(12));
        TimeFrame afternoonWork1tf = new TimeFrame(availableStart.plusHours(13), availableStart.plusHours(18));
        TimeFrame afternoonWork2tf = new TimeFrame(secondDayStart.plusHours(13), secondDayStart.plusHours(18));
        Schedule sleep1 = new Schedule("sleep1", sleep1tf);
        Schedule sleep2 = new Schedule("sleep2", sleep2tf);
        Schedule sleep3 = new Schedule("sleep3", sleep3tf);
        Schedule morningWork1 = new Schedule("morningWork1", morningWork1tf);
        Schedule morningWork2 = new Schedule("morningWork2", morningWork2tf);
        Schedule afternoonWork1 = new Schedule("afternoonWork1", afternoonWork1tf);
        Schedule afternoonWork2 = new Schedule("afternoonWork2", afternoonWork2tf);
        List<Schedule> everydaySchedule = new ArrayList<>();
        everydaySchedule.add(sleep1);
        everydaySchedule.add(sleep2);
        everydaySchedule.add(sleep3);
        everydaySchedule.add(morningWork1);
        everydaySchedule.add(morningWork2);
        everydaySchedule.add(afternoonWork1);
        everydaySchedule.add(afternoonWork2);
        for(Schedule s:everydaySchedule) {
            try{
                p1.addSchedule(s);
                p2.addSchedule(s);
            } catch (ScheduleConflictException e) {
                e.printStackTrace();
            }
        }
    }
}
