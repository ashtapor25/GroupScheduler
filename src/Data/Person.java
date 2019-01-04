package Data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import Exception.ScheduleConflictException;
import Interface.Loadable;
import Interface.Saveable;
import Observer.Observable;
import Observer.Observer;

//represents a participant
public abstract class Person implements Loadable, Saveable, Observer {

    protected String name;
    private TreeSet<Schedule> schedules;
    private List<Event> events;
    private int pwhash;

    public int getPwhash() {
        return pwhash;
    }

    private static final int NAME_INDEX = 0;
    private static final int START_INDEX = 1;
    private static final int END_INDEX = 2;
    private static final int DESC_INDEX = 3;

    //REQUIRES: positive length string
    //MODIFIES: this
    //EFFECTS: constructs a new person with empty schedules
    public Person(String name, int pwhash){
        this.name = name;
        this.pwhash = pwhash;
        this.schedules = new TreeSet<>();
        this.events = new ArrayList<>();
    }

    //REQUIRES: name is not null
    //MODIFIES:
    //EFFECTS: returns the name of the person
    public String getName() {return name;}

    public List<Event> getEvents() {return events;}

    //REQUIRES: schedules is not empty
    //MODIFIES:
    //EFFECTS: returns the schedules
    public TreeSet<Schedule> getSchedules() {
        return schedules;
    }

    //REQUIRES: nothing
    //MODIFIES: this
    //EFFECTS: takes user input and adds a new schedule to the schedules
    public void addSchedule(Schedule schedule) throws ScheduleConflictException {
        if(checkScheduleConflict(schedule)) {
            System.out.println(schedule+"is making a collision");
            throw new ScheduleConflictException();
        }
        schedules.add(schedule);
    }

    //returns true if the given schedule has a conflict with the existing schedules, false otherwise
    public boolean checkScheduleConflict(Schedule schedule) {
        if (schedules.contains(schedule)) {
            return true;
        } else {
            Schedule ceiling = schedules.ceiling(schedule);
            Schedule floor = schedules.floor(schedule);
            boolean ceilingConflict;
            boolean floorConflict;
            if(ceiling==null) {
                ceilingConflict = false;
            } else {
                ceilingConflict = schedule.getTimeFrame().collidesWith(ceiling.getTimeFrame());
            }
            if(floor==null) {
                floorConflict = false;
            } else {
                floorConflict = schedule.getTimeFrame().collidesWith(floor.getTimeFrame());
            }
            return (ceilingConflict || floorConflict);
        }
    }

    //Overload for TimeFrame
    public boolean checkScheduleConflict(TimeFrame timeFrame) {
        Schedule schedule = new Schedule("temp", timeFrame);
        if (schedules.contains(schedule)) {
            return true;
        } else {
            Schedule ceiling = schedules.ceiling(schedule);
            Schedule floor = schedules.floor(schedule);
            boolean ceilingConflict;
            boolean floorConflict;
            if(ceiling==null) {
                ceilingConflict = false;
            } else {
                ceilingConflict = timeFrame.collidesWith(ceiling.getTimeFrame());
            }
            if(floor==null) {
                floorConflict = false;
            } else {
                floorConflict = timeFrame.collidesWith(floor.getTimeFrame());
            }
            return (ceilingConflict || floorConflict);
        }
    }

    public void addEvent(Event event){
        if(!events.contains(event)){
            events.add(event);
            event.addParticipant(this);
        }
    }

    public void removeEvent(Event event){
        if(events.contains(event)){
            events.remove(event);
            event.removeParticipant(this);
        }
    }

    /*
    //REQUIRES: schedules is not empty
    //MODIFIES:
    //EFFECTS: prints out the schedules in the schedules
    public void PrintSchedules() {
        for(Schedule s:schedules){
            System.out.print(s.getDate());
            if(s.IsLunch()){
                System.out.println(" Lunch");
            }
            else {
                System.out.println(" Dinner");
            }
        }
    }
    */

    public void load(){
        BufferedReader br = null;
        Schedule tempSchedule = null;

        try{
            br = Files.newBufferedReader(Paths.get("C:\\신준수\\학교\\2018년 2학기(UBC)\\Software Construction\\lab\\projectw1_team376\\data\\users\\"+name+".csv"));
            //Charset.forName("UTF-8");
            String line = "";

            while((line = br.readLine()) != null){
                List<String> temp = new ArrayList<>();
                String array[] = line.split(",");
                temp = Arrays.asList(array);
                tempSchedule = list2schedule(temp);
                schedules.add(tempSchedule);
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            try{
                if(br != null) br.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private Schedule list2schedule(List<String> inputList){
        String name = inputList.get(NAME_INDEX);
        ZonedDateTime start = ZonedDateTime.parse(inputList.get(START_INDEX));
        ZonedDateTime end = ZonedDateTime.parse(inputList.get(END_INDEX));
        TimeFrame timeFrame = new TimeFrame(start, end);
        String desc = inputList.get(DESC_INDEX);
        Schedule ret = new Schedule(name, timeFrame);
        ret.setDescription(desc);
        return ret;
    }

    private List<String> schedule2list(Schedule schedule){
        List<String> ret = new ArrayList<>();
        ret.add(schedule.getName());
        ret.add(schedule.getTimeFrame().getStart().toString());
        ret.add(schedule.getTimeFrame().getEnd().toString());
        ret.add(schedule.getDescription());
        return ret;
    }

    public void save(){
        BufferedWriter wr = null;
        try {
            wr = Files.newBufferedWriter(Paths.get("C:\\신준수\\학교\\2018년 2학기(UBC)\\Software Construction\\lab\\projectw1_team376\\data\\users\\"+name+".csv"));
            for (Schedule newSchedule : schedules) {
                List<String> list = schedule2list(newSchedule);
                for (String d : list) {
                    wr.write(d);
                    wr.write(",");
                }
                //wr.write("address");
                wr.newLine();
            }
        } catch (NoSuchFileException e) {
            e.printStackTrace();
            try{
                wr = new BufferedWriter(new FileWriter("C:\\신준수\\학교\\2018년 2학기(UBC)\\Software Construction\\lab\\projectw1_team376\\data\\"+name+".csv", true));
                for (Schedule newSchedule : schedules) {
                    List<String> list = schedule2list(newSchedule);
                    for (String d : list) {
                        wr.write(d);
                        wr.write(",");
                    }
                    //wr.write("address");
                    wr.newLine();
                }
            }catch(Exception ep){
                ep.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (wr != null) {
                    wr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract String role();

    protected abstract void removePerson(Person person);

    public void update(Observable obs) {
        Event event = (Event) obs;
        if(events.contains(event)){
            try{
                addSchedule(event.getEventSchedule());
            }catch(ScheduleConflictException e) {
                System.out.println("Error: You cannot join "+event.getEventSchedule().getName());
            }
        }
    }
}
