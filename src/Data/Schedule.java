package Data;

public class Schedule implements Comparable<Schedule> {
    private TimeFrame timeFrame;
    private String name;

    private String description;

    public Schedule(String name, TimeFrame timeFrame) {
        this.name = name;
        this.timeFrame = timeFrame;
        this.description = "";
    }

    public TimeFrame getTimeFrame() {
        return timeFrame;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimeFrame(TimeFrame timeFrame) {
        this.timeFrame = timeFrame;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(Schedule o) {
        return this.getTimeFrame().compareTo(o.getTimeFrame());
    }

    public boolean collidesWith(Schedule o) {
        return this.getTimeFrame().collidesWith(o.getTimeFrame());
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "timeFrame=" + timeFrame +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
