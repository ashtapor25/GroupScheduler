package Data;

import Observer.Observable;
import Observer.Observer;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Event extends Observable{

    private TimeFrame availableTimeFrame;
    private Schedule eventSchedule;
    private List<Person> participants;

    public TimeFrame getAvailableTimeFrame() {
        return availableTimeFrame;
    }

    public Event(String name, TimeFrame availableTimeFrame) {
        this.eventSchedule = new Schedule(name, null);
        this.observers = new ArrayList<>();
        this.participants = new ArrayList<>();
        this.availableTimeFrame = availableTimeFrame;
    }

    public void setName(String name) {
        eventSchedule.setName(name);
    }

    public void setAvailableTimeFrame(TimeFrame availableTimeFrame) {
        this.availableTimeFrame = availableTimeFrame;
    }

    public Schedule getEventSchedule() {
        return eventSchedule;
    }

    public void setEventScheduleTF(TimeFrame timeFrame) {
        eventSchedule.setTimeFrame(timeFrame);
        notifyObservers();
    }

    public List<Person> getParticipants() {return participants;}

    public void addParticipant(Person person) {
        if (!observers.contains(person)){
            observers.add(person);
        }
        if (!participants.contains(person)){
            participants.add(person);
            person.addEvent(this);
        }
    }

    public void removeParticipant(Person person) {
        if (observers.contains(person)) {
            observers.remove(person);
            person.removeEvent(this);
        }
    }

    // REQUIRES: availableTimeFrame starts and ends at either xx:00 or xx:30
    // returns 30 min TimeFrames which are available for every participant
    public List<TimeFrame> getEmptySpots() {
        List<TimeFrame> chopped = availableTimeFrame.chopInto30mins();
        List<TimeFrame> ret = new ArrayList<>();
        for(TimeFrame block: chopped) {
            boolean conflicted = false;
            if(block.getStart().getHour()<=8 || block.getEnd().getHour()>=22) {
                conflicted = true;
            }
            for(Person person: participants){
                if(person.checkScheduleConflict(block)) {
                    conflicted = true;
                    break;
                }
            }
            if(!conflicted){
                ret.add(block);
            }
        }
        return ret;
    }

    /* Previous Code
    public void setAppoinment(Schedule schedule) {
        appointments.add(schedule);
        for(Person person: participants){
            try{
                person.addSchedule(schedule);
            }catch(ScheduleConflictException e){
                System.out.println(person.getName()+" was excluded from the appointment");
            }
        }
    }
    */
}
