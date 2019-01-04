package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/*
public class TestPerson {

    private static String NAME = "guyname";
    private Person person;
    private Date d1 = new Date(2000, 10, 20);
    private Date d2 = new Date(3000, 6, 15);
    private TimeFrame lunchTF;
    private TimeFrame dinnerTF;

    @BeforeEach
    public void runBefore() {
         person = new Guest(NAME);

    }

    @Test
    public void testgetSchedules(){
        Schedule toAdd1 = new Schedule(d1, true);
        Schedule toAdd2 = new Schedule(d2, false);
        try{
            person.addSchedule(toAdd1);
            person.addSchedule(toAdd2);
            assertEquals(person.getSchedules().get(0), toAdd1);
            assertEquals(person.getSchedules().get(1), toAdd2);
        }catch (ScheduleConflictException e) {
            fail(e);
        }
    }

    @Test
    public void testaddSchedule(){
        Schedule toAdd = new Schedule(d1, true);
        try{
            person.addSchedule(toAdd);
            Integer size = person.getSchedules().size();
            assertEquals(person.getSchedules().get(size-1), toAdd);
        }catch (ScheduleConflictException e){
            fail(e);
        }
    }

    @Test
    public void testScheduleConflictException(){
        Schedule toAdd1 = new Schedule(d1, true);
        Date d3 = new Date(2000, 10, 20);
        Schedule toAdd2 = new Schedule(d3, true);
        try{
            person.addSchedule(toAdd1);
            person.addSchedule(toAdd2);
            fail("");
        }catch (ScheduleConflictException e) {
            //expected
        }
    }


    //How to test a printing method?
    @Test
    public void testPrintSchedules(){

    }

    @Test
    public void testloadsave(){
        Schedule toAdd1 = new Schedule(d1, true);
        Schedule toAdd2 = new Schedule(d2, false);
        try{
            person.addSchedule(toAdd1);
            person.addSchedule(toAdd2);
            person.save();
            person = new Guest("guyname");
            person.load();
            assertEquals(person.getSchedules().get(0).getDate(), d1);
            assertEquals(person.getSchedules().get(0).IsLunch(), true);
            assertEquals(person.getSchedules().get(1).getDate(), d2);
            assertEquals(person.getSchedules().get(1).IsLunch(), false);
        }catch(ScheduleConflictException e){
            fail(e);
        }
    }
}
*/