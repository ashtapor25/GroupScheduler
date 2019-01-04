package ui;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.util.*;

import Data.*;
import Data.TimeFrame;
import Exception.ScheduleConflictException;
import Interface.Loadable;
import Interface.Saveable;
import ui.ScreenFrames.*;
import ui.SwingCalendar.CalendarEventClickEvent;
import ui.SwingCalendar.CalendarEventClickListener;

import javax.swing.*;

public class GroupScheduler implements Loadable, Saveable, CalendarEventClickListener {

    private HashMap<String, Person> users;
    private Person currUser;
    private Event event;
    private JFrame loginScreen;
    private UserScreen userScreen;
    private ScheduleAdderScreen scheduleAdderScreen;
    private EventScreen eventScreen;
    private EventAdderScreen eventAdderScreen;

    //List<List<String>> loaded = new ArrayList<>();
    //Represents a new group schedule setup
    public GroupScheduler() {
        this.users = new HashMap<>();
        this.load();
        event = new Event("mymeeting", nextWeekTF());
        for(Person p: users.values()) {
            event.addParticipant(p);
        }
        loginScreen = new LoginScreen(this);
        userScreen = new UserScreen(this);
        scheduleAdderScreen = new ScheduleAdderScreen(this);
        eventScreen = new EventScreen(Converter.TimeframesToCalendarEvents(event.getEmptySpots()), this);
        eventAdderScreen = new EventAdderScreen(this);
    }

    private TimeFrame nextWeekTF() {
        ZonedDateTime start = ZonedDateTime.of(LocalDate.now(), LocalTime.MIN, ZoneId.systemDefault());
        ZonedDateTime end = start.plusDays(7);
        return new TimeFrame(start, end);
    }

    private TimeFrame next3dayTF() {
        ZonedDateTime start = ZonedDateTime.of(LocalDate.now(), LocalTime.MIN, ZoneId.systemDefault());
        ZonedDateTime end = start.plusDays(3);
        return new TimeFrame(start, end);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    public void Scheduling() {
        loginScreen.setVisible(true);
    }

    public void calendarEventClick(CalendarEventClickEvent e) {
        eventScreen.setVisible(false);
        LocalDateTime startLDT = LocalDateTime.of(e.getCalendarEvent().getDate(), e.getCalendarEvent().getStart());
        Date startDate = Date.from(startLDT.atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime endLDT = LocalDateTime.of(e.getCalendarEvent().getDate(), e.getCalendarEvent().getEnd());
        Date endDate = Date.from(endLDT.atZone(ZoneId.systemDefault()).toInstant());
        eventAdderScreen.setStartTime(startDate);
        eventAdderScreen.setEndTime(endDate);
        eventAdderScreen.setVisible(true);
    }

    public void submitEvent(Schedule schedule) {
        eventAdderScreen.setVisible(false);
        event.getEventSchedule().setDescription(schedule.getDescription());
        event.setName(schedule.getName());
        event.setEventScheduleTF(schedule.getTimeFrame());
        userScreen.setUser(currUser);
        userScreen.setVisible(true);
    }

    /*
    public void importGoogleCalendar() {
        List<Event> imported = CalendarQuickstart.fetchItems(currUser.getName(), event.getAvailableTimeFrame().getStart(), event.getAvailableTimeFrame().getEnd());
        for(Event e: imported) {
            currUser.addSchedule(Converter.CalendarEventToSchedule(e));
        }
    }
    */

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    public void addGuest(String name, int pwhash) {
        if(users.containsKey(name)) {
            PopupScreen popup = new PopupScreen("This name already exists!");
        } else {
            Person newPerson = new Guest(name, pwhash);
            users.put(name, newPerson);
            currUser = newPerson;
            event.addParticipant(newPerson);
            loginScreen.setVisible(false);
            userScreen.setUser(currUser);
        }
    }

    private void addHost(String name, int pwhash) {
        Person newPerson = new Host(name, pwhash);
        users.put(name, newPerson);
        System.out.println(name+" is the Host");
        currUser = newPerson;
    }

    public void logout() {
        currUser = null;
        userScreen.setVisible(false);
        loginScreen.setVisible(true);
    }

    //REQUIRES:
    //MODIFIES:
    //EFFECTS:
    public void changeUser(String pname, int pwhash) {
        Person temp = users.get(pname);
        if(temp==null){
            PopupScreen popup = new PopupScreen("User does not exist");
        } else if(temp.getPwhash()==pwhash) {
            currUser = users.get(pname);
            loginScreen.setVisible(false);
            userScreen.setUser(currUser);
            userScreen.setVisible(true);
        } else {
            PopupScreen popup = new PopupScreen("Wrong password");
        }
    }

    public void addSchedule(){
        userScreen.setVisible(false);
        scheduleAdderScreen.setVisible(true);
    }

    public void submitSchedule(Schedule schedule) {
        try {
            currUser.addSchedule(schedule);
        } catch (ScheduleConflictException e) {
            PopupScreen scheduleConflict = new PopupScreen("Schedule Conflict Occured!");
        } finally {
            scheduleAdderScreen.setVisible(false);
            userScreen.setUser(currUser);
            userScreen.setVisible(true);
        }
    }

    public void cancelSchedule() {
        scheduleAdderScreen.setVisible(false);
        eventAdderScreen.setVisible(false);
        userScreen.setVisible(true);
    }

    public void showAvailableSpots() {
        eventScreen.setEvents(Converter.TimeframesToCalendarEvents(event.getEmptySpots()));
        eventScreen.setVisible(true);
        userScreen.setVisible(false);
    }

    //Credit to http://jeong-pro.tistory.com/70
    public void load(){
        BufferedReader br = null;

        try{
        br = Files.newBufferedReader(Paths.get("C:\\신준수\\학교\\2018년 2학기(UBC)\\Software Construction\\lab\\projectw1_team376\\data\\userlist.csv"));
        //Charset.forName("UTF-8");
        String line = "";

            while((line = br.readLine()) != null){
                List<String> temp;
                Person tempPerson;
                String array[] = line.split(",");
                temp = Arrays.asList(array);
                if(temp.get(2).equals("host")) {
                    tempPerson = new Host(temp.get(0), Integer.parseInt(temp.get(1)));
                }
                else tempPerson = new Guest(temp.get(0), Integer.parseInt(temp.get(1)));
                users.put(temp.get(0), tempPerson);
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
        for(String key:users.keySet()){
            users.get(key).load();
        }
    }

    //Credit to http://jeong-pro.tistory.com/70
    public void save() {
        BufferedWriter wr = null;
        try {
            wr = Files.newBufferedWriter(Paths.get("C:\\신준수\\학교\\2018년 2학기(UBC)\\Software Construction\\lab\\projectw1_team376\\data\\userlist.csv"));
            for (Person person : users.values()) {
                wr.write(person.getName());
                wr.write(",");
                wr.write(Integer.toString(person.getPwhash()));
                wr.write(",");
                wr.write(person.role());
                //wr.write("address");
                wr.newLine();
            }
        } catch (FileNotFoundException e) {
            try{
                wr = new BufferedWriter(new FileWriter("C:\\신준수\\학교\\2018년 2학기(UBC)\\Software Construction\\lab\\projectw1_team376\\data\\userlist.csv", true));
                for (Person person : users.values()) {
                    wr.write(person.getName());
                    wr.write(",");
                    wr.write(Integer.toString(person.getPwhash()));
                    wr.write(",");
                    wr.write(person.role());
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
        for(String key:users.keySet()){
            users.get(key).save();
        }
    }
}
