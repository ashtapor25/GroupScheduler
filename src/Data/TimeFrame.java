package Data;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TimeFrame implements Comparable<TimeFrame> {

    private ZonedDateTime start;
    private ZonedDateTime end;

    public TimeFrame(ZonedDateTime start, ZonedDateTime end) {
        this.start = start;
        this.end = end;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public boolean collidesWith(TimeFrame other) {

        if(this.start.compareTo(other.start)!=1 && this.end.compareTo(other.start)!=1) {
            return false;
        } else if(this.start.compareTo(other.end)!=-1 && this.end.compareTo(other.end)!=-1) {
            return false;
        } else {
            return true;
        }
    }

    // tests whether this object is inside the given timeframe
    public boolean isWithin(TimeFrame other) {

        if (this.start.isBefore(other.start) || this.start.isAfter(other.end) || this.end.isBefore(other.start) || this.end.isAfter(other.end))
            return false;
        else return true;
    }

    public List<TimeFrame> chopInto30mins() {
        List<TimeFrame> ret = new ArrayList<>();
        ZonedDateTime tempStart = start.minusSeconds(start.getSecond());
        while (tempStart.isBefore(end)) {
            ZonedDateTime tempEnd = tempStart.plusMinutes(30);
            if(tempEnd.isAfter(end)) {
                break;
            }
            TimeFrame tempTF = new TimeFrame(tempStart, tempEnd);
            ret.add(tempTF);
            tempStart = tempStart.plusMinutes(30);
        }
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeFrame timeFrame = (TimeFrame) o;
        return Objects.equals(start, timeFrame.start) &&
                Objects.equals(end, timeFrame.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    // REQUIRES: o1 and o2 are TimeFrame objects
    // EFFECT: returns... -1 if o1 is before o2, 0 if o1 is same as o2, 1 if o1 is after o2
    @Override
    public int compareTo(TimeFrame o) {
        int ret;
        ZonedDateTime tf1start = this.getStart();
        ZonedDateTime tf1end = this.getEnd();
        ZonedDateTime tf2start = o.getStart();
        ZonedDateTime tf2end = o.getEnd();
        ret = tf1start.compareTo(tf2start);
        if (ret == 0) {
            ret = tf1end.compareTo(tf2end);
        }
        return ret;
    }

    @Override
    public String toString() {
        return "TimeFrame{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
