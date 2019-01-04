package test;

import Data.Guest;
import Data.Person;
import Data.Schedule;
import Data.TimeFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import Exception.ScheduleConflictException;

public class TestTimeFrame {

    private TimeFrame initTF;
    private ZonedDateTime initStart, initEnd;


    @BeforeEach
    public void runBefore() {
        initStart = ZonedDateTime.now();
    }

    @Test
    public void testcollidesWith() {
        ZonedDateTime availableStart = ZonedDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault());
        TimeFrame sleep1tf = new TimeFrame(availableStart, availableStart.plusHours(9));
        TimeFrame morningWork1tf = new TimeFrame(availableStart.plusHours(9), availableStart.plusHours(12));
        TimeFrame timeFrame= new TimeFrame(availableStart.plusHours(8).plusMinutes(30), availableStart.plusHours(9));
        Schedule tocomp = new Schedule("tocompare", timeFrame);
        assertTrue(sleep1tf.collidesWith(timeFrame));
        assertFalse(morningWork1tf.collidesWith(timeFrame));
        Person person = new Guest("whatever");
        try {
            person.addSchedule(new Schedule("sleep",sleep1tf));
            person.addSchedule(new Schedule("work", morningWork1tf));
        } catch (ScheduleConflictException e) {

        }
        System.out.println(person.getSchedules().ceiling(tocomp));
        System.out.println(person.getSchedules().floor(tocomp));
    }

    @Test
    public void testisWithin() {

    }

    @Test
    public void testSortedSet() {
        SortedSet<TimeFrame> mySet = new TreeSet<>();
        List<TimeFrame> compareList = new ArrayList<>();
        initEnd = initStart.plusHours(2);
        initTF = new TimeFrame(initStart, initEnd);
        TimeFrame first = new TimeFrame(initStart.minusHours(2),initEnd.minusHours(2));
        TimeFrame second = initTF;
        TimeFrame third = new TimeFrame(initStart.plusHours(2),initEnd.plusHours(2));
        mySet.add(third);
        mySet.add(second);
        mySet.add(first);
        compareList.add(first);
        compareList.add(second);
        compareList.add(third);
        int index = 0;
        for(Iterator i = mySet.iterator(); i.hasNext();) {
            assertEquals(i.next(), compareList.get(index));
            index ++;
        }
    }

    @Test
    public void testchopInto30mins() {
        initEnd = initStart.plusHours(2);
        initTF = new TimeFrame(initStart, initEnd);
        List<TimeFrame> chopped = initTF.chopInto30mins();
        List<TimeFrame> compared = new ArrayList<>();

        ZonedDateTime zdt1, zdt2, zdt3, zdt4;
        TimeFrame tf1, tf2, tf3, tf4;
        zdt1 = initStart.plusMinutes(30);
        zdt2 = initStart.plusMinutes(60);
        zdt3 = initStart.plusMinutes(90);
        zdt4 = initStart.plusMinutes(120);
        tf1 = new TimeFrame(initStart, zdt1);
        tf2 = new TimeFrame(zdt1, zdt2);
        tf3 = new TimeFrame(zdt2, zdt3);
        tf4 = new TimeFrame(zdt3, zdt4);
        compared.add(tf1);
        compared.add(tf2);
        compared.add(tf3);
        compared.add(tf4);
        for(int i=0; i < compared.size(); i++){
            assertEquals(compared.get(i), chopped.get(i));
        }
    }
}
